package com.csofdv.bmvpf.repository;

import com.csofdv.bmvpf.model.ViewModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewModelRepository extends JpaRepository<ViewModel, Long> {
}

