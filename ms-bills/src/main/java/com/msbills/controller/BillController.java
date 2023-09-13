package com.msbills.controller;

import com.msbills.models.Bill;
import com.msbills.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bills")
@RequiredArgsConstructor
public class BillController {

    private final BillService service;

    @GetMapping("/all")
    public ResponseEntity<List<Bill>> getAll() {

        return ResponseEntity.ok().body(service.getAllBill());
    }

    @GetMapping("{id}")
    public ResponseEntity<List<Bill>> find(@PathVariable String id) {

        return ResponseEntity.ok().body(service.findBillPerUserId(id));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('PROVIDERS')")
    public ResponseEntity<String> deleteBill(@PathVariable String id) {

        return ResponseEntity.ok().body(service.deleteBill(id));
    }


}
