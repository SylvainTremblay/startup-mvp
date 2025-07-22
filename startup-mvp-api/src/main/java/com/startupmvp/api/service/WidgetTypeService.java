package com.startupmvp.api.service;

import com.startupmvp.api.model.View;
import com.startupmvp.api.model.WidgetType;
import com.startupmvp.api.repository.WidgetTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WidgetTypeService {

    @Autowired
    private WidgetTypeRepository widgetTypeRepository;

    public List<WidgetType> findAll() {
        return widgetTypeRepository.findAll();
    }

    public Optional<WidgetType> findById(Long id) {
        return widgetTypeRepository.findById(id);
    }

    public Optional<WidgetType> findByName(String name) {
        WidgetType widgetType  = widgetTypeRepository.findByName(name);
        return Optional.ofNullable(widgetType);
    }

    public WidgetType save(WidgetType widgetType) {
        return widgetTypeRepository.save(widgetType);
    }

    public void deleteById(Long id) {
        widgetTypeRepository.deleteById(id);
    }
}