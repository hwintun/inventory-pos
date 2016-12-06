package com.webservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.webservice.model.Person;
import com.webservice.services.PersonServices;


@RestController
public class ProjController {
	@Autowired
	PersonServices personService;
	
	@RequestMapping(value = "/person/", method = RequestMethod.GET)
	public ResponseEntity<List<Person>> listAllPerson() {
		List<Person> personList = personService.findAllPerson();
		if(personList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Person>>(personList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/person/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Person> getPerson(@PathVariable("id") long id) {
		Person p = personService.findById(id);
		if(p == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<Person>(p, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/person/", method = RequestMethod.POST)
	public ResponseEntity<Void> createPerson(@RequestBody Person p, UriComponentsBuilder uiBuilder) {
		if(personService.isUserExist(p)) return new ResponseEntity<Void>(HttpStatus.CONFLICT);		
		personService.savePerson(p);		
		HttpHeaders header = new HttpHeaders();
		header.setLocation(uiBuilder.path("/person/{id}").buildAndExpand(p.getId()).toUri());
		return new ResponseEntity<Void>(header, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/person/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Person> updatePerson(@PathVariable("id") long id, @RequestBody Person p) {
		Person currentPerson = personService.findById(id);
		if(currentPerson == null) return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);		
		currentPerson.setName(p.getName());
		currentPerson.setAge(p.getAge());
		currentPerson.setWeight(p.getWeight());		
		personService.updatePerson(currentPerson);				
		return new ResponseEntity<Person>(currentPerson, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/person/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Person> deletePerson(@PathVariable("id") long id) {
		Person currentPerson = personService.findById(id);
		if(currentPerson == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		personService.deleteById(id);
		return new ResponseEntity<Person>(currentPerson, HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "/person/", method = RequestMethod.DELETE)
	public ResponseEntity<Person> deleteAllPerson() {
		personService.deleteAllPerson();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
