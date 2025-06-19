package com.startupmvp.api.model;

import jakarta.persistence.*;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "WIDGET")
@Getter
@Setter
@ToString
public class Widget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WIDGET_ID", nullable = false)
    private Long widgetId;

    @ManyToOne
    @JoinColumn(name = "WIDGET_TYPE_ID", nullable = false)
    private WidgetType widgetType;

    @Column(name = "CONSTANT", nullable = false)
    private Boolean constant;

    @Column(name = "TIMESTAMP", nullable = false)
    private LocalDateTime timestamp;

    @OneToMany(mappedBy = "widget", cascade = jakarta.persistence.CascadeType.ALL, fetch = FetchType.EAGER)
    private List<WidgetAttributeValue> attributeValues;

}

