package project.semsark.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_details")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    private String username;
    private String gender;
    private String email;
    private String password;
    private String phone = "00000000000";
    private Double rate=0.0;
    private Double rateSum=0.0;
    private long rateCounter=0;
    @OneToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;
    private boolean verifyID = false;
    private boolean verify = false;
    private boolean suspended = false;
    private String img;
    private String personalImg;
    private String idImg;
    @OneToMany
    private List<Building> myAds = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL)
    private Favourites favourites;

}
