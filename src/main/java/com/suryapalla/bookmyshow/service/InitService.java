package com.suryapalla.bookmyshow.service;

import com.suryapalla.bookmyshow.model.*;
import com.suryapalla.bookmyshow.model.constant.AuditoriumFeature;
import com.suryapalla.bookmyshow.model.constant.SeatStatus;
import com.suryapalla.bookmyshow.model.constant.SeatType;
import com.suryapalla.bookmyshow.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class InitService {
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private TheatreRepository theatreRepository;
    @Autowired
    private AuditoriumRepository auditoriumRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserRepository userRepository;


    public boolean initialise(){
        City kakinada = new City();
        kakinada.setName("Kakinada");
        City bangalore = new City();
        bangalore.setName("Bangalore");
        City canberra = new City();
        canberra.setName("Canberra");

        kakinada = cityRepository.save(kakinada);
        bangalore = cityRepository.save(bangalore);
        canberra = cityRepository.save(canberra);

        City savedKakinada = cityRepository.findCityByName("Kakinada");
        System.out.println(savedKakinada.getTheatres());
        Theatre anand = new Theatre("Anand Multiplex", "Banugudi, Kakinada");
        Theatre padmaPriya = new Theatre("Padma Priya Theatre", "Banugudi, Kakinada");
        Theatre devi = new Theatre("Devi Multiplex", "Cinema Road, Kakinada");
        Theatre tirumala = new Theatre("Thirumala Theatre", "Main road, Kakinada");

        anand = theatreRepository.save(anand);
        padmaPriya = theatreRepository.save(padmaPriya);
        devi = theatreRepository.save(devi);
        tirumala = theatreRepository.save(tirumala);

        List<Theatre> kakinadaTheatres = new ArrayList<>();
        kakinadaTheatres.add(anand);
        kakinadaTheatres.add(padmaPriya);
        kakinadaTheatres.add(devi);
        kakinadaTheatres.add(tirumala);
        savedKakinada.setTheatres(kakinadaTheatres);
        cityRepository.save(savedKakinada);


        Auditorium auditorium = new Auditorium();
        auditorium.setName("Screen 1");
        auditorium.setCapacity(5);
        auditorium.setAuditoriumFeatures(List.of(AuditoriumFeature.DOLBY, AuditoriumFeature.INOX));


        List<Seat> savedSeatList = new ArrayList<>();
        for(int i=1; i<=5; i++){
            Seat a = new Seat();
            a.setColumn_num(i);
            a.setRow_num(1);
            a.setSeatNumber("A"+i);
            a.setSeatType(SeatType.GOLD);
            a.setStatus(SeatStatus.AVAILABLE);
            savedSeatList.add(seatRepository.save(a));
        }

        auditorium.setSeats(savedSeatList);
        auditoriumRepository.save(auditorium);

        anand = theatreRepository.findById(anand.getId()).get();
        anand.setAuditoriums(auditoriumRepository.findAll());

        theatreRepository.save(anand);

        Movie hanuman = new Movie();
        hanuman.setName("Hanuman"); hanuman.setDescription("An Indian Superhero movie");
        movieRepository.save(hanuman);

        Event event = new Event();
        event.setStartTime(LocalDateTime.now());
        event.setEndTime(LocalDateTime.now().plusHours(3));
        event.setMovie(movieRepository.findMovieByName(hanuman.getName()));
        event.setAuditorium(auditoriumRepository.findAuditoriumByName(auditorium.getName()));

        eventRepository.save(event);
        return true;
    }
}
