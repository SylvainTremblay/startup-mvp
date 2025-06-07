package com.startupmvp.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "ORGANIZATION")
@Getter
@Setter
@ToString
public class Organization {

    @Id
    @Column(name = "ORGANIZATION_ID", nullable = false, updatable = false)
    private UUID organizationId;

    @Column(name = "ORGANIZATION_NAME", nullable = false, length = 100)
    private String organizationName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORGANIZATION_ADDRESS_ID")
    private OrganizationAddress organizationAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BILLING_ADDRESS_ID")
    private OrganizationAddress billingAddress;

    @Column(name = "WEBSITE", length = 255)
    private String website;

    // Getters and Setters
}

