package com.example.hotelbooking.controller;

import com.example.hotelbooking.exception.ResourceNotFoundException;
import com.example.hotelbooking.exception.RoomUnavailableException;
import com.example.hotelbooking.mapper.BookingMapper;
import com.example.hotelbooking.model.Booking;
import com.example.hotelbooking.service.impl.BookingServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Booking", description = "APIs related to booking operations")
@RestController
@AllArgsConstructor
@RequestMapping("/bookings")
public class BookingController {

    private final BookingServiceImpl bookingService;
    private final BookingMapper bookingMapper;

    @Operation(summary = "Get bookings by user ID", description = "Retrieves a list of bookings for the specified user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookings retrieved successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Booking.class)))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUserId(@PathVariable Long userId) {
        List<Booking> bookings = bookingService.getBookingsByUserId(userId);
        return ResponseEntity.ok(bookings);
    }

    @Operation(summary = "Create a new booking", description = "Creates a new booking with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Booking successfully created", content = @Content(schema = @Schema(implementation = Booking.class))),
            @ApiResponse(responseCode = "404", description = "Related resource not found", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request or room unavailable", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        try {
            Booking createdBooking = bookingService.createBooking(bookingMapper.modelToEntity(booking));
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RoomUnavailableException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @Operation(summary = "Update a booking", description = "Updates the details of an existing booking by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking successfully updated"),
            @ApiResponse(responseCode = "404", description = "Booking not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request or room unavailable")
    })
    @PutMapping("/{bookingId}")
    public ResponseEntity<?> updateBooking(@PathVariable Long bookingId, @RequestBody Booking booking) {
        try {
            var updatedBooking = bookingService.updateBooking(bookingId, bookingMapper.modelToEntity(booking));
            return ResponseEntity.status(HttpStatus.OK).body(updatedBooking);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RoomUnavailableException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
