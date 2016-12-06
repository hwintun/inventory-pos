package com.webservice.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.webservice.model.Person;



@Service("personServices")
public class PersonServices {
	public static final AtomicLong counter = new AtomicLong();
	
	private static List<Person> personList;
	
	static{
		personList = populateDummyPersonList(); 
	}
	
	public List<Person> findAllPerson(){
		return personList;
	}
	
	public Person findById(long id) {
		for(Person p : personList){
			if(p.getId() == id) {
				return p;
			}
		}
		return null;
	}
	
	public Person findByName(String name) {
		for(Person p : personList) {
			if(p.getName().equalsIgnoreCase(name)) {
				return p;
			}
		}
		return null;
	}
	
	public void savePerson(Person p) {
		p.setId(counter.incrementAndGet());
		personList.add(p);
	}
	
	public void updatePerson(Person p) {
		int index = personList.indexOf(p);
		personList.set(index, p);
	}
	
	public void deleteById(long id) {
		for(Iterator<Person> iterator = personList.iterator(); iterator.hasNext();) {
			Person p = iterator.next();
			if(p.getId() == id) {
				iterator.remove();
			}
		}
	}
	
	public boolean isUserExist(Person p) {
		return findByName(p.getName()) != null;
	}
	
	public void deleteAllPerson() {
		personList.clear();
	}
	
	private static List<Person> populateDummyPersonList() {
		List<Person> tmp = new ArrayList<Person>();
		tmp.add(new Person(counter.incrementAndGet(),"Hlaing Win Tun",33, 137));
		tmp.add(new Person(counter.incrementAndGet(),"Yin Pa Pa",25, 90));
		tmp.add(new Person(counter.incrementAndGet(),"Htet Htet Lin",23, 100));
		return tmp;
	}
}
