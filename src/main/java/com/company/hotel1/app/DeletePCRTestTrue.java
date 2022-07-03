package com.company.hotel1.app;

import com.company.hotel1.entity.Apartment;
import com.company.hotel1.entity.Client;
import com.company.hotel1.entity.RegistrationCards;
import io.jmix.core.DataManager;
import io.jmix.core.entity.KeyValueEntity;
import io.jmix.core.querycondition.PropertyCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeletePCRTestTrue {
    @Autowired
    private DataManager dataManager;

    public List<RegistrationCards> loadByCondition() {
        return dataManager.load(RegistrationCards.class)
                .condition(PropertyCondition.contains("resultsOfPCRTestForCOVID19", "Положительный"))
                .list();
    }
}