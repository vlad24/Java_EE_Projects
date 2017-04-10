package ru.spbu.math.ais.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="dwd_storagegroup")
public class StorageGroup {
	
	@Id
	@Column(name="storagegroupkey")
	int id;
	@Column(name="storagegroupid")
	String name;
	
	@OneToMany(mappedBy="storageGroup")
	Set<StorageGroupStatPoint> storageGroupStatPoints;
	
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
		return "StorageGroup [id=" + id + ", name=" + name + "]";
	}
	
}
