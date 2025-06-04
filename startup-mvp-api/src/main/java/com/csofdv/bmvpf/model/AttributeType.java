package com.csofdv.bmvpf.model;
// Import necessary packages
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ATTRIBUTE_TYPE")
@Getter
@Setter
@ToString
public class AttributeType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ATTRIBUTE_TYPE_ID", nullable = false)
    private Long attributeTypeId;

    @Column(name = "NAME", nullable = false, length = 30)
    private String name;

    @Column(name = "ENUM", nullable = true)
    private Boolean enumeration;
}