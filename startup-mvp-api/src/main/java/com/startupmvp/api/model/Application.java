package com.startupmvp.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "APPLICATION")
@Getter
@Setter
@ToString
public class Application {

    @Id
    @Column(name = "APPLICATION_ID", nullable = false)
    private UUID applicationId;

    @Column(name = "APPLICATION_NAME", nullable = false, length = 30)
    private String applicationName;

    @Column(name = "DESCRIPTION", length = 255)
    private String description;

    @Column(name = "PLATFORM_CODE", nullable = false, length = 8)
    private String platformCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGANIZATION_ID", nullable = false)
    private Organization organization;

}

