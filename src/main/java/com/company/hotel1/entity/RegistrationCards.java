package com.company.hotel1.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@JmixEntity
@Table(name = "REGISTRATION_CARDS", indexes = {
        @Index(name = "IDX_REGISTRATIONCARDS", columnList = "CLIENT_ID")
})
@Entity
public class RegistrationCards {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "CREATION_DATE")
    private LocalTime creationDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @InstanceName
    @JoinColumn(name = "APARTMENT_ID", nullable = false)
    private Apartment apartment;

    @NotNull
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Client client;

    @NotNull
    @Column(name = "ARRIVAL_DATE", nullable = false)
    private LocalDateTime arrivalDate;

    @NotNull
    @Column(name = "DEPARTURE_DATE", nullable = false)
    private LocalDateTime departureDate;

    @Column(name = "PAYMENT_INDICATION")
    private Boolean paymentIndication;

    @Column(name = "INDICATION_OF_PREPAYMENT")
    private Boolean indicationOfPrepayment;

    @Column(name = "PAYMENT_DATE")
    private LocalDate paymentDate;

    @Column(name = "PREPAYMENT_DATE")
    private LocalDate prepaymentDate;

    @Column(name = "RESULTS_OF_PCR_TEST_FOR_COVID1", nullable = false)
    private @NotNull String resultsOfPCRTestForCOVID19;

    public LocalTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setResultsOfPCRTestForCOVID19(String resultsOfPCRTestForCOVID19) {
        this.resultsOfPCRTestForCOVID19 = resultsOfPCRTestForCOVID19;
    }

    public String getResultsOfPCRTestForCOVID19() {
        return resultsOfPCRTestForCOVID19;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public LocalDate getPrepaymentDate() {
        return prepaymentDate;
    }

    public void setPrepaymentDate(LocalDate prepaymentDate) {
        this.prepaymentDate = prepaymentDate;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Boolean getIndicationOfPrepayment() {
        return indicationOfPrepayment;
    }

    public void setIndicationOfPrepayment(Boolean indicationOfPrepayment) {
        this.indicationOfPrepayment = indicationOfPrepayment;
    }

    public Boolean getPaymentIndication() {
        return paymentIndication;
    }

    public void setPaymentIndication(Boolean paymentIndication) {
        this.paymentIndication = paymentIndication;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @DependsOnProperties({"apartment", "client", "paymentDate"})
    public String getCount() {
        return String.format("%s %s %s", apartment, client, paymentDate);
    }


}