package com.company.hotel1.screen.registrationcards;

import com.company.hotel1.entity.Apartment;
import com.company.hotel1.screen.client.ClientBrowse;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.component.Label;
import io.jmix.ui.component.Timer;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import com.company.hotel1.entity.RegistrationCards;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Convert;
import java.util.ArrayList;
import java.util.List;
@UiController("RegistrationCards.edit")
@UiDescriptor("registration-cards-edit.xml")
@EditedEntityContainer("registrationCardsDc")
public class RegistrationCardsEdit extends StandardEditor<RegistrationCards> {
    @Autowired
    private ComboBox<String> resultsOfPCRTestForCOVID19Field;
    @Autowired
    private EntityPicker<Apartment> apartmentField;
    @Autowired
    private Notifications notifications;
    protected int seconds = 0;
    @Subscribe
    public void onInit(InitEvent event) {
        List<String> list = new ArrayList<>();
        list.add("Отрицательный");
        list.add("Положительный");
        resultsOfPCRTestForCOVID19Field.setOptionsList(list);
    }
}