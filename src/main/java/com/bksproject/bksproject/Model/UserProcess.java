package com.bksproject.bksproject.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant createAt;

    @NotBlank
    private Boolean isComplete = false;

    @ManyToOne
    @JoinColumn(name="lesson_ID", referencedColumnName = "ID")
    private Lessons lessonIdCurrent;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "ID")
    private Users userCurrent;

    public UserProcess(Lessons lessonIdCurrent, Users userCurrent) {
        this.createAt = Instant.now();
        this.lessonIdCurrent = lessonIdCurrent;
        this.userCurrent = userCurrent;
    }

    public void setIsComplete(){
        this.isComplete = !isComplete;
    }
}
