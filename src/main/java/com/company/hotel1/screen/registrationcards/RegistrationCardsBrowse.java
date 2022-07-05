package com.company.hotel1.screen.registrationcards;

import com.company.hotel1.app.BookingService;
import com.company.hotel1.app.ServiceRegistrationCards;
import com.company.hotel1.entity.Apartment;
import io.jmix.core.DataManager;
import io.jmix.core.entity.KeyValueEntity;
import io.jmix.ui.screen.*;
import com.company.hotel1.entity.RegistrationCards;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;


@UiController("RegistrationCards.browse")
@UiDescriptor("registration-cards-browse.xml")
@LookupComponent("registrationCardsesTable")
public class RegistrationCardsBrowse extends StandardLookup<RegistrationCards> {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(RegistrationCardsBrowse.class);
    @Autowired
    private DataManager dataManager;
    @Autowired
    private ServiceRegistrationCards deletePCRTestTrue;
    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        for (RegistrationCards r:deletePCRTestTrue.loadByCondition())
        {
            r.getApartment().setSignOfBooking(false);
            r.getApartment().setSignOfEmployment(false);
            dataManager.save(r.getApartment());
            dataManager.remove(r);
        }
    }



}