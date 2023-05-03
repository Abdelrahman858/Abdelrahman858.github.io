package project.semsark.model.request_body;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserUpdate {

    private String username;
    private String password;
    private String phone;
    private String gender;
    private String img;

}
