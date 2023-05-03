package project.semsark.model.request_body;

import lombok.*;
import project.semsark.model.enums.TypesOfBuilding;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdRequest {
    List<String> photosList;
    private String signalPower;
    private String title;
    private String category;
    private String address;
    private String des;
    private String apartmentDetails;
    private String city;
    private String gov;
    private Double price;
    private Double lang;
    private Double lat;
    private long area;
    private long numOfRoom;
    private long numOfBathroom;
    private long numOfHalls;
    private long level;
    private boolean finished;
    private boolean single;
    private TypesOfBuilding types;
}
