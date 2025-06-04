package com.startupmvp.api.repository;

import com.startupmvp.api.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}

