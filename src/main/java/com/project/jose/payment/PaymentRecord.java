package com.project.jose.payment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment_record")
public class PaymentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @NonNull
    @Column(nullable = false,unique = true)
    private String term;

    @NonNull
    @Column(name = "course_fee",nullable = false)
    private Float courseFee;



}
