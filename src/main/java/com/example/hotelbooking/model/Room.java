package com.example.hotelbooking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class Room {
    private Long roomId;
    private Long hotelId;
    private String roomNumber;
    private String roomType;
    private BigDecimal pricePerNight;
    private Boolean isAvailable;
}
