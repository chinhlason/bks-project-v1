package com.bksproject.bksproject.payload.response;

import com.bksproject.bksproject.Model.Users;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Data
@Setter
@Getter
public class CommentResponse {
    private Long id;
    private Instant createAt;
    private String content;
    private String username;

    public CommentResponse(Long id, Instant createAt, String content, String username) {
        this.id = id;
        this.createAt = createAt;
        this.content = content;
        this.username = username;
    }
}
