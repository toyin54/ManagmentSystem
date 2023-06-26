package com.project.jose.payment;

import com.project.jose.account.Student;

import java.time.LocalDate;

public class PaymentMethodRequest {

    private Student student;


    public PaymentMethodRequest(Student student, LocalDate date, Float amount, String typeOfMethod, String transactionId) {
        this.student = student;
        this.date = date;
        this.amount = amount;
        this.TypeOfMethod = typeOfMethod;
        this.transactionId = transactionId;
    }

    public PaymentMethodRequest(){

    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate() {
        this.date = LocalDate.now();
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getTypeOfMethod() {
        return TypeOfMethod;
    }

    public void setTypeOfMethod(String typeOfMethod) {
        TypeOfMethod = typeOfMethod;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    private LocalDate date;


    private Float amount;


    private String TypeOfMethod;


    private String transactionId;
}
