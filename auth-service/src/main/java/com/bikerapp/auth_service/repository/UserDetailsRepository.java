package com.bikerapp.auth_service.repository;

import com.bikerapp.auth_service.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {
}
