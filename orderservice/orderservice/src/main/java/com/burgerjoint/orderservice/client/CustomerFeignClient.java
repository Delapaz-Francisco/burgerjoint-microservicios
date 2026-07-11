package com.burgerjoint.orderservice.client;

import com.burgerjoint.orderservice.dto.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "customerservice", url = "http://customer-service:8081/api/customers")
public interface CustomerFeignClient {

    @GetMapping("/{id}")
    CustomerDTO obtenerClientePorId(@PathVariable("id") Long id);

}
