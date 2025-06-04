package com.startupmvp.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "PLATFORM")
@Getter
@Setter
@ToString
public class Platform {

    @Id
    @Column(name = "PLATFORM_CODE", nullable = false, length = 8)
    private String platformCode;

    @Column(name = "PLATFORM_VERSION", nullable = false, length = 20)
    private String platformVersion;

    @Column(name = "VERSION_DATE", nullable = false)
    private java.time.LocalDate versionDate;

    @Column(name = "PLATFORM_NAME", nullable = false, length = 30)
    private String platformName;

    // Getters and Setters
}