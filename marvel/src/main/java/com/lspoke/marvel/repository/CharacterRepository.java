package com.lspoke.marvel.repository;

import com.lspoke.marvel.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Integer> {
    List<Character> findByNameIgnoreCaseContaining(String name);
}
