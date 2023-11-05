package com.bksproject.bksproject.payload.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Data
@Getter
@Setter
public class PurchaseResponse {
    private Long id;
    private Instant createAt;
    private Instant updateAt;
    private String username;
    private String fullname;
    private String serial;
    private String name;
    private Long courseId;


    public PurchaseResponse(Long id, Instant createAt, Instant updateAt, String username, String fullname, String serial,String name, Long courseId) {
        this.id = id;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.username = username;
        this.fullname = fullname;
        this.serial = serial;
        this.name = name;
        this.courseId = courseId;
    }
}
