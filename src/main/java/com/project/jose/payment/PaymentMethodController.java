package com.project.jose.payment;


import com.project.jose.JoseApplication;
import com.project.jose.account.Student;
import com.project.jose.account.StudentRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("payments")
@Tag(name = "PaymentMethod",description = "Everything about Payments")
@Log4j2
public class PaymentMethodController {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private StudentRepo studentRepo;


    Logger logger = LoggerFactory.getLogger(JoseApplication.class);



    @GetMapping
    @Operation(summary = "Get all Payments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment records found ")
    })
    public Object getAllPayments(){
        logger.info("Getting all payments.");
        var paymentList = paymentMethodRepository.findAll();
        if(!paymentList.isEmpty()) {
            logger.info("Payment records found");
            return paymentList;
        }
        logger.error("No Payments found");
        throw  new Message("No Payments found !! ");
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get Payments by payment ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment record found with payment ID"),
    })
    public ResponseEntity<PaymentMethod> getPaymentMethodById(@PathVariable(value = "id") Long id) {
        logger.info("Getting details with ID");
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id)
                .orElseThrow(() ->  new Message("PaymentMethod not found for this payment id :: " + id));

        return ResponseEntity.ok().body(paymentMethod);
    }


    @GetMapping("/stu_{student_id}")
    @Operation(summary = "Get Payments for student ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment record found with student ID"),
    })
    public ResponseEntity <ArrayList<PaymentMethod>> getPaymentMethodByStudentId(@PathVariable(value = "student_id") Long student_id) {
        logger.info("Getting details for student ID");

        var list = paymentMethodRepository.findAll();
        ArrayList<PaymentMethod> resultList = new ArrayList<>();

        Student student = studentRepo.findById(student_id)
                .orElseThrow(() ->new CardDetailsController.Message("Student not found for this id :: " + student_id) );

        for(PaymentMethod paymentMethod : list){

            if(paymentMethod.getStudent().getId().equals(student.getId())){

                resultList.add(paymentMethod);

            }
        }

        if(resultList.size()==0){
            logger.error("No card details found for given id");
            throw new Message("No card details found for given id");
        }
        logger.info("Details found for the given student id");
        return ResponseEntity.ok().body(resultList);
    }



    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class Message extends RuntimeException{
        public Message(String s) {
            super(s);
        }
    }





    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }



    @PostMapping("/creates")
    @Operation(summary = "Create Payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment record created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid URL")
    })
    public PaymentMethod createPayment(@RequestBody PaymentMethodRequest paymentMethodRequest) {
        // create a new payment using the data from the paymentRequest
        PaymentMethod payment = new PaymentMethod(paymentMethodRequest);

        long beforeCount = paymentMethodRepository.count();
        logger.info("Saving payment");
        // save the payment to the database

        paymentMethodRepository.save(payment);

        long afterCount = paymentMethodRepository.count();
        if(beforeCount!=afterCount){
            logger.info("Payment record added!!");
            return payment;
        }

        logger.error("Adding Payment record failed!!");
        throw new Message( "Adding Payment record failed!!");



    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Payment record deleted!!"),
            @ApiResponse(responseCode = "400",description = "No payment record found with the given id")
    })
    public Object deletePayment(@PathVariable long id){

        logger.info("Deleting the payment record with :"+ id);
        Optional<PaymentMethod> repo = Optional.ofNullable(paymentMethodRepository.findById(id));

        if(repo.isPresent()) {
            paymentMethodRepository.deleteById(id);
            logger.info("Payment record deleted");
            return "PAYMENT RECORD DELETED: "+id;

        } else {
            logger.error("No record found to delete");
            throw new Message( "No record found to delete.");


        }

    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully updated Payment with ID"),
            @ApiResponse(responseCode = "500",description = "Payment record not found to update")
    })
    public Object updatePayment(@PathVariable long id,@RequestBody PaymentMethod paymentMethod){

        Optional<PaymentMethod> paymentsOptional2 = Optional.ofNullable(paymentMethodRepository.findById(id));
        logger.info("Update Payment record"+id);
        if(paymentsOptional2.isPresent()){
            try {
                PaymentMethod existingResource = paymentsOptional2.get();
                existingResource.setRemarks(paymentMethod.getRemarks());
                paymentMethodRepository.save(existingResource);
                logger.info("Record updated.");
                return "RECORD UPDATED :" + id;
            }catch (Exception e){
                logger.error("Record not updated.");
                throw new Message( "No record found to update.");
            }
        }else{
            logger.error("Record not updated.");
            throw new Message( "No record found to update.");

        }

    }



}
