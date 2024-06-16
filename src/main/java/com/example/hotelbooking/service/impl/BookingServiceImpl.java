package com.example.hotelbooking.service.impl;

import com.example.hotelbooking.entity.BookingEntity;
import com.example.hotelbooking.exception.ResourceNotFoundException;
import com.example.hotelbooking.exception.RoomUnavailableException;
import com.example.hotelbooking.repository.BookingRepository;
import com.example.hotelbooking.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public List<BookingEntity> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public BookingEntity getBookingById(Long bookingId) {
        Optional<BookingEntity> optionalBooking = bookingRepository.findById(bookingId);
        return optionalBooking.orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + bookingId));
    }

    @Transactional
    public BookingEntity createBooking(BookingEntity booking) {
        if (isRoomAvailable(booking.getRoomId(), booking.getCheckInDate(), booking.getCheckOutDate())) {
            return bookingRepository.save(booking);
        } else {
            throw new RoomUnavailableException("Room is already booked for the selected time frame.");
        }
    }

    private boolean isRoomAvailable(Long roomId, LocalDate checkIn, LocalDate checkOut) {
        List<BookingEntity> overlappingBookings = bookingRepository.findOverlappingBookings(roomId, checkIn, checkOut);
        return overlappingBookings.isEmpty();
    }

    @Override
    public BookingEntity updateBooking(Long bookingId, BookingEntity booking) {
        Optional<BookingEntity> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isPresent()) {
            BookingEntity existingBooking = optionalBooking.get();
            existingBooking.setUserId(booking.getUserId());
            existingBooking.setRoomId(booking.getRoomId());
            existingBooking.setCheckInDate(booking.getCheckInDate());
            existingBooking.setCheckOutDate(booking.getCheckOutDate());
            existingBooking.setBookingStatus(booking.getBookingStatus());
            return bookingRepository.save(existingBooking);
        } else {
            throw new ResourceNotFoundException("Booking not found with id " + bookingId);
        }
    }

    @Override
    public void deleteBooking(Long bookingId) {
        Optional<BookingEntity> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isPresent()) {
            bookingRepository.delete(optionalBooking.get());
        } else {
            throw new ResourceNotFoundException("Booking not found with id " + bookingId);
        }
    }
}
