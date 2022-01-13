package com.domanov.lab1.controller;

import com.domanov.lab1.model.Person;
import com.domanov.lab1.serivce.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("https://laba1-rsoi.herokuapp.com/api/v1/persons")
//@CrossOrigin("http://localhost:8080/api/v1/persons")
@RequestMapping("api/v1/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/{personId}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Person> person(@PathVariable(value = "personId") Integer id) {
        List<Person> personList = personService.getPerson(id);
        if (personList.size() != 0) {
            return new ResponseEntity<>(personList.get(0), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping()
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<Person>> persons() {
        List<Person> personList = personService.getPersons();
        return new ResponseEntity<List<Person>>(personList, HttpStatus.OK);
    }

    @PostMapping("")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Integer personId = personService.createPerson(person.getName(), person.getAge(), person.getAddress(), person.getWork()).getId();
        if (personService.getPerson(personId).size() != 0) {
            HttpHeaders headers = new HttpHeaders();
//            headers.add("Location", "http://localhost:8080/api/v1/persons/" + personId.toString());
            headers.add("Location", "https://laba1-rsoi.herokuapp.com/api/v1/persons/" + personId.toString());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{personId}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Person> updatePerson(@PathVariable(value = "personId") Integer personId, @RequestBody Person personUpdate) {
        return personService.updatePerson(personUpdate, personId);
    }

    @DeleteMapping("/{personId}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Person> deletePerson(@PathVariable(value = "personId") Integer personId) {
        return personService.deletePerson(personId);
    }
}
