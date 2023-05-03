package project.semsark.model.request_body;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChatRequest {
    private String receiverEmail;
    private String message;
    private boolean status;
    private String date;
}
