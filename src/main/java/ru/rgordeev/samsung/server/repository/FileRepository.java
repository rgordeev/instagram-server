package ru.rgordeev.samsung.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rgordeev.samsung.server.model.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
}
