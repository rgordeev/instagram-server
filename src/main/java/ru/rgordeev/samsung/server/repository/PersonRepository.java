package ru.rgordeev.samsung.server.repository;

import org.springframework.stereotype.Repository;
import ru.rgordeev.samsung.server.errors.ObjectNotFound;
import ru.rgordeev.samsung.server.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class PersonRepository {

    private List<Person> persons = new ArrayList<>();

    public List<Person> listPersons() {
        return persons;
    }

    public Person getPersonById(Long id) {
        for(Person p: persons) {
            if (Objects.equals(p.getId(), id))
                return p;
        }
        throw new ObjectNotFound("Person with id: " + id + " wasn't found");
    }

    public void addPerson(Person person) {
        persons.add(person);
    }

    public Person updatePerson(Person person) {
        for(Person p: persons) {
            if (Objects.equals(p.getId(), person.getId())) {
                p.setLogin(person.getLogin());
                p.setPassword(person.getPassword());
                p.setName(person.getName());
                p.setLastName(person.getLastName());
                p.setBio(person.getBio());
                p.setAge(person.getAge());
                return p;
            }
        }
        throw new ObjectNotFound("Person with id: " + person.getId() + " wasn't found");
    }

    public void delete(Long id) {
        Person person = null;
        for(Person p: persons) {
            if (Objects.equals(p.getId(), id)) {
                person = p;
                break;
            }
        }
        if (person != null) {
            persons.remove(person);
        }
        else {
            throw new ObjectNotFound("Person with id: " + id + " wasn't found");
        }
    }
}
