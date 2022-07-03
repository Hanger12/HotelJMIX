package com.company.hotel1.app;

import com.company.hotel1.entity.Apartment;
import com.company.hotel1.entity.Client;
import com.company.hotel1.entity.RegistrationCards;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.event.EntityChangedEvent;
import io.jmix.core.event.EntityLoadingEvent;
import io.jmix.core.event.EntitySavingEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookingService {
    @Autowired
    private DataManager dataManager;

    @EventListener
    public void onRegistrationCardsSaving(EntitySavingEvent<RegistrationCards> event) {
        Apartment apartment;
        apartment = event.getEntity().getApartment();
        if(apartment.getSignOfBooking())
        {

        }
        apartment.setSignOfBooking(true);
        dataManager.save(apartment);
    }


}