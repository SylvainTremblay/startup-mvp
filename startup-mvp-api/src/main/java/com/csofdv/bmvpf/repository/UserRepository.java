package com.csofdv.bmvpf.repository;

import com.csofdv.bmvpf.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

