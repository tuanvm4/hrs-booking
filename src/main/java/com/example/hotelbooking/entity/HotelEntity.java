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

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hotel", schema = "hotel_booking", catalog = "postgres")
public class HotelEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "hotel_id")
    private long hotelId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "city")
    private String city;
    @Basic
    @Column(name = "state")
    private String state;
    @Basic
    @Column(name = "zip_code")
    private String zipCode;
    @Basic
    @Column(name = "phone_number")
    private String phoneNumber;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Basic
    @Column(name = "modified_at")
    private Timestamp modifiedAt;
}
