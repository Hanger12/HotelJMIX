package com.company.hotel1.app;

import com.company.hotel1.entity.Apartment;
import com.company.hotel1.entity.RegistrationCards;
import com.company.hotel1.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.core.security.Authenticated;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.core.security.SystemAuthenticator;
import io.jmix.ui.Dialogs;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.DialogAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ServiceRegistrationCards {
    private static final Logger log = LoggerFactory.getLogger(ServiceRegistrationCards.class);
    @Autowired
    private ObjectProvider<Dialogs> dialogsProvider;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    public ServiceRegistrationCards(MyInterface myInterface) {
        this.myInterface = myInterface;
    }
    @Autowired
    private SystemAuthenticator systemAuthenticator;
    //за прос на наличие положительного теста на коронавирус
    public List<RegistrationCards> loadByCondition() {
        return dataManager.load(RegistrationCards.class)
                .condition(PropertyCondition.contains("resultsOfPCRTestForCOVID19", "Положительный"))
                .list();
    }
    // запрос на поиск не предоплативших клиентов в регистрационной карте
    public List<RegistrationCards> deleteOfPrepaymentFalse()
    {
        return dataManager.load(RegistrationCards.class).query("select r from RegistrationCards r where r.indicationOfPrepayment=false")
                .list();
    }
    //запрос в базу по номеру апартамента
    public RegistrationCards findRegistratioCards(Integer number)
    {
        RegistrationCards registrationCards = new RegistrationCards();
        try {
            registrationCards=dataManager.load(RegistrationCards.class).query("select r from RegistrationCards r where r.apartment.number="+number).one();
        }
        catch (IllegalStateException exception)
        {
            log.info("");
        }
        return registrationCards;
    }
    //запрос в базу по id
    public RegistrationCards fidByID(UUID id)
    {
        return dataManager
                .loadValues("select r from RegistrationCards r where r.id='"+id.toString()+"'")
                .properties("RegistrationCards")
                .list()
                .stream()
                .map(e->e.<RegistrationCards>getValue("RegistrationCards"))
                .findFirst()
                .orElse(null);
    }

    private final MyInterface myInterface;
    @Authenticated
    @ManagedOperation
    @Scheduled(fixedRate = 15000)
    public void findRegistrationCardsTest() {
        //предоставление доступа системному пользовтелю
        systemAuthenticator.withSystem(() -> {
            UserDetails user = currentAuthentication.getUser();
            log.info("User: " + user.getUsername()); // system
            // ...
            return "Done";
        });
        log.info("Job is working.");
        //проверка на точность jpql запроса и если аутификация системного пользователя прошла успешно
        if(deleteOfPrepaymentFalse()!=null&&currentAuthentication.isSet())
        {
            //проходимся по листу
            for (RegistrationCards r: deleteOfPrepaymentFalse())
            {
                // сравниваем объекты на разницу времени создания и текущем временем в данном случае 2 минуты (можно также работать с датой)
                if(r.getCreationDate().getHour()==LocalTime.now().getHour()
                        &&LocalTime.now().getMinute()-r.getCreationDate().getMinute()>=2)
                {
                    r.getApartment().setSignOfBooking(false);
                    r.getApartment().setSignOfEmployment(false);
                    dataManager.save(r.getApartment());
                    //удаляем карточку регистрации если предоплата не произведена в течении 2 минут(можно также работать с датой)
                    dataManager.remove(r);
                }

            }
        }
    }
}