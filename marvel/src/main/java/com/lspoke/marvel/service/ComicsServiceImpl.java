package com.lspoke.marvel.service;

import com.lspoke.marvel.entity.Character;
import com.lspoke.marvel.entity.Comics;
import com.lspoke.marvel.repository.ComicsRepository;
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
public class ComicsServiceImpl implements ComicsService {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ComicsRepository comicsRepository;

    @Override
    public List<Comics> findAllComics() {
        int count = (int) comicsRepository.count();
        Pageable paging = PageRequest.of(0, count);
        Page<Comics> allComics = comicsRepository.findAll(paging);
        return allComics.toList();
    }

    @Override
    public List<Comics> findAllComics(int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Comics> pageResult = comicsRepository.findAll(paging);
        return pageResult.toList();
    }

    @Override
    public List<Comics> findAllComics(int pageNo, int pageSize, String sort) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sort));
        Page<Comics> pageResult = comicsRepository.findAll(paging);
        return pageResult.toList();
    }

    @Override
    public List<Comics> findByTitle(String title) {
        return comicsRepository.findByTitleIgnoreCaseContaining(title);
    }

    @Override
    public Comics findComicsById(int id) {
        Optional<Comics> comicsResponse = comicsRepository.findById(id);
        Comics comics = comicsResponse.get();
        return comics;
    }

    @Override
    @Transactional
    public List<Character> findCharacterByComics(int id) {
        Session session = entityManager.unwrap(Session.class);
        String hql = "select c.name from Character c "+
                " where c.id = "+
                "(select ch.character from Comics ch where ch.id =" + id+")";
        Query query = session.createQuery(hql);
        List<Character> character = query.getResultList();
        return character;
    }

    @Override
    public void saveComics(Comics comics) {
        comicsRepository.save(comics);
    }
    @Override
    public void uploadCharacter(int characterId, int comicsId) {
        comicsRepository.updateCharacter(characterId, comicsId);
    }

}
