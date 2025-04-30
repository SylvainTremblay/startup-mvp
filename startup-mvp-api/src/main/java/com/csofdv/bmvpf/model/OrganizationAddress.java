package com.csofdv.bmvpf.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Table(name = "ORGANIZATION_ADDRESS")
public class OrganizationAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADDRESS_ID", nullable = false)
    private Long addressId;

    @Column(name = "STREET", nullable = false, length = 100)
    private String street;

    @Column(name = "CITY", nullable = false, length = 50)
    private String city;

    @Column(name = "STATE", nullable = false, length = 50)
    private String state;

    @Column(name = "ZIP_CODE", nullable = false, length = 10)
    private String zipCode;

    @Column(name = "COUNTRY", nullable = false, length = 50)
    private String country;

    // Getters and Setters
}