package com.example.hotelbooking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class Booking {
    private Long bookingId;
    private Long userId;
    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String bookingStatus;
    private String userName; // Assuming you also want to store user's name
    private String roomNumber; // Assuming you also want to store room number

}
