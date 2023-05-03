package project.semsark.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TokenStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private long id;

    @Column
    private String token;
    @Column
    private Long userId;

    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @Column(name = "expired_date", updatable = false)
    private Date expiredDate;
    @Column
    private Boolean notUsed;


}
