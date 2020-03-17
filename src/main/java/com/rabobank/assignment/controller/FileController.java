package com.rabobank.assignment.controller;

import com.rabobank.assignment.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller for file-related operations
 */
@Controller
public class FileController {

    private static final String UPLOAD_FORM = "uploadForm";
    private final StorageService storageService;

    @Autowired
    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String defaultPage() {
        return UPLOAD_FORM;
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        storageService.store(file);
        return UPLOAD_FORM;
    }

    @RequestMapping("/deleteFiles")
    public String deleteAll(){
        storageService.deleteAll();
        storageService.init();
        return UPLOAD_FORM;
    }

}