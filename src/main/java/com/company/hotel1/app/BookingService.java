package com.company.hotel1.app;

import com.company.hotel1.entity.Apartment;
import com.company.hotel1.entity.RegistrationCards;
import com.company.hotel1.screen.client.ClientBrowse;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.event.EntityChangedEvent;
import io.jmix.core.event.EntitySavingEvent;
import io.jmix.core.querycondition.PropertyCondition;
import org.postgresql.util.PSQLException;
import org.postgresql.util.PSQLState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class BookingService implements MyInterface{
    private static final Logger log = LoggerFactory.getLogger(BookingService.class);
    @Autowired
    private DataManager dataManager;

    //при сохранении Карточки регистрации атоматически бронировать номер и заполнять пустые поля связанные с оплатой и предоплатой
    @EventListener
    public void onRegistrationCardsSaving(EntitySavingEvent<RegistrationCards> event) {
        Apartment apartment;
        apartment = event.getEntity().getApartment();
        apartment.setSignOfBooking(true);
        if (event.getEntity().getIndicationOfPrepayment() == null) {
            event.getEntity().setIndicationOfPrepayment(false);
        }
        if (event.getEntity().getPaymentIndication() == null)
        {
            event.getEntity().setPaymentIndication(false);
        }
        if(event.getEntity().getPaymentIndication()&&event.getEntity().getPaymentDate()==null)
        {
            event.getEntity().setIndicationOfPrepayment(true);
            apartment.setSignOfEmployment(true);
            event.getEntity().setPaymentDate(LocalDate.now());
            event.getEntity().setPrepaymentDate(LocalDate.now());
            log.info(event.getEntity().getPaymentIndication().toString());
        }
        else if(event.getEntity().getPaymentIndication()&&event.getEntity().getPaymentDate()!=null)
        {
            event.getEntity().setIndicationOfPrepayment(true);
            apartment.setSignOfEmployment(true);
            event.getEntity().setPrepaymentDate(LocalDate.now());
        }
        if(event.getEntity().getIndicationOfPrepayment()&&event.getEntity().getPrepaymentDate()==null)
        {
            event.getEntity().setPrepaymentDate(LocalDate.now());
        }
        dataManager.save(apartment);
    }

    //При сохранении апартамента проверять не пустые ли ячейки бронирования и занятости
    @EventListener
    public void onApartmentSaving(EntitySavingEvent<Apartment> event) {
        if(event.getEntity().getSignOfBooking()==null)
        {
            event.getEntity().setSignOfBooking(false);
        }
        if(event.getEntity().getSignOfEmployment()==null)
        {
            event.getEntity().setSignOfEmployment(false);
        }
    }

    //при ручном удалении Карточки регистрации сбрасывать бронирование номера
    @EventListener
    public void onRegistrationCardsChangedBeforeCommit(EntityChangedEvent<RegistrationCards> event) {
        Apartment apartment;
        if(event.getType()==EntityChangedEvent.Type.DELETED)
        {
            Id<Apartment> apartmentId = event.getChanges().getOldReferenceId("apartment");
            assert apartmentId != null;
            log.info(apartmentId.toString());
            apartment= dataManager.load(apartmentId).one();
            log.info(apartment.getNumber().toString());
            apartment.setSignOfBooking(false);
            apartment.setSignOfEmployment(false);
            dataManager.save(apartment);
        }

    }
    

    @Override
    public void findRegistrationCardsTest() {
        log.info("ItsWork");
    }
}