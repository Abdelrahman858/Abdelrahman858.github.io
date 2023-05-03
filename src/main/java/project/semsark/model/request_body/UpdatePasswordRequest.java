package project.semsark.model.request_body;

import lombok.Getter;

@Getter
public class UpdatePasswordRequest {
    private String email;
    private String password;
    private String otp;
}
