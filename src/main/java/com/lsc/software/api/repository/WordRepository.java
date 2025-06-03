package com.lsc.software.api.repository;

import com.lsc.software.api.model.Word;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {

    Word findWordByWord(String word);
    Page<Word> findByLetter_Letter(String letter, Pageable pageable);
}
