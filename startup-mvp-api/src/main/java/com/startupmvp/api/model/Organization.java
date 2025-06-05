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

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ADDRESS_ID", nullable = false)
    private OrganizationAddress address;

    // Getters and Setters
}

