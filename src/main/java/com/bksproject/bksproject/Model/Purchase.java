package com.bksproject.bksproject.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant createAt;

    private Instant updateAt;

    @ManyToOne
    @JoinColumn(name="user_ID", referencedColumnName = "ID")
    private Users userPurchase;

    @ManyToOne
    @JoinColumn(name="course_ID", referencedColumnName = "ID")
    private Courses coursePurchase;

    public Purchase(Instant updateAt, Users userPurchase, Courses coursePurchase) {
        this.createAt = Instant.now();
        this.updateAt = updateAt;
        this.userPurchase = userPurchase;
        this.coursePurchase = coursePurchase;
    }
}
