package com.lspoke.marvel.controller;

import com.lspoke.marvel.entity.Character;
import com.lspoke.marvel.entity.Comics;
import com.lspoke.marvel.entity.Image;
import com.lspoke.marvel.service.CharacterService;
import com.lspoke.marvel.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;

import java.awt.*;
import java.io.IOException;
import java.util.List;



@RestController
@RequestMapping("/api")
public class CharacterRESTController {
    @Autowired
    private CharacterService characterService;
    @Autowired
    private ImageService imageService;

    private static final Logger logger = LoggerFactory.getLogger(ImageRESTController.class);

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

    @Operation(summary = "Get image by character's id")
    @GetMapping("/character/{id}/image")
    public ResponseEntity<Resource> getImageById(@PathVariable int id, HttpServletRequest request) {
        String imageName = characterService.getImageByCharacterId(id).getFileName();

        Resource resource = imageService.loadFileAsResource(imageName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @Operation(summary = "Post image to character")
    @PostMapping("/character/{id}/image")
    public Image addNewImage(@RequestParam("file") MultipartFile file, @PathVariable int id) {
        String fileName = imageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        Image image = imageService.saveImage(new Image(fileName,fileDownloadUri, file.getContentType()));
        int numImage = image.getId();
        characterService.uploadImage(numImage, id);
        return image;
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
