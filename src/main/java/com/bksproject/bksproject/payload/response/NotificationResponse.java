package com.bksproject.bksproject.payload.response;

import com.bksproject.bksproject.Model.Users;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Data
@Setter
@Getter
public class NotificationResponse {
    private Long id;
    private Instant createAt;
    private String message;
    private String type;
    private boolean read;
    private String userSend;

    public NotificationResponse() {
    }

    public NotificationResponse(Long id, Instant createAt, String message, String type, boolean read, String userSend) {
        this.id = id;
        this.createAt = createAt;
        this.message = message;
        this.type = type;
        this.read = read;
        this.userSend = userSend;
    }
}
