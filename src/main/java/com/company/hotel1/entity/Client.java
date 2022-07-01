package com.company.hotel1.entity;

import io.jmix.core.entity.annotation.EmbeddedParameters;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@JmixEntity
@Table(name = "CLIENT")
@Entity
public class Client {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "LIST_OF_REGISTRATION_CARDS")
    private Integer listOfRegistrationCards;

    @Column(name = "SURNAME", nullable = false)
    @NotNull
    private String surname;

    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    @Column(name = "MIDDLENAME")
    private String middlename;

    @EmbeddedParameters(nullAllowed = false)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "phone", column = @Column(name = "CONTACTS_PHONE")),
            @AttributeOverride(name = "mail", column = @Column(name = "CONTACTS_MAIL"))
    })
    private Contacts contacts;

    public Integer getListOfRegistrationCards() {
        return listOfRegistrationCards;
    }

    public void setListOfRegistrationCards(Integer listOfRegistrationCards) {
        this.listOfRegistrationCards = listOfRegistrationCards;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}