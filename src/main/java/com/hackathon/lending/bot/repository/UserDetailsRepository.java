package com.hackathon.lending.bot.repository;

import com.hackathon.lending.bot.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, String> {
    
    Optional<UserDetails> findByApplicationId(String applicationId);
}

