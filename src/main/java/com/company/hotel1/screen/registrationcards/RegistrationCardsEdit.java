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
    @Autowired
    private DateField<LocalDateTime> departureDateField;
    @Autowired
    private DateField<LocalDateTime> arrivalDateField;

    // при инициализации заполнить OptionField не обходимыми данными и установить
    // что пользователь не может оплатить или предоплатить в будущем времени или
    // в прошедшем времени, также, установить,что пользователь не может выбрать дату заселения и выезда в прошедшем времени
    @Subscribe
    public void onInit(InitEvent event) {
        List<String> list = new ArrayList<>();
        list.add("Отрицательный");
        list.add("Положительный");
        resultsOfPCRTestForCOVID19Field.setOptionsList(list);
        LocalDate date = LocalDate.now();
        LocalDateTime dateTime = LocalDateTime.now();
        paymentDateField.setRangeStart(date);
        paymentDateField.setRangeEnd(date);
        prepaymentDateField.setRangeStart(date);
        prepaymentDateField.setRangeEnd(date);
        arrivalDateField.setRangeStart(dateTime);
        departureDateField.setRangeStart(dateTime);
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        RegistrationCards registrationCards = getEditedEntity();
        // блокируем все поля с учетом условий.
        if(serviceRegistrationCards.fidByID(registrationCards.getId())!=null)
        {
            if(!serviceRegistrationCards.fidByID(registrationCards.getId()).getPaymentIndication())
            {
                Objects.requireNonNull(getWindow().getComponent("arrivalDateField")).setEnabled(false);
                Objects.requireNonNull(getWindow().getComponent("departureDateField")).setEnabled(false);
            }
            else
            {
                Objects.requireNonNull(getWindow().getComponent("paymentDateField")).setEnabled(false);
                Objects.requireNonNull(getWindow().getComponent("paymentIndicationField")).setEnabled(false);
            }
            if(serviceRegistrationCards.fidByID(registrationCards.getId()).getIndicationOfPrepayment())
            {
                Objects.requireNonNull(getWindow().getComponent("indicationOfPrepaymentField")).setEnabled(false);
                Objects.requireNonNull(getWindow().getComponent("prepaymentDateField")).setEnabled(false);
            }
            if(registrationCards.getApartment()!=null)
            {
                Objects.requireNonNull(getWindow().getComponent("apartmentField")).setEnabled(false);
            }
            if(registrationCards.getClient()!=null)
            {
                Objects.requireNonNull(getWindow().getComponent("clientField")).setEnabled(false);
            }
            if(serviceRegistrationCards.fidByID(registrationCards.getId()).getResultsOfPCRTestForCOVID19()!=null)
            {
                Objects.requireNonNull(getWindow().getComponent("resultsOfPCRTestForCOVID19Field")).setEnabled(false);
            }
        }
        Objects.requireNonNull(getWindow().getComponent("creationDateField")).setEnabled(false);

    }
    // при создании новой сущности установить дату создания
    @Subscribe
    public void onInitEntity(InitEntityEvent<RegistrationCards> event) {
        LocalTime time = LocalTime.now();
        event.getEntity().setCreationDate(time);

    }
    //проверка если номер уже забронирован то клиент не может его забронировать повторно
    @Subscribe("apartmentField")
    public void onApartmentFieldValueChange(HasValue.ValueChangeEvent<Apartment> event) {
        try {
            if(serviceRegistrationCards.findRegistratioCards(Objects.requireNonNull(event.getValue()).getNumber()).getApartment().getNumber()!=null
                    &&event.isUserOriginated()
                    &&serviceRegistrationCards.findRegistratioCards(Objects.requireNonNull(event.getValue()).getNumber()).getApartment().getSignOfBooking()){
                event.getComponent().clear();
                dialogs.createMessageDialog().withCaption("").withMessage("Данный номер уже забронированирован").show();

            }
        }
        catch (NullPointerException exception)
        {
            log.info("");
        }

    }

}