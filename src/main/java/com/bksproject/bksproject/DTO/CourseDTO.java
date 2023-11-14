package com.bksproject.bksproject.DTO;

import com.bksproject.bksproject.Model.Courses;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class CourseDTO {
    private int totalPage;
    private List<Courses> courses;

    public CourseDTO(int totalPage, List<Courses> courses) {
        this.totalPage = totalPage;
        this.courses = courses;
    }
}
