package com.example.hotelbooking.ultilities;

import com.example.hotelbooking.exception.RoomUnavailableException;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class DateValidator {
    public static void validateCheckInBeforeCheckOut(LocalDate checkInDate, LocalDate checkOutDate) {
        if (checkInDate.isAfter(checkOutDate)) {
            throw new RoomUnavailableException("Check-in date must be before check-out date");
        }
    }
}
