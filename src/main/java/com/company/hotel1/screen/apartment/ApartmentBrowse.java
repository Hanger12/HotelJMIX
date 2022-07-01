package com.company.hotel1.screen.apartment;

import io.jmix.ui.screen.*;
import com.company.hotel1.entity.Apartment;

@UiController("Apartment.browse")
@UiDescriptor("apartment-browse.xml")
@LookupComponent("apartmentsTable")
public class ApartmentBrowse extends StandardLookup<Apartment> {
}