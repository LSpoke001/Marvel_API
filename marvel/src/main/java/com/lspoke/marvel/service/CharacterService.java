package com.lspoke.marvel.service;


import com.lspoke.marvel.entity.Character;
import com.lspoke.marvel.entity.Comics;
import com.lspoke.marvel.entity.Image;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CharacterService {

    List<Character> findAllCharacters();
    List<Character> findAllCharacters(int pageNo, int pageSize);
    List<Character> findAllCharacters(int pageNo, int pageSize, String sort);

    List<Character> findByName(String name);

    Character findCharacterById(int id);

    List<Comics> findComicsByCharacter(int id);

    void saveCharacter(Character character);
    void uploadImage(int imageId, int characterId);
    Image getImageByCharacterId(int characterId);

}
