package com.bksproject.bksproject.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "courses",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "serial")
        })
@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String abtrac;

    @NotBlank
    private String author;

    @NotBlank
    private String imgUrl;

    @NotBlank
    private String serial;

    private Instant createAt;

    @NotBlank
    private Long price;

    @OneToMany(mappedBy = "courseId")
    @JsonBackReference
    private Set<Lessons> lessons;

    @OneToMany(mappedBy = "coursePurchase")
    @JsonBackReference
    private Set<Purchase> purchases;

    public Courses(String name, String abtrac, String author, String imgUrl, String serial, Long price) {
        this.name = name;
        this.abtrac = abtrac;
        this.author = author;
        this.imgUrl = imgUrl;
        this.serial = serial;
        this.createAt = Instant.now();
        this.price = price;
    }
}
