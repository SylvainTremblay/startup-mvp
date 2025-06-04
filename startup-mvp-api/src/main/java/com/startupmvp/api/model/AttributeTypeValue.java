package com.startupmvp.api.model;
// Import statements
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
@Table(name = "ATTRIBUTE_TYPE_VALUE")
@Getter
@Setter
@ToString
public class AttributeTypeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ATTRIBUTE_TYPE_VALUE_ID", nullable = false)
    private Long attributeTypeValueId;

    @ManyToOne
    @JoinColumn(name = "ATTRIBUTE_TYPE_ID", nullable = false)
    private AttributeType attributeType;

    @Column(name = "ATTRIBUTE_VALUE", nullable = false, length = 50)
    private String attributeValue;

    // Getters and Setters
}