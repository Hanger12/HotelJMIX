package com.company.hotel1.screen.registrationcards;

import io.jmix.ui.screen.*;
import com.company.hotel1.entity.RegistrationCards;

@UiController("RegistrationCards.browse")
@UiDescriptor("registration-cards-browse.xml")
@LookupComponent("registrationCardsesTable")
public class RegistrationCardsBrowse extends StandardLookup<RegistrationCards> {
}