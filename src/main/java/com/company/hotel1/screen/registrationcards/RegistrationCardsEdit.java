package com.company.hotel1.screen.registrationcards;

import io.jmix.ui.screen.*;
import com.company.hotel1.entity.RegistrationCards;

@UiController("RegistrationCards.edit")
@UiDescriptor("registration-cards-edit.xml")
@EditedEntityContainer("registrationCardsDc")
public class RegistrationCardsEdit extends StandardEditor<RegistrationCards> {
}