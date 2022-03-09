package com.lspoke.marvel.service;

import com.lspoke.marvel.entity.Image;
import com.lspoke.marvel.repository.CharacterRepository;
import com.lspoke.marvel.entity.Character;
import com.lspoke.marvel.entity.Comics;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
public class CharactersServiceImpl implements CharacterService {
    @Autowired
    private CharacterRepository characterRepository;
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Character> findAllCharacters() {
        int count = (int) characterRepository.count();
        Pageable paging = PageRequest.of(0, count);
        Page<Character> allCharacters = characterRepository.findAll(paging);
        return allCharacters.toList();
    }

    @Override
    public List<Character> findAllCharacters(int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Character> pageResult = characterRepository.findAll(paging);
        return pageResult.toList();
    }

    @Override
    public List<Character> findAllCharacters(int pageNo, int pageSize, String sort) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sort));
        Page<Character> pageResult = characterRepository.findAll(paging);
        return pageResult.toList();
    }

    @Override
    public List<Character> findByName(String name) {
        return characterRepository.findByNameIgnoreCaseContaining(name);
    }

    @Override
    public Character findCharacterById(int id) {
        Optional<Character> characterResponse = characterRepository.findById(id);
        Character character = characterResponse.get();

        return character;
    }

    @Override
    @Transactional
    public List<Comics> findComicsByCharacter(int id) {
        Session session = entityManager.unwrap(Session.class);
        String hql = " select c.title from Comics c where c.character = " + id;
        Query query = session.createQuery(hql);
        List<Comics> comics = query.getResultList();
        return comics;
    }

    @Override
    public void saveCharacter(Character character) {
        characterRepository.save(character);
    }

    @Override
    public void uploadImage(int imageId, int characterId) {
        characterRepository.uploadImage(imageId, characterId);
    }

    @Override
    public Image getImageByCharacterId(int characterId){
        Session session = entityManager.unwrap(Session.class);
        String hql = "select c.image from Character c where c.id = "+ characterId;
        Query query = session.createQuery(hql);

        Image image = (Image)query.getSingleResult();
        return image;
    }

}
