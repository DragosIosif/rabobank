package com.rabobank.assignment.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Representation of the storage related properties
 */
@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "upload-dir";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
