package com.project.jose.payment;


import com.project.jose.JoseApplication;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("payment_record")
@Tag(name = "CourseFee",description = "Everything about CourseFee")
@Log4j2
public class PaymentRecordController {

    @Autowired
    private PaymentRecordRepository paymentRecordRepository;

    Logger logger = LoggerFactory.getLogger(JoseApplication.class);

    @GetMapping
    @Operation(summary = "Get all terms course fee ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " Records found ")
    })
    public List<PaymentRecord> getAllCourseFee(){
        logger.info("Getting all course fee records.");
        var list = paymentRecordRepository.findAll();
        if(!list.isEmpty()) {
            logger.info("Records found");
            return list;
        }
        logger.error("No records found");
        throw  new PaymentMethodController.Message("No records found !! ");


    }

    @GetMapping("/{term}")
    @Operation(summary = "Get course fee for term")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fee record found successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid URL")
    })
    public ResponseEntity<PaymentRecord> getPaymentMethodById(@PathVariable(value = "term") String term) {
        logger.info("getting course fee for term :"+ term);
        var list = paymentRecordRepository.findAll();
        PaymentRecord result= null;
        for(PaymentRecord paymentRecord : list){
            String a = paymentRecord.getTerm().toLowerCase();
            String b = term.toLowerCase();
            if(a.equals(b)){
                logger.info("Record found");
                return ResponseEntity.ok().body(paymentRecord);

            }
        }

        logger.error("No record found for that term :"+term);
        throw  new Message("No record found for that term !! ");

    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class Message extends RuntimeException{
        public Message(String s) {
            super(s);
        }
    }

    @PostMapping("/creates")
    @Operation(summary = "Set course fee for term")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fee record created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid URL")
    })
    public PaymentRecord createPayment(@RequestBody PaymentRecord paymentRecord) {

        PaymentRecord paymentRecord1 = new PaymentRecord();
        paymentRecord1.setTerm(paymentRecord.getTerm());
        paymentRecord1.setCourseFee(paymentRecord.getCourseFee());

        long beforeCount = paymentRecordRepository.count();
        logger.info("Saving payment");


        paymentRecordRepository.save(paymentRecord1);

        long afterCount = paymentRecordRepository.count();
        if(beforeCount!=afterCount){
            logger.info("Course fee record added!!");
            return paymentRecord1;
        }

        logger.error("Adding course fee record failed!!");
        throw new Message( "Adding course fee record failed!!");

    }


    @DeleteMapping("/{term}")
    @Operation(summary = "Delete fee record for given term")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "fee record deleted!!"),
            @ApiResponse(responseCode = "400",description = "No fee record found with the given term")
    })
    public Object deletePayment(@PathVariable String term){

        var list = paymentRecordRepository.findAll();
        PaymentRecord result= null;
        for(PaymentRecord paymentRecord : list){
            String a = paymentRecord.getTerm().toLowerCase();
            String b = term.toLowerCase();
            if(a.equals(b)){
                paymentRecordRepository.deleteById(  paymentRecord.getId());
                logger.info("Record deleted!");

                return paymentRecord.getTerm()+ " Term fee deleted.";
            }
        }

        logger.error("No record found to delete");
        throw new PaymentMethodController.Message( "No record found to delete.");

    }


    @PutMapping("/{term}")
    @Operation(summary = "Update term fee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully updated fee for given term"),
            @ApiResponse(responseCode = "500",description = "Fee record not found to update")
    })
    public Object updatePayment(@PathVariable String term,Float fee){



        var list = paymentRecordRepository.findAll();
        PaymentRecord result= null;
        for(PaymentRecord paymentRecord : list){
            String a = paymentRecord.getTerm().toLowerCase();
            String b = term.toLowerCase();
            if(a.equals(b)){
                paymentRecord.setTerm(term);
                paymentRecord.setCourseFee(fee);
                paymentRecordRepository.save(paymentRecord);

                logger.info("Record updated!");

                return paymentRecord.getTerm()+ " Term fee updated . ";
            }
        }

        logger.error("No record found to update");
        throw new PaymentMethodController.Message( "No record found to update.");


    }


}
