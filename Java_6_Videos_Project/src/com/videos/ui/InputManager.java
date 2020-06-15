package com.videos.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.videos.project.User;
import com.videos.project.Video;

public class InputManager {
	
	Scanner input;
	List<User> userList;
	User currentUser = null; 
	
	public InputManager() {
		this.input = new Scanner(System.in);
		this.userList = new ArrayList<>();
	}

	public void close() {
		this.input.close();
	}
	
	public void showMenu() {
		System.out.println("--------------------------------------------- ");
		System.out.println("                    MENU");
		System.out.println("--------------------------------------------- ");

		System.out.println("1. Crear nou Usuari");
		System.out.println("2. Seleccionar usuari");
		System.out.println("3. Crear nou Video per a usuari actual");
		System.out.println("4. Veure llistat de videos de l'usuari actual");
		System.out.println("5. Sortir del programa");
		
		System.out.println("---------------------------------------------");
		
		if (this.currentUser!=null) this.showCurrentUser();
		
		System.out.println("---------------------------------------------");

		
		int option = this.askOption(1, 5);
		
		if (option==1) {
			this.createUser();
		}else if(option==2) {
			this.chooseUser();
		}else if(option==3) {
			this.createVideo();
		}else if(option==4) {
			this.showVideos();
		}
		
		if(option!=5) {
			showMenu();
		}else {
			System.out.println("---------------");
			System.out.println("FI DEL PROGRAMA");
			System.out.println("---------------");
		}
	}
	
	
	
	public void createUser() {
		
		String name,surname,password;
		Date date;
		User newUser = null;
		
		boolean fieldFormat = false;
		
		while(fieldFormat==false) {
			System.out.println("------------");
			System.out.println("CREAR USUARI");
			System.out.println("------------");
			
			name = this.askString("Introdueix NOM:");
			surname = this.askString("Introdueix COGNOM:");
			password = this.askString("Introdueix PASSWORD:");
			date = this.askDate("Introdueix DATA de REGISTRE:");		
			
			try {
				newUser = new User(name,surname,password,date);
				fieldFormat = true;
			}catch(Exception e) {
				System.out.println(e.getMessage());
				System.out.println("Torna a introduir l'usuari de nou.");
			}
		}
		
		this.userList.add(newUser);
		System.out.println("USUARI CREAT CORRECTAMENT.");
		pause();
		
	}
	
	public void chooseUser() {
		System.out.println("------------------");
		System.out.println("SELECCIONAR USUARI");
		System.out.println("------------------");
		
		for(int i=0;i<userList.size();i++) {
			User u = userList.get(i);
			System.out.println(i+". "+ u.getName() + " "+u.getSurname());
		}
		
		System.out.println("------------------");
		
		if (this.userList.size()>0) {
			int option = this.askOption(0, userList.size()-1);
			this.currentUser = userList.get(option);
			this.showCurrentUser();

		} else {
			System.out.println("Encara no has creat cap usuari!");
		}
		pause();
	}
	
	public void showCurrentUser() {
		System.out.println("USUARI ACTUAL SELECCIONAT: "+ this.currentUser.getName().toUpperCase() + 
		           " " + this.currentUser.getSurname().toUpperCase());
	}
	
	public void createVideo() {
		
		Video video = null;
		
		System.out.println("---------------------------------------------");
		System.out.println("CREAR VIDEO per a l'usuari actual seleccionat");
		System.out.println("---------------------------------------------");
		
		if(this.currentUser==null) {
			System.out.println("Primer has de seleccionar un usuari!");
			pause();
		}else {
			boolean fieldFormat = false;
			
			while(fieldFormat==false) {
				
				String url = askString("Introdueix la URL:");
				String titol = askString("Introdueix el TITOL:");
				try {
					video = new Video(url,titol);
					fieldFormat=true;
				}catch(Exception e) {
					System.out.println(e.getMessage());
					System.out.println("Torna a introduir el video de nou.");
				}
			}
			addVideoTags(video);
			this.currentUser.addVideo(video);
			
			System.out.println("VIDEO CREAT CORRECTAMENT.");
			pause();
		}
	}
	
	public void addVideoTags(Video video) {
		
		boolean end = false;
		
		while(end==false) {
			
			String tag = askString("Introdueix un TAG:");
			
			try {
				video.addTag(tag);
			}catch(Exception e) {
				System.out.println(e.getMessage());
				System.out.println("Torna a introduir el tag de nou.");
			}
			
			int opcio = askInt("Vols continuar afegint TAGS al video? (0:No / 1:Si) ",0,1);
			if(opcio==0) end=true;
		}
	}
	
	public void showVideos() {
		
		System.out.println("---------------------------------------------------------");
		System.out.println("VEURE LLISTAT de VIDEOS per a l'usuari actual seleccionat");
		System.out.println("---------------------------------------------------------");
		
		if(this.currentUser==null) {
			System.out.println("Primer has de seleccionar un usuari!");
			pause();
		}else {
			
			List<Video> list = this.currentUser.getVideoList();
			String output = "";
			for (int i=0;i<list.size();i++) {
				
				Video video = list.get(i);
				
				output ="";
				output = i +". " + "TITOL = [" + video.getTitle() + "], URL = ["+video.getURL()+"] ";
				output += ", TAGS = [";
				
				for (String s : video.getTagsList()) {
					output += s +",";
				}
				
				output += "]";
				
				System.out.println(output);
			}
			if(list.size()==0) System.out.println("Aquest usuari no ha creat cap video.");
			pause();
		}
	}
	
	public String askString(String questionMessage) {
		
		System.out.println(questionMessage);
		String answer = this.input.nextLine();
		
		return answer;
	}
	
	public Date askDate(String questionMessage){
		
		boolean dateFormat = false;
		Date date = null;
		
		while (dateFormat == false) {
			try {
				System.out.println(questionMessage);
				System.out.println("Introdueix la data amb format 'dd/mm/aaaa':");
				String strDate = this.input.nextLine();
				
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");
				date = format.parse(strDate);
				
				dateFormat = true;
				
			}catch(ParseException e) {
				System.out.println("El camp de la data ha de tenir un format 'dd/mm/aa'! Torna a introduir la data de nou.");
			}
		}
		
		return date;
	}
	

	public int askOption(int minInt, int maxInt) {
		
		return askInt("Selecciona una opció",minInt,maxInt);
		
	}
	
	public int askInt(String questionMessage,int minInt, int maxInt) {
		
		int option= 0;
		boolean numberFormat = false;
		
		System.out.println(questionMessage);
		
		while(numberFormat==false) {
			if (this.input.hasNextInt()) {
				option = this.input.nextInt();
				if(option>=minInt && option<=maxInt) {
					numberFormat=true;
				}else {
					System.out.println("Tria una opció introduint un número entre "+minInt+ " i "+maxInt);
				}
			}else {
				System.out.println("Tria una opció introduint un número entre "+minInt+ " i "+maxInt);
			}
			this.input.nextLine();
		}
		
		return option;
	}
	
	public void pause() {
		System.out.println("Prem qualsevol tecla per a tornar al menu...");
		this.input.nextLine();
	}
	
}
