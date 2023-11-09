package com.example.model;

import java.sql.Date;
import javax.persistence.*;

@Entity
@Table(name="User")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	private String email;
	private String password;
	private Date bdate;
	private String cnumber;
	private String url;
	private String filepath;
	
	public String getName() {
		return name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public User(long id, String name, String email, String password, Date bdate, String cnumber, String url,
			String filepath) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.bdate = bdate;
		this.cnumber = cnumber;
		this.url = url;
		this.filepath = filepath;
	}
	
	public User() {
		
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getBdate() {
		return bdate;
	}
	public void setBdate(Date bdate) {
		this.bdate = bdate;
	}
	public String getCnumber() {
		return cnumber;
	}
	public void setCnumber(String cnumber) {
		this.cnumber = cnumber;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
