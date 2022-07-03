package com.company.hotel1.app;

import com.company.hotel1.entity.Client;
import io.jmix.core.DataManager;
import io.jmix.core.entity.KeyValueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationCardCount {
    @Autowired
    private DataManager dataManager;

    public List<KeyValueEntity> loadSorted() {
        return dataManager.loadValues("select count(r.id) as listcount from RegistrationCards r where r.resultsOfPCRTestForCOVID19='Отрицательный'").properties("listcount")
                .list();
    }
}