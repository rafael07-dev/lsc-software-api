package com.lsc.software.api.repository;

import com.lsc.software.api.model.SignPattern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SignPatternRepository extends JpaRepository<SignPattern, Long> {
    Optional<SignPattern> findByWord(String word);
}
