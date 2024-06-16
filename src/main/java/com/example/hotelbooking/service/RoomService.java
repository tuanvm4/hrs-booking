package com.example.hotelbooking.service;

import com.example.hotelbooking.model.Room;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {
    List<Room> getAvailableRooms(Long hotelId, LocalDate checkInDate, LocalDate checkOutDate);

}
