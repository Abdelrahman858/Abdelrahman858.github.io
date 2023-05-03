package project.semsark.model.response_body;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenResponse {
    private String refreshToken;
    private Date issuedDate;
    private Date expiredDate;
}
