package com.lsc.software.api.repository;

import com.lsc.software.api.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    //List<Word> findAllByLetter (String letter);
}
