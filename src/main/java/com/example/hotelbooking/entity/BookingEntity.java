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

import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "booking", schema = "hotel_booking", catalog = "postgres")
public class BookingEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "booking_id")
    private long bookingId;
    @Basic
    @Column(name = "user_id")
    private long userId;
    @Basic
    @Column(name = "room_id")
    private long roomId;
    @Basic
    @Column(name = "check_in_date")
    private LocalDate checkInDate;
    @Basic
    @Column(name = "check_out_date")
    private LocalDate checkOutDate;
    @Basic
    @Column(name = "booking_status")
    private String bookingStatus;
    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Basic
    @Column(name = "modified_at")
    private Timestamp modifiedAt;
}
