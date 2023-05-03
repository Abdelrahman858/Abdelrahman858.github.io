package project.semsark.model.request_body;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.Email;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetailsDto {

    private String username;
    private String phone;
    @Email
    private String email;
    private boolean social ;
    private String img;
    private String personalImg;
    private String idImg;
    private String password;
    private String gender;
    private boolean verifyID;

    private boolean suspended;
    private Double rate;


}
