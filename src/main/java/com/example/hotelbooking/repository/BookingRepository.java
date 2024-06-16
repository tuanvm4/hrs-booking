package com.example.hotelbooking.repository;

import com.example.hotelbooking.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    /**
     * Handle check-in and check-out at the bound
     *
     * @param roomId
     * @param checkIn
     * @param checkOut
     * @return
     */
    @Query("SELECT b FROM BookingEntity b WHERE b.roomId = :roomId AND b.bookingStatus = 'CONFIRMED' AND ("
            + "(b.checkInDate <= :checkOut AND b.checkOutDate >= :checkIn))")
    List<BookingEntity> findOverlappingBookings(Long roomId, LocalDate checkIn, LocalDate checkOut);

    /**
     * Handle check-in and check-out at the upper-bound and lower-bound
     *
     * @param bookingId
     * @param roomId
     * @param checkIn
     * @param checkOut
     * @return
     */
    @Query("SELECT b FROM BookingEntity b WHERE b.roomId = :roomId AND b.bookingStatus = 'CONFIRMED' AND b.bookingId <> :bookingId AND ("
            + "b.checkInDate < :checkOut AND b.checkOutDate > :checkIn)")
    List<BookingEntity> findOverlappingBookingsExcludeCurrentBooking(Long bookingId, Long roomId, LocalDate checkIn, LocalDate checkOut);

    List<BookingEntity> findByUserId(Long userId);
}
