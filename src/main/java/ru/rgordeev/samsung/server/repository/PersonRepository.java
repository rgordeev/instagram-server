package ru.rgordeev.samsung.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rgordeev.samsung.server.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
