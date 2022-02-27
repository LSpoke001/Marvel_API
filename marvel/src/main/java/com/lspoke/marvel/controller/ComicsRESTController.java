package com.lspoke.marvel.controller;

import com.lspoke.marvel.entity.Character;
import com.lspoke.marvel.entity.Comics;
import com.lspoke.marvel.service.ComicsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ComicsRESTController {
    @Autowired
    private ComicsService comicsService;

    @Operation(summary = "Get all comics")
    @GetMapping("/comics")
    public List<Comics> getAllComics(){
        return comicsService.findAllComics();
    }

    @Operation(summary = "Get all comics by title")
    @GetMapping(value = "/comics", params = {"title"})
    public List<Comics> getComicsByTitle(@RequestParam("title") String title){
        return comicsService.findByTitle(title);
    }

    @Operation(summary = "Get all comics with pagination")
    @GetMapping(value = "/comics", params = {"page", "size"})
    public List<Comics> getAllComics(@RequestParam("page") int page, @RequestParam("size") int size){
        return comicsService.findAllComics(page, size);
    }

    @Operation(summary = "Get all comics with pagination and sort")
    @GetMapping(value = "/comics", params = {"page", "size", "sort"})
    public List<Comics> getAllComics(@RequestParam("page") int page,
                                     @RequestParam("size") int size, @RequestParam("sort") String sort){
        return comicsService.findAllComics(page, size, sort);
    }

    @Operation(summary = "Get comics by id")
    @GetMapping("/comics/{id}")
    public Comics getComicsById(@PathVariable int id){
        return comicsService.findComicsById(id);
    }

    @Operation(summary = "Get character by comic's ID")
    @GetMapping("/comics/{id}/character")
    public List<Character> getCharacterByComics(@PathVariable int id){
        return comicsService.findCharacterByComics(id);
    }

    @Operation(summary = "Post comics")
    @PostMapping("/comics")
    public Comics addNewComics(@RequestBody Comics comics) {
        comicsService.saveComics(comics);
        return comics;
    }

    @Operation(summary = "Update comics")
    @PutMapping("/comics")
    public Comics updateComics(@RequestBody Comics comics) {
        comicsService.saveComics(comics);
        return comics;
    }

}