package com.example.hotelbooking.mapper;

import com.example.hotelbooking.model.Booking;
import com.example.hotelbooking.entity.BookingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    Booking entityToModel(BookingEntity entity);

    @Mapping(target = "bookingId", ignore = true)
    BookingEntity modelToEntity(Booking model);
}
