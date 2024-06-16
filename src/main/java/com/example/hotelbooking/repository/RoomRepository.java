package com.example.hotelbooking.repository;

import com.example.hotelbooking.entity.HotelEntity;
import com.example.hotelbooking.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {

}
