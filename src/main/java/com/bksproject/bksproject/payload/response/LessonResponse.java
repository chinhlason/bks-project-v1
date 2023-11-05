package com.bksproject.bksproject.payload.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Data
@Setter
@Getter
public class LessonResponse {
    private Long id;
    private Instant createAt;
    private String title;
    private String description;
    private String mediaUrl;

    public LessonResponse(Long id, Instant createAt, String title, String description, String mediaUrl) {
        this.id = id;
        this.createAt = createAt;
        this.title = title;
        this.description = description;
        this.mediaUrl = mediaUrl;
    }
}
