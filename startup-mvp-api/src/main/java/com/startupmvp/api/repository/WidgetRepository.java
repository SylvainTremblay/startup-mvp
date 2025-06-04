package com.startupmvp.api.repository;

import com.startupmvp.api.model.Widget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WidgetRepository extends JpaRepository<Widget, Long> {
}

