package ru.spbu.math.ais.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "dwd_fedirector")
public class Director {

	@Id
	@Column(name="fedirectorkey")
	int id;
	
	@Column(name="fedirectorid")
	String name;
	
	@OneToMany(mappedBy="director")
	Set<DirectorStatPoint> directorStatPoints;
	
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Director [id=" + id + ", name=" + name + "]";
	}
	


	
}
