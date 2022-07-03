package com.company.hotel1.app;

import com.company.hotel1.entity.Apartment;
import com.company.hotel1.entity.RegistrationCards;
import io.jmix.core.DataManager;
import io.jmix.core.event.EntitySavingEvent;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.Timer;

@Service
public class BookingService {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(BookingService.class);
    @Autowired
    private DataManager dataManager;
    Timer timer;
    public void Alarm(long date) {

        timer = new Timer();
        timer.schedule(new AlarmTask(), date);
    }
    class AlarmTask extends TimerTask {
        private final Logger log = org.slf4j.LoggerFactory.getLogger(AlarmTask.class);
        public void run() {
            log.info("Wake up!!!");
            timer.cancel();

        }
    }
    @EventListener
    public void onRegistrationCardsSaving(EntitySavingEvent<RegistrationCards> event) {
        Apartment apartment;
        RegistrationCards registrationCards;
        apartment = event.getEntity().getApartment();
        registrationCards = event.getEntity();
        if(registrationCards.getIndicationOfPrepayment()==null)
        {
            registrationCards.setIndicationOfPrepayment(false);
        }
        log.info(registrationCards.getIndicationOfPrepayment().toString());
        if(!registrationCards.getIndicationOfPrepayment())
        {
            LocalDate date = LocalDate.now();
            int year = date.getYear();
            int month = date.getMonthValue();
            int NextDayOfMonth = date.getDayOfMonth()+1;
            Calendar calendar =new GregorianCalendar(2022, Calendar.JULY,3,19,15);
            log.info(calendar.getTime().toString());
            log.info(Integer.toString(NextDayOfMonth));
        }
        apartment.setSignOfBooking(true);
        dataManager.save(apartment);
    }
}