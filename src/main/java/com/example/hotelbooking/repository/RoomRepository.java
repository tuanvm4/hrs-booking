package com.example.hotelbooking.repository;

import com.example.hotelbooking.entity.HotelEntity;
import com.example.hotelbooking.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    /**
     * Default status in the Not In query is CONFIRMED. That mean select only available room without Booked room on specified date range
     * @param hotelId
     * @param checkInDate
     * @param checkOutDate
     * @return
     */
    @Query("SELECT r FROM RoomEntity r WHERE r.hotelId = :hotelId AND r.isAvailable = true AND r.roomId NOT IN " +
            "(SELECT b.roomId FROM BookingEntity b WHERE b.bookingStatus = 'CONFIRMED' AND b.checkInDate <= :checkOutDate AND b.checkOutDate >= :checkInDate)")
    List<RoomEntity> findAvailableRooms(Long hotelId, LocalDate checkInDate, LocalDate checkOutDate);
}
