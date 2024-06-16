package com.example.hotelbooking.controller;

import com.example.hotelbooking.exception.RoomUnavailableException;
import com.example.hotelbooking.model.Room;
import com.example.hotelbooking.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/available")
    public ResponseEntity<?> getAvailableRooms(
            @RequestParam Long hotelId,
            @RequestParam LocalDate checkInDate,
            @RequestParam LocalDate checkOutDate
    ) {
        try {

            List<Room> availableRooms = roomService.getAvailableRooms(hotelId, checkInDate, checkOutDate);
            return ResponseEntity.ok(availableRooms);
        } catch (RoomUnavailableException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}