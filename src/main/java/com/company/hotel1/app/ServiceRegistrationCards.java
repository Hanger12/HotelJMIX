package com.company.hotel1.app;

import com.company.hotel1.entity.Apartment;
import com.company.hotel1.entity.RegistrationCards;
import com.company.hotel1.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.core.security.Authenticated;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.core.security.SystemAuthenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Service
public class ServiceRegistrationCards {
    private static final Logger log = LoggerFactory.getLogger(ServiceRegistrationCards.class);
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
    public List<RegistrationCards> loadByCondition() {
        return dataManager.load(RegistrationCards.class)
                .condition(PropertyCondition.contains("resultsOfPCRTestForCOVID19", "Положительный"))
                .list();
    }
    public List<RegistrationCards> deleteOfPrepaymentFalse()
    {
        return dataManager.load(RegistrationCards.class).query("select r from RegistrationCards r where r.indicationOfPrepayment=false")
                .list();
    }
    public RegistrationCards findRegistrationCards(Apartment apartment)
    {
        return dataManager.load(RegistrationCards.class).query("select r from RegistrationCards r where r.apartment.number="+apartment.getNumber()).one();
    }
    private final MyInterface myInterface;
    @Authenticated
    @ManagedOperation
    @Scheduled(fixedRate = 15000)
    public void findRegistrationCardsTest() {
        systemAuthenticator.withSystem(() -> {
            UserDetails user = currentAuthentication.getUser();
            log.info("User: " + user.getUsername()); // system
            // ...
            return "Done";
        });
        log.info("Find new article job started.");
        if(deleteOfPrepaymentFalse()!=null&&currentAuthentication.isSet())
        {
            for (RegistrationCards r: deleteOfPrepaymentFalse())
            {
                if(r.getCreationDate().getHour()==LocalTime.now().getHour()
                        &&LocalTime.now().getMinute()-r.getCreationDate().getMinute()>=2)
                {
                    r.getApartment().setSignOfBooking(false);
                    r.getApartment().setSignOfEmployment(false);
                    dataManager.remove(r);
                }

            }
        }
    }
}