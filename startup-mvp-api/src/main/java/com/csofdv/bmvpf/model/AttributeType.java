package com.csofdv.bmvpf.model;
// Import necessary packages
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Table(name = "ATTRIBUTE_TYPE")
public class AttributeType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ATTRIBUTE_TYPE_ID", nullable = false)
    private Long attributeTypeId;

    @Column(name = "NAME", nullable = false, length = 30)
    private String name;

    // Getters and Setters
}