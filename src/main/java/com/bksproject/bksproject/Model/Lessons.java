package com.bksproject.bksproject.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Lessons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant createAt;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @ManyToOne
    @JoinColumn(name="course_id", referencedColumnName = "ID")
    private Courses courseId;

    @OneToOne(mappedBy = "lessonId")
    @JsonBackReference
    private Media media;

    @OneToMany(mappedBy = "lessonIdCurrent")
    @JsonBackReference
    private Set<UserProcess> lessonProcess;

    public Lessons(String title, String description, Courses courseId) {
        this.createAt = Instant.now();
        this.title = title;
        this.description = description;
        this.courseId = courseId;
    }
}
