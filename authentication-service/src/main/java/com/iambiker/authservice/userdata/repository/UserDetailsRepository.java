package com.iambiker.authservice.userdata.repository;

import com.iambiker.authservice.userdata.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {
}
