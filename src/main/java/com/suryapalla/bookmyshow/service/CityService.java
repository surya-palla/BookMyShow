package com.suryapalla.bookmyshow.service;


import com.suryapalla.bookmyshow.model.City;
import com.suryapalla.bookmyshow.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    public City saveCity(String cityName){
        City city = new City();
        city.setName(cityName);

        return cityRepository.save(city);
    }

    public City saveCity(City city){
        return cityRepository.save(city);
    }

    public boolean deleteCity(int cityId){
        cityRepository.deleteById(cityId);
        return true;
    }

    public City getCityByName(String cityName){
        return  cityRepository.findCityByName(cityName);
    }

    public City getCityById(Integer id){
        return cityRepository.findById(id).get();
    }

    public List<City> getAllCities(){
        return cityRepository.findAll();
    }
}
