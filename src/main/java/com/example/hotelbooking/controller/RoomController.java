package com.example.hotelbooking.controller;

import com.example.hotelbooking.exception.RoomUnavailableException;
import com.example.hotelbooking.model.Room;
import com.example.hotelbooking.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Room", description = "APIs related to room operations")
@RestController
@AllArgsConstructor
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;
    @Operation(summary = "Get available rooms", description = "Retrieves a list of available rooms for the specified hotel and date range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Available rooms retrieved successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Room.class)))),
            @ApiResponse(responseCode = "400", description = "Room unavailable or invalid input", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/available")
    public ResponseEntity<?> getAvailableRooms(
            @Parameter(description = "ID of the hotel to search for available rooms", required = true) @RequestParam Long hotelId,
            @Parameter(description = "Check-in date", required = true) @RequestParam LocalDate checkInDate,
            @Parameter(description = "Check-out date", required = true) @RequestParam LocalDate checkOutDate
    ) {
        try {

            List<Room> availableRooms = roomService.getAvailableRooms(hotelId, checkInDate, checkOutDate);
            return ResponseEntity.ok(availableRooms);
        } catch (RoomUnavailableException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}