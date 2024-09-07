package com.suryapalla.bookmyshow.service;

import com.suryapalla.bookmyshow.model.ShowSeat;
import com.suryapalla.bookmyshow.model.Theatre;
import com.suryapalla.bookmyshow.model.Ticket;
import com.suryapalla.bookmyshow.model.constant.ShowSeatStatus;
import com.suryapalla.bookmyshow.repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    private ShowSeatService showSeatService;


    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Ticket bookTicket(List<Integer> showSeatIds, Integer userId) throws Exception{
        for(Integer showSeatId : showSeatIds){
            ShowSeat showSeat = showSeatService.getShowSeat(showSeatId);
            if(!showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE)){
                throw new Exception("Seat no: "+showSeat.getSeat().getSeatNumber()+" is not available anymore");
            }
        }

        for(Integer showSeatId: showSeatIds){
            ShowSeat showSeat = showSeatService.getShowSeat(showSeatId);
            showSeat.setShowSeatStatus(ShowSeatStatus.LOCKED);
            showSeatService.saveShowSeat(showSeat);
        }

        //logic for payment flow
        startPayment(showSeatIds);
        return new Ticket();
    }

    public boolean startPayment(List<Integer> showSeatids){
        return true;
    }
    public String greet(){
        return "Hello world!";
    }


}
