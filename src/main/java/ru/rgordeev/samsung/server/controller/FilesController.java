package ru.rgordeev.samsung.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.rgordeev.samsung.server.service.FileStorageService;

@Slf4j
@RestController
@RequestMapping("/files")
public class FilesController {
    private final FileStorageService fileStorageService;

    @Autowired
    public FilesController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Resource> getFileByName(@PathVariable Long id) {
        Resource file = fileStorageService.getResource(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, "image/jpg")
                .body(file);
    }

    @PostMapping("/")
    public void uploadFileForPerson(@RequestParam("file") MultipartFile file, @RequestParam("personId") Long personId) {
        fileStorageService.save(file, personId);
    }

}
