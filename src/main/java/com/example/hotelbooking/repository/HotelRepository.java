package com.example.hotelbooking.repository;

import com.example.hotelbooking.entity.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<HotelEntity, Long> {

    List<HotelEntity> findByCityContainingIgnoreCase(String city);

}
