package com.csofdv.bmvpf.model;

import jakarta.persistence.*;

@Entity
@Table(name = "VIEW_MODEL_ATTRIBUTE_VALUE")
public class ViewModelAttributeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VIEW_MODEL_ATTRIBUTE_VALUE_ID", nullable = false)
    private Long viewModelAttributeValueId;

    @ManyToOne
    @JoinColumn(name = "VIEW_MODEL_ATTRIBUTE_ID", nullable = false)
    private ViewModelAttribute viewModelAttribute;

    @Column(name = "ATTRIBUTE_VALUE", nullable = false, length = 255)
    private String attributeValue;

    // Getters and Setters
}