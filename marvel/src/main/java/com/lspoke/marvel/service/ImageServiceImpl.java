package com.lspoke.marvel.service;


import com.lspoke.marvel.entity.Image;
import com.lspoke.marvel.extection.FileStorageException;
import com.lspoke.marvel.extection.FileNotFoundException;
import com.lspoke.marvel.property.FileProperties;
import com.lspoke.marvel.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService{

    private final Path fileStorageLocation;
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(FileProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public List<Image> getAllImages() {
        int count = (int) imageRepository.count();
        Pageable paging = PageRequest.of(0, count);
        Page<Image> allImages = imageRepository.findAll(paging);

        return allImages.toList();
    }

    @Override
    public List<Image> getAllImages(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Image> imageResult = imageRepository.findAll(paging);
        return imageResult.toList();
    }

    @Override
    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(fileName.contains("..")) {
                throw new FileStorageException("Filename contains invalid path sequence " + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!");
        }
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName, ex);
        }
    }

    @Override
    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }
}