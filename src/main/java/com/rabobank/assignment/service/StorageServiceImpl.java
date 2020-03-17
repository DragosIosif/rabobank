package com.rabobank.assignment.service;

import com.rabobank.assignment.model.StorageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Default implementation of StorageService
 */
@Service
public class StorageServiceImpl implements StorageService {
    private static final Logger log = LoggerFactory.getLogger(StorageServiceImpl.class);

    private final Path rootLocation;

    @Autowired
    public StorageServiceImpl(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void store(MultipartFile file) {
        if(file == null){
            return;
        }
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty() || filename.contains("..")) {
                log.error("Cannot store file. File empty or name invalid. " + filename);
                return;
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            log.error("Failed to store file " + filename, e);
        }
    }

    @Override
    public void copyFile(File file) {
        if (file == null) {
            return;
        }
        try (final InputStream inputStream = new FileInputStream(file)) {
            Files.copy(inputStream, this.rootLocation.resolve(file.getName()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resource[] loadAsResourcesByExtension(String extension) {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .filter(path -> path.toString().endsWith(extension))
                    .map(FileSystemResource::new)
                    .toArray(FileSystemResource[]::new);
        } catch (IOException e) {
            return new Resource[0];
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            log.error("Could not initialize storage", e);
        }
    }
}
