package com.suryapalla.bookmyshow.controller;

import com.suryapalla.bookmyshow.dto.TheatreRequestDTO;
import com.suryapalla.bookmyshow.service.TheatreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/theatre")
public class TheatreController {

    @Autowired
    private TheatreService theatreService;

    @GetMapping("/")
    public ResponseEntity<String> getTheatre() {
        log.info("getTheatre");
        return ResponseEntity.ok("Hello");
    }

    @PostMapping("/")
    public ResponseEntity createTheatre(@RequestBody TheatreRequestDTO theatreRequestDTO){
        log.info("In TheatreController createTheatre");
        return ResponseEntity.ok(
                theatreService.createTheatre(
                        theatreRequestDTO.getName(),
                        theatreRequestDTO.getAddress(),
                        theatreRequestDTO.getCityId()
                )
        );
    }

    @GetMapping("/city/{cityName}")
    public ResponseEntity getAllTheatre(@PathVariable String cityName){
        return ResponseEntity.ok(theatreService.getTheatreByCity(cityName));
    }

}
