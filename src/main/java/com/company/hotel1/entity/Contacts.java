package com.company.hotel1.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@JmixEntity
@Embeddable
public class Contacts {
    @Column(name = "PHONE", nullable = false)
    @NotNull
    private String phone;

    @Column(name = "MAIL", nullable = false)
    @NotNull
    private String mail;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}