package com.startupmvp.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "WIDGET_ATTRIBUTE_VALUE")
@Getter
@Setter
@ToString
public class WidgetAttributeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WIDGET_ATTRIBUTE_VALUE_ID", nullable = false)
    private Long widgetAttributeValueId;

    @ManyToOne
    @JoinColumn(name = "WIDGET_ID", nullable = false)
    private Widget widget;

    @ManyToOne
    @JoinColumn(name = "ATTRIBUTE_ID", nullable = false)
    private AttributeType attributeType;

    @Column(name = "ATTRIBUTE_VALUE", nullable = false, length = 255)
    private String attributeValue;

    // Getters and Setters
}