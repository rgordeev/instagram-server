package ru.rgordeev.samsung.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Slf4j
@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path root = Paths.get("/tmp/imstagram");

    public FileStorageServiceImpl() {
        init();
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (Exception e) {
            log.error("Error: {}", e);
        }
    }

    @Override
    public void save(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try (InputStream in = file.getInputStream()) {
            Files.copy(in, this.root.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("Error: {}", e);
        }
    }

    @Override
    public Stream<Path> get() {
        try {
            return Files.walk(this.root, 1)
                    .filter(path -> !path.equals(this.root))
                    .map(this.root::relativize);
        } catch (IOException e) {
            log.error("Error: {}", e);
            return Stream.empty();
        }

    }

    @Override
    public Path get(String fileName) {
        return this.root.resolve(fileName);
    }

    @Override
    public Resource getResource(String fileName) {
        try {
            Path path = get(fileName);
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
        } catch (MalformedURLException e) {
            log.error("Error: {}", e);
        }
        return null;
    }

    @Override
    public void delete() {
        FileSystemUtils.deleteRecursively(this.root.toFile());
    }
}
