package com.videos.project;

import java.util.ArrayList;
import java.util.List;

import com.videos.exceptions.EmptyFieldException;

public class Video {
	
	private String URL;
	private String title;
	private List<String> tagsList;
	
	public Video(String URL, String title) throws Exception{
		this.URL = URL;
		this.title = title;
		this.tagsList= new ArrayList<>();
		
		// comprova si existeix algun camp buit (URL o title)
		//  en cas afirmatiu dispara una excepció de tipus EmptyFieldException
		this.checkFields();
	}

	public void checkFields() throws Exception{
		if (this.URL.equals("")) throw new EmptyFieldException("El camp URL no pot ser buit.");
		if (this.title.equals("")) throw new EmptyFieldException("El camp títol no pot ser buit.");
	}
	
	public String getURL() {
		return URL;
	}

	public String getTitle() {
		return title;
	}

	public List<String> getTagsList() {
		return tagsList;
	}
	
	// Afegeix un nou tag a la llista de tags "tagsList" de l'objecte Video
	// Si el tag que es vol afegir al video esta buit dispara una excepció de tipus EmptyFieldException
	public void addTag(String tag) throws Exception {
		if (tag.equals("")) throw new EmptyFieldException("El camp tag no pot ser buit.");
		this.tagsList.add(tag);
	}
	
}
