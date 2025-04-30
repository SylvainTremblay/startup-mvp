package com.csofdv.bmvpf.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "WIDGET_TYPE_ATTRIBUTE")
public class WidgetTypeAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WIDGET_TYPE_ATTRIBUTE_ID", nullable = false)
    private Long widgetTypeAttributeId;

    @Column(name = "ATTRIBUTE_NAME", nullable = false, length = 20)
    private String attributeName;

    @ManyToOne
    @JoinColumn(name = "ATTRIBUTE_TYPE_ID", nullable = false)
    private AttributeType attributeType;

    @Column(name = "SINCE_PLATFORM_VERSION", length = 20)
    private String sincePlatformVersion;

    @Column(name = "UNTIL_PLATFORM_VERSION", length = 20)
    private String untilPlatformVersion;

    @Column(name = "REQUIRED", nullable = false)
    private Boolean required;

    @Column(name = "ATTRIBUTE_NAME_REQUIRED", nullable = false)
    private Boolean attributeNameRequired;

    @ManyToOne
    @JoinColumn(name = "WIDGET_TYPE_ID", nullable = false)
    private WidgetType widgetType;

    // Getters and Setters
}