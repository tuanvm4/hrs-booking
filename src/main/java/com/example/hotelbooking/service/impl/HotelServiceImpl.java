package com.example.hotelbooking.service.impl;

import com.example.hotelbooking.entity.BookingEntity;
import com.example.hotelbooking.exception.ResourceNotFoundException;
import com.example.hotelbooking.exception.RoomUnavailableException;
import com.example.hotelbooking.mapper.BookingMapper;
import com.example.hotelbooking.mapper.HotelMapper;
import com.example.hotelbooking.model.Booking;
import com.example.hotelbooking.model.Hotel;
import com.example.hotelbooking.repository.BookingRepository;
import com.example.hotelbooking.repository.HotelRepository;
import com.example.hotelbooking.service.BookingService;
import com.example.hotelbooking.service.HotelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    @Override
    public List<Hotel> getAllHotels() {
            var hotelEntities= hotelRepository.findAll();
        return hotelMapper.entitiesToModels(hotelEntities);
    }

    @Override
    public List<Hotel> findHotelsByCity(String city) {
        var hotelEntities =  hotelRepository.findByCityContainingIgnoreCase(city);
        return hotelMapper.entitiesToModels(hotelEntities);
    }
}
