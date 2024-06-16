package com.example.hotelbooking.service.impl;

import com.example.hotelbooking.entity.RoomEntity;
import com.example.hotelbooking.mapper.RoomMapper;
import com.example.hotelbooking.model.Room;
import com.example.hotelbooking.repository.BookingRepository;
import com.example.hotelbooking.repository.RoomRepository;
import com.example.hotelbooking.repository.UserRepository;
import com.example.hotelbooking.service.RoomService;
import com.example.hotelbooking.ultilities.DateValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Override
    public List<Room> getAvailableRooms(Long hotelId, LocalDate checkInDate, LocalDate checkOutDate) {
        // Logic to find available rooms based on hotelId, checkInDate, and checkOutDate
        DateValidator.validateCheckInBeforeCheckOut(checkInDate, checkOutDate);
        List<RoomEntity> availableRooms = roomRepository.findAvailableRooms(hotelId, checkInDate, checkOutDate);
        return availableRooms.stream()
                .map(roomMapper::entityToModel)
                .toList();
    }
}
