package com.webservice.model;

public class Person {
	private long id;
	private String name;
	private int age;
	private float weight;
	
	public Person() {
		this.id = 0;
		this.age = 0;
		this.weight = 0.0f;
	}
	
	public Person(long id, String name, int age, float weight) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.weight = weight;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(!(obj instanceof Person)) return false;
		Person p = (Person) obj;
		if(this.id != p.id) return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Person [id=" + this.id + ", name=" + this.name + ", age=" + this.age + ", weight=" + this.weight + "]";
	}
}
