package com.iambiker.authservice.userdata.repository;

import com.iambiker.authservice.userdata.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {
}
