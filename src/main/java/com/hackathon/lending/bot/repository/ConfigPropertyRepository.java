package com.hackathon.lending.bot.repository;

import com.hackathon.lending.bot.entity.ConfigProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigPropertyRepository extends JpaRepository<ConfigProperty, String> {
}


