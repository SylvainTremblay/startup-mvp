package com.startupmvp.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "WIDGET_TYPE")
@Getter
@Setter
@ToString
public class WidgetType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WIDGET_TYPE_ID", nullable = false)
    private Long widgetTypeId;

    @Column(name = "NAME", nullable = false, length = 30)
    private String name;

    @ManyToOne
    @JoinColumn(name = "PLATFORM_CODE", nullable = false)
    private Platform platform;

    @Column(name = "SINCE_PLATFORM_VERSION", length = 20)
    private String sincePlatformVersion;

    @Column(name = "UNTIL_PLATFORM_VERSION", length = 20)
    private String untilPlatformVersion;

    // Getters and Setters
}