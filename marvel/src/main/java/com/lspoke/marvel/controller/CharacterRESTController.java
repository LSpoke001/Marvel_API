package com.lspoke.marvel.controller;

import com.lspoke.marvel.entity.Character;
import com.lspoke.marvel.entity.Comics;
import com.lspoke.marvel.service.CharacterService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api")
public class CharacterRESTController {
    @Autowired
    private CharacterService characterService;

    @Operation(summary = "Get all characters")
    @GetMapping("/character")
    public List<Character> getAllCharacter(){
        List<Character> characters = characterService.findAllCharacters();
        return characters;
    }

    @Operation(summary = "Get characters by name")
    @GetMapping(value = "/character", params = {"name"})
    public List<Character> getCharacterByName(@RequestParam("name") String name){
        List<Character> characters = characterService.findByName(name);
        return characters;
    }

    @Operation(summary = "Get all characters with pagination")
    @GetMapping(value = "/character", params = {"page", "size"})
    public List<Character> getAllCharacter(@RequestParam("page") int page, @RequestParam("size") int size){
        return characterService.findAllCharacters(page, size);
    }

    @Operation(summary = "Get all characters with pagination and sort")
    @GetMapping(value = "/character", params = {"page", "size", "sort"})
    public List<Character> getAllCharacter(@RequestParam("page") int page,
                                           @RequestParam("size") int size, @RequestParam("sort") String sort){

        return characterService.findAllCharacters(page, size, sort);
    }

    @Operation(summary = "Get character by ID")
    @GetMapping("/character/{id}")
    public Character getCharacter(@PathVariable int id){
        Character character = characterService.findCharacterById(id);
        return character;
    }

    @Operation(summary = "Get comics by character's ID")
    @GetMapping("/character/{id}/comics")
    public List<Comics> getComicsByCharacter(@PathVariable int id){
        List<Comics> comics = characterService.findComicsByCharacter(id);

        return comics;
    }

    @Operation(summary = "Post character")
    @PostMapping("/character")
    public Character addNewCharacter(@RequestBody Character character) {
        characterService.saveCharacter(character);
        return character;
    }

    @Operation(summary = "Update Character")
    @PutMapping("/character")
    public Character updateCharacter(@RequestBody Character character) {
        characterService.saveCharacter(character);
        return character;
    }

}
