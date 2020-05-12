package ru.rgordeev.samsung.server.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface FileStorageService {
    void init();
    void save(MultipartFile file, Long personId);
    Stream<Path> get();
    Path get(String fileName);
    Resource getResource(Long id);
    void delete();
    List<Long> listFileIDsByPersonId(Long personId);
}
