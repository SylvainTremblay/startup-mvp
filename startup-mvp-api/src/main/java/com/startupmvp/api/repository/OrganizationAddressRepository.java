package com.startupmvp.api.repository;

import com.startupmvp.api.model.OrganizationAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationAddressRepository extends JpaRepository<OrganizationAddress, Long> {
}

