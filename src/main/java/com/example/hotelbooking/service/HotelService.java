package com.example.hotelbooking.service;

import com.example.hotelbooking.model.Hotel;

import java.util.List;

public interface HotelService {

    List<Hotel> getAllHotels();

    List<Hotel> findHotelsByCity(String city);

}
