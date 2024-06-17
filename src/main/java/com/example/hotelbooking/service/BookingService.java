package com.example.hotelbooking.service;

import com.example.hotelbooking.entity.BookingEntity;
import com.example.hotelbooking.model.Booking;

import java.util.List;
/**
 * Service interface for handling booking operations.
 */
public interface BookingService {

    List<Booking> getBookingsByUserId(Long userId);

    Booking createBooking(BookingEntity booking);

    Booking updateBooking(Long bookingId, BookingEntity booking);
}
