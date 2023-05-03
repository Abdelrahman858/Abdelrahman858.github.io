package project.semsark.controller.forget_password_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.semsark.model.response_body.AuthenticationResponse;
import project.semsark.model.request_body.EmailRequest;
import project.semsark.model.request_body.OtpRequest;
import project.semsark.model.request_body.UpdatePasswordRequest;
import project.semsark.service.auth_service.ForgetPasswordService;

import javax.mail.MessagingException;

@RestController

@RequestMapping("/forgetPassword")
public class ForgetPasswordController {

    @Autowired
    private ForgetPasswordService forgetPasswordService;

    @PostMapping(value = "/")
    public void forgetPassword(@RequestBody EmailRequest email) throws MessagingException {
        forgetPasswordService.forgetPassword(email.getEmail());
    }

    @PostMapping(value = "/checkOtp")
    public void checkOtp(@RequestBody OtpRequest otp) {
        forgetPasswordService.checkOtp(otp, "check");
    }

    @PostMapping(value = "/updatePassword")
    public AuthenticationResponse updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
        return forgetPasswordService.updatePassword(updatePasswordRequest);
    }


}
