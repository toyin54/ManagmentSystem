package com.project.jose.payment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.jose.account.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "card_details", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"number"})
})
public class CardDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NonNull
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "stu_id",nullable = false)
    private Student student;


    @NonNull
    @Column(name = "type", nullable = false)
    private String type;


    @NonNull
    @Column(name = "number", nullable = false,unique = true)
    private String number;

    @NonNull
    @Column(name = "exp_month", nullable = false)
    private String expMonth;

    @NonNull
    @Column(name = "exp_year", nullable = false)
    private String expYear;


}
