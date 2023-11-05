package com.bksproject.bksproject.payload.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CourseResponse {
    private Long id;
    private String name;
    private String abtrac;
    private String author;
    private String imgUrl;
    private String serial;
    private Long price;
    private boolean isPurchased;

    public CourseResponse(Long id, String name, String abtrac, String author, String imgUrl, String serial, Long price, boolean isPurchased) {
        this.id = id;
        this.name = name;
        this.abtrac = abtrac;
        this.author = author;
        this.imgUrl = imgUrl;
        this.serial = serial;
        this.price = price;
        this.isPurchased = isPurchased;
    }
}
