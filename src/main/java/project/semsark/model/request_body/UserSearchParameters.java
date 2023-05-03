package project.semsark.model.request_body;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSearchParameters {

    private String password;
    private String email;
}
