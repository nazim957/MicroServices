package com.hotelservice.service;

import com.hotelservice.entities.Hotel;

import java.util.List;

public interface HotelService {

    //create
    Hotel create(Hotel hotel);

    List<Hotel> getAll();

    Hotel get(String id);
}
