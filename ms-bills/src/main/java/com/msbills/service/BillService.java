package com.msbills.service;

import com.msbills.models.Bill;
import com.msbills.repositories.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository repository;
    private final String SUCESS_OPERATION="sucess";

    public List<Bill> getAllBill() {
        return repository.findAll();
    }

    public List<Bill> findBillPerUserId(String id) {

        return repository.findByCustomerBill(id);
    }

    public String deleteBill(String id) {
      Optional<Bill> bill= Optional.ofNullable(repository.findById(id).orElse(null));
      if (!bill.isPresent()){
          throw new RuntimeException("not found bill in the database with id "+id);
      }
      repository.deleteById(id);
      return SUCESS_OPERATION;
    }


}
