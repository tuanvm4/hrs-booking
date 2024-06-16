package com.example.hotelbooking.mapper;

import com.example.hotelbooking.entity.HotelEntity;
import com.example.hotelbooking.model.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    Hotel entityToModel(HotelEntity entity);

    @Mapping(target = "hotelId", ignore = true)
    HotelEntity modelToEntity(Hotel model);

    List<Hotel> entitiesToModels(List<HotelEntity> entities);

    List<HotelEntity> modelsToEntities(List<Hotel> models);
}
