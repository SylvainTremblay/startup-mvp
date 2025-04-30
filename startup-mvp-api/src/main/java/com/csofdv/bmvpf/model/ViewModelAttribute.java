package com.csofdv.bmvpf.model;

import jakarta.persistence.*;

@Entity
@Table(name = "VIEW_MODEL_ATTRIBUTE")
public class ViewModelAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VIEW_MODEL_ATTRIBUTE_ID", nullable = false)
    private Long viewModelAttributeId;

    @ManyToOne
    @JoinColumn(name = "VIEW_MODEL_ID")
    private ViewModel viewModel;

    @Column(name = "ATTRIBUTE_NAME", nullable = false, length = 20)
    private String attributeName;

    @ManyToOne
    @JoinColumn(name = "ATTRIBUTE_TYPE_ID", nullable = false)
    private AttributeType attributeType;

    // Getters and Setters
}