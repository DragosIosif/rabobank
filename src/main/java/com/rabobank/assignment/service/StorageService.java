package com.rabobank.assignment.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Service for file-related operations
 */
public interface StorageService {

    /**
     * Initialize the storage location
     */
    void init();

    /**
     * Store a given file
     * @param file the file to store
     */
    void store(MultipartFile file);

    /**
     * Load all files in the storage that have the given extension
     * @param extension the extension the files should have
     * @return an array of Resources; if no files found, an empty array is returned
     */
    Resource[] loadAsResourcesByExtension(String extension);

    /**
     * Delete all the files in the storage and the storage space.
     * If other files need to be added, run init() first.
     */
    void deleteAll();

    /**
     * Copy a file to the storage
     * @param file the file to be copied
     */
    void copyFile(File file);

}
