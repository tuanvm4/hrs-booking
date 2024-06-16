package com.example.hotelbooking.service;

import com.example.hotelbooking.entity.BookingEntity;
import com.example.hotelbooking.model.Booking;

import java.util.List;

public interface BookingService {

    List<BookingEntity> getAllBookings();

    BookingEntity getBookingById(Long bookingId);

    BookingEntity createBooking(BookingEntity booking);

    BookingEntity updateBooking(Long bookingId, BookingEntity booking);

    void deleteBooking(Long bookingId);
}
