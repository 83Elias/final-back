package com.example.msusers.feing;

import com.example.msusers.config.keyloakFlow.OAuthFeignConfig;
import com.example.msusers.config.viaGateway.FeignInterceptor;
import com.example.msusers.domain.Bill;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-bill",configuration = OAuthFeignConfig.class)
public interface BillsFeign {


    @GetMapping("/api/v1/bills/{id}")
    ResponseEntity<List<Bill>> getBillsPerIdUser(@PathVariable String id);
}
