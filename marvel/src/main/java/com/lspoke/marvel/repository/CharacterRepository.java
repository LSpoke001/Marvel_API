package com.lspoke.marvel.repository;

import com.lspoke.marvel.entity.Character;
import com.lspoke.marvel.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Integer> {
    List<Character> findByNameIgnoreCaseContaining(String name);

    @Transactional
    @Modifying
    @Query("update Character set image.id = :imageId where id = :characterId")
    void uploadImage(int imageId, int characterId);
}
