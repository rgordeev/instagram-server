package ru.rgordeev.samsung.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.rgordeev.samsung.server.model.File;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    @Query("select f.id from File f join f.person p where p.id = :personId")
    List<Long> findAllByPersonId(@Param("personId") Long personId);

}
