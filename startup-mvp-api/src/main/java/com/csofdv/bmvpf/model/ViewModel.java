package com.csofdv.bmvpf.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "VIEW_MODEL")
@Getter
@Setter
@ToString
public class ViewModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VIEW_MODEL_ID", nullable = false)
    private Long viewModelId;

    @ManyToOne
    @JoinColumn(name = "VIEW_ID")
    private View view;

    // Getters and Setters
}