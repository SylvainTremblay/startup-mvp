package com.csofdv.bmvpf.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "COUNTRY")
@Getter
@Setter
@ToString
public class Country {

    @Id
    @Column(name = "COUNTRY_CD", nullable = false, length = 2)
    private String countryCode;

    @Column(name = "COUNTRY_NM", nullable = false, unique = true, length = 50)
    private String countryName;

    // Getters and Setters
}