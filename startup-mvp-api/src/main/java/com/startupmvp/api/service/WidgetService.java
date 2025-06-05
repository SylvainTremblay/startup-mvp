package com.startupmvp.api.service;

import com.startupmvp.api.model.Widget;
import com.startupmvp.api.repository.WidgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WidgetService {

    @Autowired
    private WidgetRepository widgetRepository;

    public List<Widget> findAll() {
        return widgetRepository.findAll();
    }

    public Optional<Widget> findById(Long id) {
        return widgetRepository.findById(id);
    }

    public Widget save(Widget widget) {
        return widgetRepository.save(widget);
    }

    public void deleteById(Long id) {
        widgetRepository.deleteById(id);
    }
}