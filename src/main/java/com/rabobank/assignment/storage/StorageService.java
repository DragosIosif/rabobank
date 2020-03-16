package com.rabobank.assignment.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {

    void init();

    void store(MultipartFile file);

    Resource[] loadAsResourcesByExtension(String extension);

    Path load(String filename);

    void deleteAll();

}
