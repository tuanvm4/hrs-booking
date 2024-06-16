package com.example.hotelbooking.service;

import com.example.hotelbooking.entity.BookingEntity;
import com.example.hotelbooking.model.Booking;

import java.util.List;

public interface BookingService {

    List<Booking> getBookingsByUserId(Long userId);

    Booking getBookingById(Long bookingId);

    Booking createBooking(BookingEntity booking);

    Booking updateBooking(Long bookingId, BookingEntity booking);

    void deleteBooking(Long bookingId);
}
