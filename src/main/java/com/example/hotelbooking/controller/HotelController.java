package com.example.hotelbooking.controller;

import com.example.hotelbooking.model.Hotel;
import com.example.hotelbooking.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Hotel", description = "APIs related to hotel operations")
@RestController
@AllArgsConstructor
@RequestMapping("/hotels")
public class HotelController {

    private final HotelService hotelService;

    @Operation(summary = "Search hotels by location", description = "Retrieves a list of hotels filtered by city. If no city is provided, returns all hotels.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotels retrieved successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Hotel.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/search")
    public ResponseEntity<List<Hotel>> searchHotelsByLocation(
            @Parameter(description = "City to filter hotels by", required = false) @RequestParam(required = false) String city
    ) {
        List<Hotel> hotels;
        if (city != null) {
            hotels = hotelService.findHotelsByCity(city);
        } else {
            // Handle invalid request, e.g., return all hotels
            hotels = hotelService.getAllHotels();
        }
        return ResponseEntity.ok(hotels);
    }
}