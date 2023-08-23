package com.userservice.controller;

import com.userservice.entities.User;
import com.userservice.services.UserService;
import com.userservice.services.impl.UserServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class USerController {

    @Autowired
    private UserService userService;


    private Logger logger = LoggerFactory.getLogger(USerController.class);


    //creting user
    @PostMapping("/add")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    //getting single user
    int retryCount=1;
    @GetMapping("/userById/{userId}")
 //   @CircuitBreaker(name = "ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
 //   @Retry(name = "ratingHotelService",fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
        logger.info("Get Single User Handler: USEr Controller");
        logger.info("Retry Cout: {}",retryCount);
        retryCount++;
        User userById = userService.getUserById(userId);
        return ResponseEntity.ok(userById);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    //creating fallback method for circuit breaker


    public ResponseEntity<User> ratingHotelFallback(String userId,Exception ex){
     //   logger.info("Fallback is executed because service is down :", ex.getMessage());

        ex.printStackTrace();
       User user =  User.builder().
                email("dummy@gmail.com").
                name("Dummy").
                about("This user is creating dummmy because some services are down")
                .userId("12345")
                .build();
        return new ResponseEntity<>(user,HttpStatus.BAD_REQUEST);
    }
}
