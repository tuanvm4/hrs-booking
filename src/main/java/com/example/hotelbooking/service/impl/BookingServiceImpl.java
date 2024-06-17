package com.example.hotelbooking.service.impl;

import com.example.hotelbooking.entity.BookingEntity;
import com.example.hotelbooking.entity.RoomEntity;
import com.example.hotelbooking.entity.UserEntity;
import com.example.hotelbooking.enums.BookingStatus;
import com.example.hotelbooking.exception.ResourceNotFoundException;
import com.example.hotelbooking.exception.RoomUnavailableException;
import com.example.hotelbooking.mapper.BookingMapper;
import com.example.hotelbooking.model.Booking;
import com.example.hotelbooking.repository.BookingRepository;
import com.example.hotelbooking.repository.RoomRepository;
import com.example.hotelbooking.repository.UserRepository;
import com.example.hotelbooking.service.BookingService;
import com.example.hotelbooking.ultilities.DateValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final BookingMapper bookingMapper;

    @Override
    public List<Booking> getBookingsByUserId(Long userId) {
        List<BookingEntity> bookingEntities = bookingRepository.findByUserId(userId);
        return bookingEntities.stream()
                .map(bookingMapper::entityToModel)
                .toList();
    }

    @Transactional
    public Booking createBooking(BookingEntity booking) {
        DateValidator.validateCheckInBeforeCheckOut(booking.getCheckInDate(), booking.getCheckOutDate());
        // Check if the user exists
        Optional<UserEntity> userOptional = userRepository.findById(booking.getUserId());
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User not found with id " + booking.getUserId());
        }
        // Check if the room exists
        Optional<RoomEntity> roomOptional = roomRepository.findById(booking.getRoomId());
        if (roomOptional.isEmpty()) {
            throw new ResourceNotFoundException("Room not found with id " + booking.getRoomId());
        }

        List<BookingEntity> overlappingBookings = bookingRepository.findOverlappingBookings(booking.getRoomId(),booking.getCheckInDate(), booking.getCheckOutDate());

        if (overlappingBookings.isEmpty()) {
            booking.setBookingStatus(BookingStatus.CONFIRMED.name());
            BookingEntity bookingEntity = bookingRepository.save(booking);
            return bookingMapper.entityToModel(bookingEntity);
        } else {
            throw new RoomUnavailableException("Room is already booked for the selected time frame.");
        }
    }

    @Override
    public Booking updateBooking(Long bookingId, BookingEntity booking) {
        DateValidator.validateCheckInBeforeCheckOut(booking.getCheckInDate(), booking.getCheckOutDate());
        Optional<BookingEntity> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isPresent()) {
            if (BookingStatus.CANCELLED.name().equals(booking.getBookingStatus())) {
                BookingEntity existingBooking = optionalBooking.get();
                existingBooking.setBookingStatus(booking.getBookingStatus());
                var bookingEntity = bookingRepository.save(existingBooking);
                return bookingMapper.entityToModel(bookingEntity);
            } else if (BookingStatus.CONFIRMED.name().equals(booking.getBookingStatus())) {
                List<BookingEntity> overlappingBookings = bookingRepository.findOverlappingBookings(booking.getRoomId(),booking.getCheckInDate(), booking.getCheckOutDate());

                if (overlappingBookings.isEmpty()) {
                    throw new RoomUnavailableException("Room is already booked for the selected time frame.");
                }
                BookingEntity existingBooking = optionalBooking.get();
                existingBooking.setUserId(booking.getUserId());
                existingBooking.setRoomId(booking.getRoomId());
                existingBooking.setCheckInDate(booking.getCheckInDate());
                existingBooking.setCheckOutDate(booking.getCheckOutDate());
                existingBooking.setBookingStatus(booking.getBookingStatus());
                var bookingEntity = bookingRepository.save(existingBooking);
                return bookingMapper.entityToModel(bookingEntity);
            }
        }

        throw new ResourceNotFoundException("Booking not found with id " + bookingId);
    }
}

