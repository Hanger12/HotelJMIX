package com.company.hotel1.app;

import com.company.hotel1.entity.Apartment;
import com.company.hotel1.entity.RegistrationCards;
import com.company.hotel1.screen.client.ClientBrowse;
import io.jmix.core.DataManager;
import io.jmix.core.event.EntityChangedEvent;
import io.jmix.core.event.EntitySavingEvent;
import io.jmix.core.querycondition.PropertyCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class BookingService implements MyInterface{
    private static final Logger log = LoggerFactory.getLogger(BookingService.class);
    @Autowired
    private DataManager dataManager;

    @EventListener
    public void onRegistrationCardsSaving(EntitySavingEvent<RegistrationCards> event) {
        Apartment apartment;
        apartment = event.getEntity().getApartment();
        apartment.setSignOfBooking(true);
        if (event.getEntity().getIndicationOfPrepayment()==null&&event.getEntity().getPaymentIndication()==null)
        {
            event.getEntity().setIndicationOfPrepayment(false);
            event.getEntity().setPaymentIndication(false);
        }
        log.info(event.getEntity().getIndicationOfPrepayment().toString());
        dataManager.save(apartment);

    }

    @Override
    public void findRegistrationCardsTest() {
        log.info("ItsWork");
    }
}