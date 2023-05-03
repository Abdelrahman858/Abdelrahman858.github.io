package project.semsark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.semsark.exception.HelperMessage;
import project.semsark.jwt.JwtUtil;
import project.semsark.mapper.UserDetailsMapper;
import project.semsark.mapper.UserUpdateMapper;
import project.semsark.model.entity.Emails;
import project.semsark.model.entity.OTP;
import project.semsark.model.entity.Role;
import project.semsark.model.entity.User;
import project.semsark.model.enums.Using;
import project.semsark.model.request_body.AuthenticationRequest;
import project.semsark.model.request_body.UserDetailsDto;
import project.semsark.model.request_body.UserSearchParameters;
import project.semsark.model.request_body.UserUpdate;
import project.semsark.model.response_body.UserResponse;
import project.semsark.repository.EmailsRepository;
import project.semsark.repository.OTPRepository;
import project.semsark.repository.UserRepository;
import project.semsark.repository.specification.UserSpecifications;
import project.semsark.service.auth_service.VerifyEmailService;
import project.semsark.validation.UserDetailsValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    VerifyEmailService verifyEmailService;
    @Autowired
    EmailsRepository emailsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailsMapper userDetailsMapper;
    @Autowired
    private UserUpdateMapper userUpdateMapper;
    @Autowired
    private UserDetailsValidator userDetailsValidator;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private OTPRepository otpRepository;
    @Autowired
    JwtUtil jwtUtil;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with the name " + username));

        for (Role role : user.getProfile().getRoles()) {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), roles);
    }

    public User createUser(UserDetailsDto userDetailsDTO) {

        Optional<Emails> emails = emailsRepository.findByEmail(userDetailsDTO.getEmail());
        User user = new User();
        userDetailsMapper.mapTo(userDetailsDTO, user);


        if (!userDetailsDTO.isSocial()) {
            if (!emails.isPresent() || !emails.get().isVerified())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, HelperMessage.EMAIL_NOT_VERIFIED);
            else{
                userDetailsValidator.validate(userDetailsDTO.getEmail());
                emailsRepository.delete(emails.get());
                return userRepository.save(user);
            }
        } else {
            Optional<User> user1 = userRepository.findByEmail(userDetailsDTO.getEmail());
            OTP otp = otpRepository.findByEmailAndAndUsed(userDetailsDTO.getEmail(), Using.EMAIL.name());
            emails.ifPresent(value -> emailsRepository.delete(value));
            if(otp != null)
                otpRepository.delete(otp);
            return user1.orElseGet(() -> userRepository.save(user));
        }
    }

    public void validateEmail(String email) {
        userDetailsValidator.validate(email);
        if (!emailsRepository.findByEmail(email).isPresent()) {
            Emails emails = Emails.builder()
                    .email(email)
                    .verified(false)
                    .build();
            emailsRepository.save(emails);
        }
    }

    public UserResponse updateUserByEmail(UserUpdate userUpdate) {
        User user  = jwtUtil.getUserDataFromToken();
        userUpdateMapper.mapTo(userUpdate, user);
        User updatedUser = userRepository.save(user);

        return userDetailsMapper.mapTo(updatedUser);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, HelperMessage.USER_NOT_FOUND));
    }

    public User findUser(AuthenticationRequest authenticationRequest) {
        if (Boolean.TRUE.equals(authenticationRequest.getSocial()))
            return userRepository.findByEmail(authenticationRequest.getEmail())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, HelperMessage.USER_NOT_FOUND));


        UserSearchParameters userSearchParameters = userDetailsMapper.mapTo(authenticationRequest);

        UserSpecifications userSpecifications = UserSpecifications.builder()
                .userSearchParameters(userSearchParameters)
                .build();

        return userRepository.findAll(userSpecifications).stream()
                .filter(filterdUser -> passwordEncoder.matches(userSearchParameters.getPassword(), filterdUser.getPassword()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, HelperMessage.INCORRECT_PASSWORD));
    }

    public String getUserDataById(Long id){

        Optional<User>user =userRepository.findById(id);

        if(user.isPresent()){
            return user.get().getEmail();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, HelperMessage.USER_NOT_FOUND);
    }
}
