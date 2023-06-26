package com.project.jose.account;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Log4j2
public class AdminService {

    @Autowired
    private AdminRepo adminRepo;


    @GetMapping
    @Operation(summary = "Returns all the Admins in the database")
    @ApiResponse(responseCode = "200", description = "valid response",
            content = {@Content(mediaType="application/json", schema=@Schema(implementation= Admin.class))})
    public List<Admin> list(){
        log.traceEntry("Enter list");
        var retval = adminRepo.findAll();
        log.traceExit("Exit list", retval);
        return adminRepo.findAll();
    }

    @PostMapping
    public void save(@RequestBody Admin admin) {
        log.traceEntry("enter save", admin);
        adminRepo.save(admin);
        log.traceExit("exit save", admin);
    }

    @DeleteMapping
    public void delete(Long code) {
        log.traceEntry("Enter delete", code);
        adminRepo.deleteById(code);
        log.traceExit("Exit delete");
    }

}
