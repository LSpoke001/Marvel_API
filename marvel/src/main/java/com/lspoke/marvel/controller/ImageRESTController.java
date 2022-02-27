package com.lspoke.marvel.controller;


import com.lspoke.marvel.entity.Image;
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
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ImageRESTController {

    private static final Logger logger = LoggerFactory.getLogger(ImageRESTController.class);

    @Autowired
    private ImageService imageService;

    @Operation(summary = "Get all image in json")
    @GetMapping("/image")
    public List<Image> getAllImages(){
        return imageService.getAllImages();
    }

    @Operation(summary = "Get all image in json with pagination")
    @GetMapping(value = "/image", params = {"page", "size"})
    public List<Image> getAllImages(@RequestParam("page") int page, @RequestParam("size") int size){
        return imageService.getAllImages(page, size);
    }

    @Operation(summary = "Get image by name ")
    @GetMapping("/image/{imageName:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName, HttpServletRequest request) {
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

    @Operation(summary = "Post image with form-data")
    @PostMapping("/image")
    public Image addNewImage(@RequestParam("file") MultipartFile file) {
        String fileName = imageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        Image image = new Image(fileName,fileDownloadUri, file.getContentType());
        imageService.saveImage(image);
        return image;
    }
}