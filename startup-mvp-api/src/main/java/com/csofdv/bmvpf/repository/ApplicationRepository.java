package com.csofdv.bmvpf.repository;

import com.csofdv.bmvpf.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}

