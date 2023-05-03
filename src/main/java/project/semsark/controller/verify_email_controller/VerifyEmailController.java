package project.semsark.controller.verify_email_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.semsark.model.request_body.OtpRequest;
import project.semsark.model.response_body.AuthenticationResponse;
import project.semsark.model.request_body.UpdatePasswordRequest;
import project.semsark.service.auth_service.VerifyEmailService;

import javax.mail.MessagingException;

@RestController

@RequestMapping("/email")
public class VerifyEmailController {
    @Autowired
    VerifyEmailService verifyEmailService;

    @PostMapping(value = "/sendOtp/{email}")
    void sendOtp(@PathVariable String email) throws MessagingException {
        verifyEmailService.sendEmailVerification(email);
    }

    @PostMapping(value = "/verifyEmail")
    public String verifyEmail(@RequestBody OtpRequest otpRequest) {
        return verifyEmailService.checkOtpValid(otpRequest);
    }



}
