package com.example.hotelbooking.repository;

import com.example.hotelbooking.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    @Query("SELECT b FROM BookingEntity b WHERE b.roomId = :roomId AND b.bookingStatus = 'CONFIRMED' AND ("
            + "(b.checkInDate < :checkOut AND b.checkOutDate > :checkIn))")
    List<BookingEntity> findOverlappingBookings(Long roomId, LocalDate checkIn, LocalDate checkOut);

    List<BookingEntity> findByUserId(Long userId);
}
