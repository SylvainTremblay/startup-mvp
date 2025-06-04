package com.startupmvp.api.repository;

import com.startupmvp.api.model.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformRepository extends JpaRepository<Platform, String> {
}

