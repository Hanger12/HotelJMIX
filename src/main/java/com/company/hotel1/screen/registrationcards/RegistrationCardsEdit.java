package com.company.hotel1.screen.registrationcards;

import com.company.hotel1.app.BookingService;
import com.company.hotel1.app.ServiceRegistrationCards;
import com.company.hotel1.entity.Apartment;
import com.company.hotel1.screen.client.ClientBrowse;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.component.*;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import com.company.hotel1.entity.RegistrationCards;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Convert;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@UiController("RegistrationCards.edit")
@UiDescriptor("registration-cards-edit.xml")
@EditedEntityContainer("registrationCardsDc")
public class RegistrationCardsEdit extends StandardEditor<RegistrationCards> {
    private static final Logger log = LoggerFactory.getLogger(RegistrationCardsEdit.class);
    @Autowired
    private ComboBox<String> resultsOfPCRTestForCOVID19Field;
    @Autowired
    private EntityPicker<Apartment> apartmentField;
    @Autowired
    private Notifications notifications;
    @Autowired
    ServiceRegistrationCards serviceRegistrationCards;

    protected int seconds = 0;
    @Subscribe
    public void onInit(InitEvent event) {
        List<String> list = new ArrayList<>();
        list.add("Отрицательный");
        list.add("Положительный");
        resultsOfPCRTestForCOVID19Field.setOptionsList(list);
        
    }
    @Subscribe
    public void onInitEntity(InitEntityEvent<RegistrationCards> event) {
        LocalTime time = LocalTime.now();
        event.getEntity().setCreationDate(time);
    }

    /*@Install(to = "apartmentField", subject = "validator")
    private void apartmentFieldValidator(Apartment value) {
        if ((value.getSignOfBooking() || value.getSignOfEmployment())) {
            throw new ValidationException("Данные апартаменты уже забронированы или заняты");
        }
    }

     */




    

}