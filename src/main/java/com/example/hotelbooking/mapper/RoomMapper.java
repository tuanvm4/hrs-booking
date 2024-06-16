package com.example.hotelbooking.mapper;

import com.example.hotelbooking.entity.HotelEntity;
import com.example.hotelbooking.entity.RoomEntity;
import com.example.hotelbooking.model.Hotel;
import com.example.hotelbooking.model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    Room entityToModel(RoomEntity entity);
    RoomEntity modelToEntity(Room model);
}
