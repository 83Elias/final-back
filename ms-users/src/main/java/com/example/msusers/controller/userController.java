package com.example.msusers.controller;

import com.example.msusers.domain.User;
import com.example.msusers.service.ServiceUser;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequestMapping("/customer/bills")
@RestController
@AllArgsConstructor
public class userController {

    private ServiceUser serviceUser;


    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getAllUsers(@PathVariable String id) {
        return ResponseEntity.ok(serviceUser.getUserWithBills(id));
    }





}
