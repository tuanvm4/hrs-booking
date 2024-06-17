package com.example.hotelbooking.service;

import com.example.hotelbooking.model.Hotel;

import java.util.List;
/**
 * Service interface for handling Hotel related operations.
 */
public interface HotelService {

    List<Hotel> getAllHotels();

    List<Hotel> findHotelsByCity(String city);
}
