package project.semsark.model.response_body;

import lombok.*;
import project.semsark.model.entity.Photos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdResponse {

    private Long id;
    private UserResponse user;
    List<Photos> photosList = new ArrayList<>();
    private String signalPower;
    private String title;
    private String category;
    private String address;
    private String  des;
    private String apartmentDetails;
    private String city;
    private String gov;
    private String  types;
    private Double price;
    private long views;
    private LocalDate date;

    private Double lang;
    private Double lat;
    private long area;
    private long numOfRoom;
    private long numOfBathroom;
    private long numOfHalls;
    private long level;
    private boolean finished;
    private boolean single;

}
