package com.startupmvp.api.service;

import com.startupmvp.api.model.View;
import com.startupmvp.api.repository.ViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ViewService {

    @Autowired
    private ViewRepository viewRepository;

    public List<View> findAll() {
        return viewRepository.findAll();
    }

    public Optional<View> findById(Long id) {
        return viewRepository.findById(id);
    }

    public View save(View view) {
        return viewRepository.save(view);
    }

    public void deleteById(Long id) {
        viewRepository.deleteById(id);
    }
}