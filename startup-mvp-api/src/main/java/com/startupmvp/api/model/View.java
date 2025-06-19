package com.startupmvp.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "VIEW")
@Getter
@Setter
@ToString
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

    @ManyToOne
    @JoinColumn(name = "APPLICATION_ID", nullable = false)
    private Application application;
    // Getters and Setters
}