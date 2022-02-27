package com.lspoke.marvel.repository;

import com.lspoke.marvel.entity.Comics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComicsRepository extends JpaRepository<Comics, Integer> {
    List<Comics> findByTitleIgnoreCaseContaining(String title);
}
