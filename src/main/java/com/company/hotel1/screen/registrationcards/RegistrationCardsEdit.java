package com.company.hotel1.screen.registrationcards;

import com.company.hotel1.app.BookingService;
import com.company.hotel1.app.ServiceRegistrationCards;
import com.company.hotel1.entity.Apartment;
import com.company.hotel1.screen.client.ClientBrowse;
import io.jmix.core.DataManager;
import io.jmix.core.TimeSource;
import io.jmix.ui.Dialogs;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.component.*;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import com.company.hotel1.entity.RegistrationCards;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Convert;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@UiController("RegistrationCards.edit")
@UiDescriptor("registration-cards-edit.xml")
@EditedEntityContainer("registrationCardsDc")
public class RegistrationCardsEdit extends StandardEditor<RegistrationCards> {
    private static final Logger log = LoggerFactory.getLogger(RegistrationCardsEdit.class);
    @Autowired
    private ComboBox<String> resultsOfPCRTestForCOVID19Field;
    @Autowired
    ServiceRegistrationCards serviceRegistrationCards;
    @Autowired
    private TimeSource timeSource;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private Dialogs dialogs;
    @Autowired
    private DateField<LocalDate> paymentDateField;
    @Autowired
    private DateField<LocalDate> prepaymentDateField;

    @Subscribe
    public void onInit(InitEvent event) {
        List<String> list = new ArrayList<>();
        list.add("Отрицательный");
        list.add("Положительный");
        resultsOfPCRTestForCOVID19Field.setOptionsList(list);
        LocalDate date = LocalDate.now();
        paymentDateField.setRangeStart(date);
        paymentDateField.setRangeEnd(date);
        prepaymentDateField.setRangeStart(date);
        prepaymentDateField.setRangeEnd(date);
    }

    @Subscribe
    public void onInitEntity(InitEntityEvent<RegistrationCards> event) {
        LocalTime time = LocalTime.now();
        event.getEntity().setCreationDate(time);
    }

    @Subscribe("apartmentField")
    public void onApartmentFieldValueChange(HasValue.ValueChangeEvent<Apartment> event) {
        try {
            if(serviceRegistrationCards.findRegistratioCards(Objects.requireNonNull(event.getValue()).getNumber()).getApartment().getNumber()!=null&&event.isUserOriginated()){
                event.getComponent().clear();
                dialogs.createMessageDialog().withCaption("").withMessage("Данный номер уже забронированирован").show();

            }
        }
        catch (NullPointerException exception)
        {
            log.info("");
        }

    /*@Install(to = "apartmentField", subject = "validator")
    private void apartmentFieldValidator(Apartment value) {
        if ((value.getSignOfBooking() || value.getSignOfEmployment())) {
            throw new ValidationException("Данные апартаменты уже забронированы или заняты");
        }
     */


    }

}