package com.suryapalla.bookmyshow.repository;

import com.suryapalla.bookmyshow.model.City;
import com.suryapalla.bookmyshow.model.Theatre;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    City findCityByName(String name);

    @Query("SELECT c.theatres FROM City c WHERE c.name = :cityName")
    List<Theatre> findTheatresByCityName(@Param("cityName") String cityName);
}
