package com.example.hotelbooking.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "room", schema = "hotel_booking", catalog = "postgres")
public class RoomEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "room_id")
    private long roomId;
    @Basic
    @Column(name = "hotel_id")
    private long hotelId;
    @Basic
    @Column(name = "room_number")
    private String roomNumber;
    @Basic
    @Column(name = "room_type")
    private String roomType;
    @Basic
    @Column(name = "price_per_night")
    private BigDecimal pricePerNight;
    @Basic
    @Column(name = "is_available")
    private Boolean isAvailable;
    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Basic
    @Column(name = "modified_at")
    private Timestamp modifiedAt;
}
