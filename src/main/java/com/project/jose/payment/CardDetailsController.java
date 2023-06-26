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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("card_details")
@Tag(name = "CardDetails",description = "Everything about cards")
@Log4j2
public class CardDetailsController {


    @Autowired
    private CardDetailsRepository cardDetailsRepository;

    @Autowired
    private StudentRepo studentRepo;
    Logger logger = LoggerFactory.getLogger(JoseApplication.class);



    @GetMapping
    public List<CardDetails> getAllCards(){

        return cardDetailsRepository.findAll();
    }


    @GetMapping("/{student_id}")
    @Operation(summary = "Get card details by Student Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card details record found"),
    })
    public ResponseEntity
            <List<CardDetails>> getCardDetailsByStudentId(@PathVariable(value = "student_id") Long studentId) {
        logger.info("Getting details with student ID");
        List<CardDetails> cardDetailsList = cardDetailsRepository.findAll();

        List<CardDetails> resultList = new ArrayList<>();

        for(CardDetails cardDetails : cardDetailsList){
            Student student =cardDetails.getStudent();
            if(student.getId()==studentId){
                resultList.add(cardDetails);

            }
        }
        if(resultList.size()==0){
            logger.error("No details found for the given student id :"+studentId);
            throw  new PaymentRecordController.Message("No details found for the given student id !! ");
        }
        logger.info("Details found with student ID -"+studentId);

        return ResponseEntity.ok().body(resultList);
    }







    @PostMapping("/creates")
    @Operation(summary = "Create card details by Student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card details record found"),
    })
    public CardDetails createCardDetail(@RequestBody CardDetails cardDetails,Long student_id) {

        long beforeCount = cardDetailsRepository.count();
        logger.info("Creating card details record");

        CardDetails cardDetails1 = new CardDetails();
        Student student = studentRepo.findById(student_id)
                .orElseThrow(() ->  new Message("Student not found for this id :: " + student_id) );
        logger.info("Student found");
        cardDetails1.setStudent(student);
        cardDetails1.setType(cardDetails.getType());
        cardDetails1.setExpMonth(cardDetails.getExpMonth());
        cardDetails1.setNumber(cardDetails.getNumber());
        cardDetails1.setExpYear(cardDetails.getExpYear());


        cardDetailsRepository.save(cardDetails1);
        long afterCount = cardDetailsRepository.count();

        if(beforeCount!=afterCount){
            logger.info("Payment record added!!");
            return cardDetails1;
        }

        logger.error("Adding card details  failed!!");
        throw new Message( "Adding card details  failed!!");

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class Message extends RuntimeException{
        public Message(String s) {
            super(s);
        }
    }




    @DeleteMapping("/{id}")
    @Operation(summary = "Delete card details record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Card details record deleted!!"),
            @ApiResponse(responseCode = "400",description = "No card details record found")
    })
    public Object deletePayment(@PathVariable Long id){

        var list = cardDetailsRepository.findAll();
        CardDetails result= null;
        for(CardDetails cardDetails : list){

            if(cardDetails.getId().equals(id)){
                cardDetailsRepository.deleteById(  cardDetails.getId());
                logger.info("Record deleted!");

                return cardDetails.getId()+ " :id - card record deleted.";
            }
        }

        logger.error("No record found to delete");
        throw new Message( "No record found to delete.");

    }

    @PutMapping("/{id}")
    @Operation(summary = "Update card details ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully updated card details"),
            @ApiResponse(responseCode = "500",description = "Card details record not found to update")
    })
    public Object updatePayment(@RequestBody CardDetails cardDetails1,Long student_id){



        logger.info("Updating card details");
        var list = cardDetailsRepository.findAll();

        CardDetails result= null;
        for(CardDetails cardDetails : list){

            if(cardDetails.getId().equals(cardDetails1.getId())){


                Student student = studentRepo.findById(student_id)
                        .orElseThrow(() ->  new Message("Student not found for this id :: " + student_id) );
                logger.info("Student found");
                cardDetails.setStudent(student);

                cardDetails.setType(cardDetails1.getType());
                cardDetails.setExpMonth(cardDetails1.getExpMonth());
                cardDetails.setNumber(cardDetails1.getNumber());
                cardDetails.setExpYear(cardDetails1.getExpYear());

                cardDetailsRepository.save( cardDetails );



                logger.info("Record updated!!");

                return cardDetails.getId()+ " :id - card record updated.";
            }
        }

        logger.error("No record found to update");
        throw new PaymentMethodController.Message( "No record found to update.");


    }

}
