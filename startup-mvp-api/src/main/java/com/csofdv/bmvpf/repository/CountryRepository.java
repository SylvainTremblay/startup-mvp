package com.csofdv.bmvpf.repository;

import com.csofdv.bmvpf.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, String> {
}

