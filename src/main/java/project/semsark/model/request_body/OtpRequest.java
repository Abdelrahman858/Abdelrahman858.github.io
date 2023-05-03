package project.semsark.model.request_body;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OtpRequest {
    private String otp;
    private String email;
}
