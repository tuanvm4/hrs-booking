package com.example.hotelbooking.controller;

import com.example.hotelbooking.model.Hotel;
import com.example.hotelbooking.service.HotelService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/hotels")
public class HotelController {

    private final HotelService hotelService;

    @GetMapping("/search")
    public ResponseEntity<List<Hotel>> searchHotelsByLocation(
            @RequestParam(required = false) String city
    ) {
        List<Hotel> hotels;
        if (city != null) {
            hotels = hotelService.findHotelsByCity(city);
        } else {
            // Handle invalid request, e.g., return all hotels
            hotels = hotelService.getAllHotels();
        }
        return ResponseEntity.ok(hotels);
    }
}