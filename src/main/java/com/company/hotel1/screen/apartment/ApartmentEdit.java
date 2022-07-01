package com.company.hotel1.screen.apartment;

import io.jmix.ui.screen.*;
import com.company.hotel1.entity.Apartment;

@UiController("Apartment.edit")
@UiDescriptor("apartment-edit.xml")
@EditedEntityContainer("apartmentDc")
public class ApartmentEdit extends StandardEditor<Apartment> {
}