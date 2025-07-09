package com.mahmoud.appointmentsystem.client;


import com.mahmoud.appointmentsystem.DTO.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@FeignClient(name="user-service",configuration = FeignClientConfig.class)// no need url eureka will handle
public interface UserServiceClient {

    @GetMapping("/users/{id}")
    UserDTO  getUserById(@PathVariable("id") Long id);
}
