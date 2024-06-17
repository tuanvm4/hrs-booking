package com.example.hotelbooking.service;

import com.example.hotelbooking.entity.BookingEntity;
import com.example.hotelbooking.entity.RoomEntity;
import com.example.hotelbooking.entity.UserEntity;
import com.example.hotelbooking.enums.BookingStatus;
import com.example.hotelbooking.exception.ResourceNotFoundException;
import com.example.hotelbooking.mapper.BookingMapper;
import com.example.hotelbooking.model.Booking;
import com.example.hotelbooking.repository.BookingRepository;
import com.example.hotelbooking.repository.RoomRepository;
import com.example.hotelbooking.repository.UserRepository;
import com.example.hotelbooking.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class BookingServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingMapper bookingMapper;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetBookingsByUserId() {
        Long userId = 1L;
        List<BookingEntity> bookings = Arrays.asList(new BookingEntity(), new BookingEntity());
        when(bookingRepository.findByUserId(userId)).thenReturn(bookings);

        List<Booking> result = bookingService.getBookingsByUserId(userId);

        assertEquals(2, result.size());
        verify(bookingRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testCreateBooking_UserNotFound() {
        // Setup
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setUserId(1L); // Assuming user with ID 1 does not exist
        bookingEntity.setCheckInDate(LocalDate.now().plusDays(1)); // Bypass date validator
        bookingEntity.setCheckOutDate(LocalDate.now().plusDays(2)); // Bypass date validator
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Execute and assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> bookingService.createBooking(bookingEntity));
        assertEquals("User not found with id 1", exception.getMessage());

        // Verify interactions
        verify(userRepository, times(1)).findById(1L);
        verifyNoInteractions(roomRepository, bookingRepository, bookingMapper);
    }

    @Test
    void testCreateBooking_RoomNotFound() {
        // Setup
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setUserId(1L); // Assuming user with ID 1 exists
        bookingEntity.setRoomId(1L); // Assuming room with ID 1 does not exist
        bookingEntity.setCheckInDate(LocalDate.now().plusDays(1)); // Bypass date validator
        bookingEntity.setCheckOutDate(LocalDate.now().plusDays(2)); // Bypass date validator
        when(userRepository.findById(1L)).thenReturn(Optional.of(new UserEntity()));
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        // Execute and assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> bookingService.createBooking(bookingEntity));
        assertEquals("Room not found with id 1", exception.getMessage());

        // Verify interactions
        verify(userRepository, times(1)).findById(1L);
        verify(roomRepository, times(1)).findById(1L);
        verifyNoInteractions(bookingRepository, bookingMapper);
    }

    @Test
    public void testCreateBooking_Success() {
        // Mock data
        Long userId = 1L;
        Long roomId = 1L;
        LocalDate checkInDate = LocalDate.now().plusDays(1);
        LocalDate checkOutDate = LocalDate.now().plusDays(2);

        // Mock user and room entities
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setRoomId(roomId);

        // Mock user and room repository behavior
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(roomEntity));

        // Mock overlapping bookings to be empty (room is available)
        when(bookingRepository.findOverlappingBookings(roomId, checkInDate, checkOutDate)).thenReturn(Collections.emptyList());

        // Mock booking entity and mapper
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setUserId(userId);
        bookingEntity.setRoomId(roomId);
        bookingEntity.setCheckInDate(checkInDate);
        bookingEntity.setCheckOutDate(checkOutDate);

        Booking bookingModel = new Booking(); // Mock booking model

        // Mock booking repository to save the booking entity
        when(bookingRepository.save(bookingEntity)).thenReturn(bookingEntity);
        when(bookingMapper.entityToModel(bookingEntity)).thenReturn(bookingModel);

        // Perform the method call
        Booking createdBooking = bookingService.createBooking(bookingEntity);

        // Verify the result
        assertNotNull(createdBooking);
        assertEquals(bookingModel, createdBooking);
        assertEquals(BookingStatus.CONFIRMED.name(), bookingEntity.getBookingStatus());
    }
}