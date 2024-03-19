package com.suryapalla.bookmyshow.service;

import com.suryapalla.bookmyshow.model.City;
import com.suryapalla.bookmyshow.model.Theatre;
import com.suryapalla.bookmyshow.repository.CityRepository;
import com.suryapalla.bookmyshow.repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheatreService {

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private CityService cityService;

    public Theatre createTheatre(String name, String address, Integer cityId){
        Theatre theatre = new Theatre();
        theatre.setName(name);
        theatre.setAddress(address);
        Theatre savedTheatre = theatreRepository.save(theatre);

        City city = cityService.getCityById(cityId);
        city.getTheatres().add(savedTheatre);
        cityService.saveCity(city);
        return savedTheatre;
    }
}
