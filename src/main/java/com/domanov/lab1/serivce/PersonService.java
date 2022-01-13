package com.domanov.lab1.serivce;

import com.domanov.lab1.model.Person;
import com.domanov.lab1.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("PersonService")
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> getPerson(Integer id) {
        Optional<Person> persons = personRepository.findById(id);
        List<Person> personList = new ArrayList<>();
        persons.ifPresent(personList::add);
        return personList;
    }

    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    public Person createPerson(String name, Integer age, String address, String work) {
        Person person = new Person(name, age, address, work);
        personRepository.save(person);
        return person;
    }

    public ResponseEntity<Person> updatePerson(Person personUpdate, Integer id) {
        if (getPerson(id).size() != 0) {
            Person person = getPerson(id).get(0);
            if (personUpdate.getName() != null) {
                person.setName(personUpdate.getName());
            }
            if (personUpdate.getAge() != null) {
                person.setAge(personUpdate.getAge());
            }
            if (personUpdate.getAddress() != null) {
                person.setAddress(personUpdate.getAddress());
            }
            if (personUpdate.getWork() != null) {
                person.setWork(personUpdate.getWork());
            }
            personRepository.save(person);
            return new ResponseEntity<>(person, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Person> deletePerson(Integer id) {
        if (getPerson(id).size() != 0) {
            Person person = getPerson(id).get(0);
            personRepository.delete(person);
            return new ResponseEntity<>(person, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
