package com.project.jose.payment;

import com.project.jose.account.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment_method", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "trans_id"})
})
public class PaymentMethod {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "stu_id",nullable = false, unique = false)
    private Student student;


    @NonNull
    @Column(name = "payment_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate date;

    @NonNull
    @Column(name = "amount", nullable = false)
    private Float amount;

    @NonNull
//    @Enumerated(EnumType.ORDINAL)
    @Column(name = "method", nullable = false)
    private String typeOfMethod;

    @NonNull
    @Column(name = "trans_id", nullable = false, unique = true)
    private String transactionId;


    private String remarks;

    public PaymentMethod(PaymentMethodRequest paymentMethodRequest){

        this.student = paymentMethodRequest.getStudent();
        this.amount = paymentMethodRequest.getAmount();
        this.typeOfMethod = paymentMethodRequest.getTypeOfMethod();
        this.transactionId = paymentMethodRequest.getTransactionId();
        this.date = LocalDate.now();
    }


//    public PaymentMethod(PaymentMethodRequest paymentMethodRequest){
//
//        this.studentId = Long.valueOf(121212);
//        this.amount = Float.valueOf(232323);
//        this.typeOfMethod = "cc";
//        this.transactionId = "sdn";
//        this.date = LocalDate.now();
//    }



}
