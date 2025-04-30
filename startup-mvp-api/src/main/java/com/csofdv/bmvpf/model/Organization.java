package com.csofdv.bmvpf.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "ORGANIZATION")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORGANIZATION_ID", nullable = false)
    private Long organizationId;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @OneToOne
    @JoinColumn(name = "ADDRESS_ID", nullable = false)
    private OrganizationAddress address;

    // Getters and Setters
}