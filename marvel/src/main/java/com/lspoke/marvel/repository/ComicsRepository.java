package com.lspoke.marvel.repository;

import com.lspoke.marvel.entity.Comics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ComicsRepository extends JpaRepository<Comics, Integer> {
    List<Comics> findByTitleIgnoreCaseContaining(String title);

    @Transactional
    @Modifying
    @Query("update Comics set character.id = :characterId where id = :comicsId")
    void updateCharacter(int characterId, int comicsId);
}
