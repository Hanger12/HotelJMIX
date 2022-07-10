package com.company.hotel1.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@JmixEntity
@Table(name = "APARTMENT")
@Entity
public class Apartment {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @Column(name = "NUMBER_", nullable = false, unique = true)
    @NotNull
    private Integer number;

    @Column(name = "SIGN_OF_BOOKING")
    private Boolean signOfBooking = false;

    @Column(name = "SIGN_OF_EMPLOYMENT")
    private Boolean signOfEmployment = false;

    public Boolean getSignOfEmployment() {
        return signOfEmployment;
    }

    public void setSignOfEmployment(Boolean signOfEmployment) {
        this.signOfEmployment = signOfEmployment;
    }

    public Boolean getSignOfBooking() {
        return signOfBooking;
    }

    public void setSignOfBooking(Boolean signOfBooking) {
        this.signOfBooking = signOfBooking;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}