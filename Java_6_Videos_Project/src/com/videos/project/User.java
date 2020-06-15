package com.videos.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.videos.exceptions.EmptyFieldException;

public class User {
	
	private String name;
	private String surname;
	private String password;
	private Date registerDate;
	private List<Video> videosList;
	
	// constructor definit inicialment pero no usat al codi del projecte
	public User( String name, String surname, String password ) throws EmptyFieldException{
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.registerDate = new GregorianCalendar().getTime(); //data actual per defecte
		this.videosList = new ArrayList<>();
		
		this.checkFields();
	}
	
	public User( String name, String surname, String password, Date date) 
			throws EmptyFieldException{
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.registerDate = date;
		this.videosList = new ArrayList<>();
		
		// comprova si existeix algun camp buit (name o surname o password)
		//  en cas afirmatiu dispara una excepció de tipus EmptyFieldException
		this.checkFields(); 
	}
	
	public void checkFields() throws EmptyFieldException {
		if (this.name.equals("")) throw new EmptyFieldException("El camp nom no pot estar buit.");
		if (this.surname.equals("")) throw new EmptyFieldException("El camp cognom no pot estar buit.");
		if (this.password.equals("")) throw new EmptyFieldException("El camp password no pot estar buit.");
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getPassword() {
		return password;
	}

	public Date getRegisterDate() {
		return registerDate;
	}
	
	public void addVideo(Video video) {
		this.videosList.add(video);
	}
	
	public List<Video> getVideoList(){
		return this.videosList;
	}
	
}
