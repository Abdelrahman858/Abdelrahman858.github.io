package project.semsark.mapper;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import project.semsark.exception.HelperMessage;
import project.semsark.model.entity.User;
import project.semsark.model.enums.Profiles;
import project.semsark.model.request_body.AuthenticationRequest;
import project.semsark.model.request_body.UserDetailsDto;
import project.semsark.model.request_body.UserSearchParameters;
import project.semsark.model.response_body.UserResponse;
import project.semsark.repository.MainProfileRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDetailsMapper {

    @Autowired
    MainProfileRepository profileRepository;
    @Autowired
    private PasswordEncoder bcryptEncoder;

    public void mapTo(UserDetailsDto userDetailsDTO, User user) {

        if (valid(userDetailsDTO.getUsername())) {
            user.setUsername(userDetailsDTO.getUsername());
        }
        if (valid(userDetailsDTO.getImg())) {
            user.setImg(userDetailsDTO.getImg());
        }

        if (valid(userDetailsDTO.getPersonalImg())) {
            user.setPersonalImg(userDetailsDTO.getPersonalImg());
        }

        if (valid(userDetailsDTO.getIdImg())) {
            user.setIdImg(userDetailsDTO.getIdImg());
        }

        if (valid(userDetailsDTO.getEmail())) {
            user.setEmail(userDetailsDTO.getEmail());
        }else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, HelperMessage.EMAIL_IMP);

        if (userDetailsDTO.isSocial()) {
            user.setVerify(true);
        }
        if (valid(userDetailsDTO.getPhone())) {
            user.setPhone(userDetailsDTO.getPhone());
        }
        if (valid(userDetailsDTO.getGender())) {
            user.setGender(userDetailsDTO.getGender());
        }


        if (valid(userDetailsDTO.getPassword())) {
            user.setPassword(bcryptEncoder.encode(userDetailsDTO.getPassword()));
        }else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, HelperMessage.PASSWORD_IMP);

        if (userDetailsDTO.isSocial() && userDetailsDTO.getPassword() == null)
            user.setPassword(bcryptEncoder.encode(generateCommonLangPassword()));
        user.setVerify(true);
        user.setProfile(profileRepository.findProfileByName(Profiles.User.name()));

    }
    boolean valid(String obj){
        return (obj!=null&&!obj.equals(""));
    }


    public String generateCommonLangPassword() {
        String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(2, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(2);
        String specialChar = RandomStringUtils.random(2, 33, 47, false, false);
        String totalChars = RandomStringUtils.randomAlphanumeric(2);
        String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
                .concat(numbers)
                .concat(specialChar)
                .concat(totalChars);
        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(pwdChars);
        return pwdChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    public UserResponse mapTo(User user){
        return UserResponse.builder()
                .email(user.getEmail())
                .myAds(user.getMyAds())
                .img(user.getImg())
                .gender(user.getGender())
                .phone(user.getPhone())
                .username(user.getUsername())
                .idImg(user.getIdImg())
                .personalImg(user.getPersonalImg())
                .rate(user.getRate())
                .suspended(user.isSuspended())
                .build();
    }

    public UserSearchParameters mapTo(AuthenticationRequest authenticationRequest) {

        return UserSearchParameters.builder()
                .email(authenticationRequest.getEmail()).password(authenticationRequest.getPassword()).build();
    }


}
