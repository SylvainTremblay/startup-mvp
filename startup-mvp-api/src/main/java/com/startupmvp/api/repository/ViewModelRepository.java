package com.startupmvp.api.repository;

import com.startupmvp.api.model.ViewModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewModelRepository extends JpaRepository<ViewModel, Long> {
}

