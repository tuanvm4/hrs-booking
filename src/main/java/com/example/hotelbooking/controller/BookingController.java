package com.example.hotelbooking.controller;

import com.example.hotelbooking.entity.BookingEntity;
import com.example.hotelbooking.mapper.BookingMapper;
import com.example.hotelbooking.model.Booking;
import com.example.hotelbooking.service.impl.BookingServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/bookings")
public class BookingController {

    private final BookingServiceImpl bookingService;
    private final BookingMapper bookingMapper;

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
//        List<Booking> bookings = bookingService.getAllBookings();
//        return ResponseEntity.ok(bookings);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long bookingId) {
//        Booking booking = bookingService.getBookingById(bookingId);
//        if (booking != null) {
//            return ResponseEntity.ok(booking);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
        return ResponseEntity.ok(null);
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
//        Booking createdBooking = bookingService.createBooking(bookingMapper.modelToEntity(booking));
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
        return ResponseEntity.ok(null);

    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long bookingId, @RequestBody Booking booking) {
        BookingEntity updatedBooking = bookingService.updateBooking(bookingId, bookingMapper.modelToEntity(booking));
        if (updatedBooking != null) {
            return ResponseEntity.ok(bookingMapper.entityToModel(updatedBooking));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long bookingId) {
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}
