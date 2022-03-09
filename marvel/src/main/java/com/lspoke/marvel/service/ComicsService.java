package com.lspoke.marvel.service;

import com.lspoke.marvel.entity.Character;
import com.lspoke.marvel.entity.Comics;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ComicsService {

    List<Comics> findAllComics();
    List<Comics> findAllComics(int pageNo, int pageSize);
    List<Comics> findAllComics(int pageNo, int pageSize, String sort);

    List<Comics> findByTitle(String title);

    Comics findComicsById(int id);

    List<Character> findCharacterByComics(int id);

    void saveComics(Comics comics);
    void uploadCharacter(int characterId, int comicsId);
}
