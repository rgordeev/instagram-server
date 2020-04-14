package ru.rgordeev.samsung.server.controller;

import lombok.extern.slf4j.Slf4j;
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

    public FilesController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<Resource> getFileByName(@RequestParam(name = "name") String filename) {
        Resource file = fileStorageService.getResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, "image/jpg")
                .body(file);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Resource> getFileById(@PathVariable Long id) {
        Resource file = fileStorageService.getFileById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, "image/jpg")
                .body(file);
    }

    @PostMapping("/")
    public void uploadFileForPerson(@RequestParam("file") MultipartFile file, @RequestParam("personid") Long id) {
        fileStorageService.save(file, id);
    }

}
