package ru.rgordeev.samsung.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.rgordeev.samsung.server.model.Person;
import ru.rgordeev.samsung.server.repository.PersonRepository;
import ru.rgordeev.samsung.server.service.FileStorageService;

@Slf4j
@RestController
@RequestMapping("/files")
public class FilesController {
    private final PersonRepository personRepository;
    private final FileStorageService fileStorageService;

    public FilesController(PersonRepository personRepository, FileStorageService fileStorageService) {
        this.personRepository = personRepository;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFileByName(@PathVariable String filename) {
        Resource file = fileStorageService.getResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, "image/jpg")
                .body(file);
    }

    @PostMapping("/")
    public void uploadFileForPerson(@RequestParam("file") MultipartFile file, @RequestParam("personid") Long id) {
        fileStorageService.save(file);
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Person p = personRepository.getPersonById(id);
        p.setAvatar(fileName);
    }

}
