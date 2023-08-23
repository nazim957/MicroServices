package com.hotelservice.controller;

import com.hotelservice.entities.Hotel;
import com.hotelservice.service.HotelService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelCotroller {

    @Autowired
    private HotelService hotelService;

    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping("/create")
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel){
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.create(hotel));
    }

    @PreAuthorize("hasAuthority('SCOPE_internal')")
    @GetMapping("/getHotelById/{hotelId}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable String hotelId){
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.get(hotelId));
    }

    @PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
    @GetMapping("/getAll")
    public ResponseEntity<List<Hotel>> getHotelById(){
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.getAll());
    }
}
