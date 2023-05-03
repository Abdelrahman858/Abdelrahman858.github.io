package project.semsark.model.response_body;

import lombok.*;
import project.semsark.model.request_body.RoleDTO;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {
    private long id;
    private String name;
    private List<RoleDTO> roles;
}
