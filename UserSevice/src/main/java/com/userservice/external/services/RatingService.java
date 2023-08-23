package com.userservice.external.services;

import com.userservice.entities.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@FeignClient(name = "RATING-SERVICE")
public interface RatingService {

    //get

    //post
    @PostMapping("/ratings/create")
    public Rating createRating(Rating values);

    //put
    @PutMapping("/ratings/update/{ratingId}")
    public ResponseEntity<Rating> updateRating(@PathVariable String ratigId, Rating rating);
//    public Rating updateRating(@PathVariable String ratigId, Rating rating);


    //delete
    @DeleteMapping("/ratings/delete/{ratingId}")
    public void deleteRating(@PathVariable String ratingId);
}
