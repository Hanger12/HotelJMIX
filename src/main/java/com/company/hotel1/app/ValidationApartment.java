package com.company.hotel1.app;

import com.company.hotel1.entity.Apartment;
import io.jmix.ui.component.ValidationException;
import io.jmix.ui.component.validation.Validator;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("ApartmentNotBooking")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ValidationApartment implements Validator<Apartment> {

    @Override
    public void accept(@NotNull Apartment value) throws ValidationException {
        if ((value.getSignOfBooking() || value.getSignOfEmployment()) ) {
            throw new ValidationException("Данные апартаменты уже забронированы ли заняты");
        }
    }
}