package com.company.hotel1.screen.registrationcards;

import com.company.hotel1.entity.Apartment;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.screen.*;
import com.company.hotel1.entity.RegistrationCards;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Autowired
    private ScreenValidation screenValidation;

    @Subscribe
    public void onInit(InitEvent event) {
        List<String> list = new ArrayList<>();
        list.add("Отрицательный");
        list.add("Положительный");
        resultsOfPCRTestForCOVID19Field.setOptionsList(list);

        apartmentField.addAction(new BaseAction("showLevel")
                .withCaption(null)
                .withDescription(null)
                .withIcon(JmixIcon.VIEW_ACTION.source())
                .withHandler(e -> {
                    Apartment apartment = apartmentField.getValue();
                    assert apartment != null;
                    if (!apartment.getSignOfBooking()) {

                        notifications.create()
                                .withCaption(apartment.getNumber() + " " +
                                        apartment.getSignOfBooking() +
                                        "'s level is " + apartment.getSignOfEmployment())
                                .show();
                    } else {
                        notifications.create()
                                .withCaption("Choose a customer")
                                .show();
                    }
                }));
    }
    
}