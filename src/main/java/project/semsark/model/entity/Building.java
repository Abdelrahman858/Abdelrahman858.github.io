package project.semsark.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long userId;
    @OneToMany(cascade = CascadeType.ALL)
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
