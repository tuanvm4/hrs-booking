package com.example.hotelbooking.service;

import com.example.hotelbooking.model.Room;

import java.time.LocalDate;
import java.util.List;
/**
 * Service interface for handling Room related operations.
 */
public interface RoomService {

    List<Room> getAvailableRooms(Long hotelId, LocalDate checkInDate, LocalDate checkOutDate);
}
