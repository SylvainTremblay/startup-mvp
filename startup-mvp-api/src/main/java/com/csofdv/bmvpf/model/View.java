package com.csofdv.bmvpf.model;

import jakarta.persistence.*;

@Entity
@Table(name = "VIEW")
public class View {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VIEW_ID", nullable = false)
    private Long viewId;

    @Column(name = "NAME", nullable = false, length = 30)
    private String name;

    @Column(name = "STATELESS", nullable = false)
    private Boolean stateless;

    @Column(name = "MAIN_VIEW", nullable = false)
    private Boolean mainView;

    @ManyToOne
    @JoinColumn(name = "MAIN_WIDGET_ID")
    private Widget mainWidget;

    // Getters and Setters
}