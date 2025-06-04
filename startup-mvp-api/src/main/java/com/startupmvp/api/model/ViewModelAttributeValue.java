package com.startupmvp.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "VIEW_MODEL_ATTRIBUTE_VALUE")
@Getter
@Setter
@ToString
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