package project.semsark.model.entity;

import lombok.*;
import javax.persistence.*;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    private String senderEmail;
    private String receiverEmail;
    private String message;
    private String dates;
    private boolean status;
}
