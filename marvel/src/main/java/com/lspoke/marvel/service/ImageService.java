package com.lspoke.marvel.service;

import com.lspoke.marvel.entity.Character;
import com.lspoke.marvel.entity.Image;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    List<Image> getAllImages();
    List<Image> getAllImages(int page, int size);

    Resource loadFileAsResource(String fileName);

    String storeFile(MultipartFile file);

    Image saveImage(Image image);
}
