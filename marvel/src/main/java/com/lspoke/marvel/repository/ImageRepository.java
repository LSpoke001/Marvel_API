package com.lspoke.marvel.repository;

import com.lspoke.marvel.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}
