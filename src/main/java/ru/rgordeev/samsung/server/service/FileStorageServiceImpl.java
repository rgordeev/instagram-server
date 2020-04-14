package ru.rgordeev.samsung.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.rgordeev.samsung.server.model.File;
import ru.rgordeev.samsung.server.model.Person;
import ru.rgordeev.samsung.server.repository.FileRepository;
import ru.rgordeev.samsung.server.repository.PersonRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
@Transactional
public class FileStorageServiceImpl implements FileStorageService {

    private final Path root = Paths.get("/tmp/instagram");

    private final FileRepository fileRepository;
    private final PersonRepository personRepository;

    @Autowired
    public FileStorageServiceImpl(FileRepository fileRepository, PersonRepository personRepository) {
        this.fileRepository = fileRepository;
        this.personRepository = personRepository;
    }

    @PostConstruct
    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (Exception e) {
            log.error("Error: {}", e);
        }
    }

    @Override
    public void save(final MultipartFile file, final Long personId) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path filePath = this.root.resolve(fileName);
        try (InputStream in = file.getInputStream()) {
            Person person = personRepository.getOne(personId);
            ru.rgordeev.samsung.server.model.File f = new File();
            f.setName(fileName);
            f.setPath(filePath.toString());
            person.addAvatar(f);
            personRepository.save(person);
            Files.copy(in, this.root.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException | EntityNotFoundException e) {
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
    public Resource getFileById(Long fileId) {
        try {
            File file = fileRepository.getOne(fileId);
            Path path = get(file.getName());
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
        } catch (MalformedURLException | EntityNotFoundException e) {
            log.error("Error: {}", e);
        }
        return null;
    }

    @Override
    public void delete() {
        FileSystemUtils.deleteRecursively(this.root.toFile());
    }
}
