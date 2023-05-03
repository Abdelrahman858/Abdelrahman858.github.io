package project.semsark.service.auth_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.semsark.exception.HelperMessage;
import project.semsark.jwt.JwtUtil;
import project.semsark.model.request_body.OtpRequest;
import project.semsark.model.response_body.AuthenticationResponse;
import project.semsark.model.entity.OTP;
import project.semsark.model.entity.User;
import project.semsark.model.enums.Using;
import project.semsark.model.request_body.UpdatePasswordRequest;
import project.semsark.repository.OTPRepository;
import project.semsark.repository.UserRepository;
import project.semsark.service.CustomUserDetailsService;
import project.semsark.service.email_sender_service.EmailSenderService;
import project.semsark.service.email_sender_service.EmailService;

import javax.mail.MessagingException;
import java.util.Date;

import static project.semsark.global_methods.GlobalMethods.generateOtp;

@Service
public class ForgetPasswordService {
    @Autowired
    EmailSenderService emailSenderService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    private PasswordEncoder bcryptEncoder;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    private int otpExpirationInMs;
    @Autowired
    private OTPRepository otpRepository;
    @Autowired
    private JwtUtil jwtUtil;


    @Value("${otp.expirationDateInMs}")
    public void setOtpExpirationInMs(int otpExpirationInMs) {
        this.otpExpirationInMs = otpExpirationInMs;
    }
    public void forgetPassword(String email) throws MessagingException {
        if(userRepository.findByEmail(email).isPresent()){
            OTP otp = otpRepository.findByEmailAndAndUsed(email,Using.PASSWORD.name());
            if (otp == null)
                otp = new OTP();
            otp.setOtp(generateOtp());
            otp.setEmail(email);
            otp.setUsed(Using.PASSWORD.name());
            otp.setCreatedAt(new Date(System.currentTimeMillis()));
            otp.setExpiredDate(new Date(System.currentTimeMillis() + otpExpirationInMs));
            String body = otp.getOtp();
            String subject = "Forget Password OTP";
            emailSenderService.sendSimpleEmail(email, body, subject);
            otpRepository.save(otp);
            throw new ResponseStatusException(HttpStatus.CREATED, HelperMessage.FORGET_PASSWORD_REQUEST);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, HelperMessage.EMAIL_WRONG);

    }



    public void checkOtp(OtpRequest otpRequest,String choice) {
        OTP otp1 = otpRepository.findByOtp(otpRequest.getOtp());
        if (otp1 != null && otp1.getUsed().equals(Using.PASSWORD.name())&& otp1.getEmail().equals(otpRequest.getEmail())) {
            if (otp1.getExpiredDate().getTime() - System.currentTimeMillis() > 0) {
                if (choice.equals("delete"))
                    otpRepository.delete(otp1);
            } else {
                otpRepository.delete(otp1);
                throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, HelperMessage.OTP_EXPIRE);
            }
        } else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, HelperMessage.OTP_NOT_FOUND);

    }

    public AuthenticationResponse updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        User user = userDetailsService.findUserByEmail(updatePasswordRequest.getEmail());
        if (user != null) {
            OtpRequest otpRequest = new OtpRequest();
            otpRequest.setOtp(updatePasswordRequest.getOtp());
            otpRequest.setEmail(updatePasswordRequest.getEmail());
            checkOtp(otpRequest, "delete");
            user.setPassword(bcryptEncoder.encode(updatePasswordRequest.getPassword()));
            userRepository.save(user);
            String token = jwtUtil.generateToken(user);
            return new AuthenticationResponse(token);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, HelperMessage.USER_NOT_FOUND);
    }

}






