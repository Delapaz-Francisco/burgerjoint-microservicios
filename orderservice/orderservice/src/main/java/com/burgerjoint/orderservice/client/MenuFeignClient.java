package com.burgerjoint.orderservice.client;

import com.burgerjoint.orderservice.dto.MenuDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "menuservice", url = "http://localhost:8082/api/menu")
public interface MenuFeignClient {

    @GetMapping("/{id}")
    MenuDTO obtenerProductoPorId(@PathVariable("id") Long id);

}
