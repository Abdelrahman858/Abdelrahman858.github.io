package project.semsark.model.response_body;

import lombok.*;
import project.semsark.model.entity.Building;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String username;
    private String gender;
    private String email;
    private String phone = "00000000000";
    private String img;
    private String personalImg;
    private String idImg;
    private List<Building> myAds = new ArrayList<>();
    private boolean verifyID;
    private boolean suspended;
    private Double rate;

}
