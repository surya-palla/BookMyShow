package com.suryapalla.bookmyshow.controller;

import com.suryapalla.bookmyshow.dto.CityRequestDTO;
import com.suryapalla.bookmyshow.model.City;
import com.suryapalla.bookmyshow.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class CityController {
    @Autowired
    private CityService cityService;

    @GetMapping("/city/{name}")
    public ResponseEntity getCityByName(@PathVariable("name") String cityName){
        return ResponseEntity.ok(cityService.getCityByName(cityName));
    }

    @GetMapping("/city")
    public ResponseEntity getCities(){
        return ResponseEntity.ok(cityService.getAllCities());
    }

    @PostMapping("/city")
    public ResponseEntity createCity(@RequestBody CityRequestDTO cityRequestDTO){
        try{
            String cityName = cityRequestDTO.getCityName();
            if(cityName == null || cityName.length() == 0 || cityName.isEmpty() || cityName.isBlank()){
                throw new Exception("City Name is invalid");
            }
            City city = cityService.saveCity(cityName);
            return ResponseEntity.ok(city);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/city/{id}")
    public ResponseEntity deleteCity(@PathVariable("id") int cityId){
        boolean isDeleted = cityService.deleteCity(cityId);
        return ResponseEntity.ok(isDeleted);
    }
}
