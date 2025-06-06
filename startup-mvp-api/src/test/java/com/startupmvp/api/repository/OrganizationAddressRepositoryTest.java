package com.startupmvp.api.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class OrganizationAddressRepositoryTest {
    @Autowired
    private OrganizationAddressRepository repository;

    @Test
    void contextLoads() {
        assertThat(repository).isNotNull();
    }
}

