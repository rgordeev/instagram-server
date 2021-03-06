package ru.rgordeev.samsung.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.rgordeev.samsung.server.model.Person;
import ru.rgordeev.samsung.server.repository.PersonRepository;

import java.util.List;

@RestController
@RequestMapping(path = "/person")
public class PersonController {

    private final PersonRepository personRepository;

    @Autowired
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping(path = "/", produces = "application/json")
    public List<Person> listPersons() {
        return personRepository.findAll();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public Person getPerson(@PathVariable(name = "id") Long id) {
        return personRepository.getOne(id);
    }

    @PutMapping(path = "/", consumes = "application/json")
    public void addPerson(@RequestBody Person person) {
        personRepository.save(person);
    }

    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    public Person updatePerson(@RequestBody Person person) {
        return personRepository.save(person);
    }

    @DeleteMapping(path = "/{id}")
    public void deletePerson(@PathVariable Long id) {
        personRepository.deleteById(id);
    }

}
