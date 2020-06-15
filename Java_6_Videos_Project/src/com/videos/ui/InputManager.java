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
	List<User> userList; //Llista de usuaris creats
	User currentUser = null;  //Usuari actual seleccionat
	
	public InputManager() {
		this.input = new Scanner(System.in);
		this.userList = new ArrayList<>();
	}

	public void close() {
		this.input.close();
	}
	
	/**
	 * Mostra el menu d'opcions per consola
	 * Si hi ha un usuari actual seleccionat es mostra a continuació del menú
	 * Es demana a l'usuari que seleccioni una opció entre 1 i 5 a l'entrada de dades de la consola
	 * Si es selecciona la opció 5 es surt del programa
	 */
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
	
	
	/**
	 * Es crea un nou usuari a partir de les dades introduïdes per consola per part de l'usuari
	 * Si s'intenta crear un usuari amb algun camp buit es torna a demanar de nou totes les dades
	 * Finalment s'afegeix l'usuari creat a la llista "userList" del InputManager
	 */
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
	
	/**
	 * Es mostra el nom i cognom de tots els usuaris amb un index a l'esquerra
	 * Els usuaris existents fins al moment estan guardats a la llista "userList" de la classe InputManager
	 * Es demana a l'usuari que seleccioni un usuari introduint el número de l'index corresponent
	 * L'usuari seleccionat es guarda a l'atribut currentUser de la classe InputManager
	 */
	public void chooseUser() {
		System.out.println("------------------");
		System.out.println("SELECCIONAR USUARI");
		System.out.println("------------------");
		
		//Mostra tots els usuaris per consola
		for(int i=0;i<userList.size();i++) {
			User u = userList.get(i);
			System.out.println(i+". "+ u.getName() + " "+u.getSurname());
		}
		
		System.out.println("------------------");
		
		//Es demana per consola escollir un usuari i es mostra per pantalla l'usuari seleccionat
		if (this.userList.size()>0) {
			int option = this.askOption(0, userList.size()-1);
			this.currentUser = userList.get(option);
			this.showCurrentUser();

		} else { //si no hi cap usuari seleccionat es mostra missatge per pantalla
			System.out.println("Encara no has creat cap usuari!");
		}
		pause();
	}
	
	/**
	 * Mostra usuari actual seleccionat per pantalla
	 * Es a dir, l'usuari instanciat a l'atribut "currentUser" de la classe actual InputManager
	 */
	public void showCurrentUser() {
		System.out.println("USUARI ACTUAL SELECCIONAT: "+ this.currentUser.getName().toUpperCase() + 
		           " " + this.currentUser.getSurname().toUpperCase());
	}
	
	/**
	 * Crea un nou video per a l'usuari actual seleccionat (currentUser)
	 * Si algun dels camps de l'objecte video (url o titol) es torna a demanar introduir de nou
	 * totes les dades del video
	 * S'afegeix el video a la llista de videos de l'usuari
	 */
	public void createVideo() {
		
		Video video = null;
		
		System.out.println("---------------------------------------------");
		System.out.println("CREAR VIDEO per a l'usuari actual seleccionat");
		System.out.println("---------------------------------------------");
		
		if(this.currentUser==null) { //si no hi ha cap usuari seleccionat mostra missatge per consola
			System.out.println("Primer has de seleccionar un usuari!");
			pause();
		}else { //es demanen les dades per consola per a crear un video sense tags
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
			addVideoTags(video); //s'afegeixen tags al video
			this.currentUser.addVideo(video); //s'afegeix el video a la llista de videos de l'usuari
			
			System.out.println("VIDEO CREAT CORRECTAMENT.");
			pause();
		}
	}
	
	/**
	 * Demana per consola introduir tags al video passat per parametre
	 * Es pregunta a l'usuari si vol continuar afegint tags, en cas negatiu es finalitza la 
	 * creació del video
	 * 
	 * @param video
	 */
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
	
	/**
	 * Mostra per pantalla tota la llista de videos de l'usuari actual seleccionat
	 * Cada video es mostra a una linea de la consola incloent titol, url i llista de tags
	 * 
	 */
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
			for (int i=0;i<list.size();i++) { //fa un recorregut dels videos de l'usuari actual seleccionat
				
				Video video = list.get(i);
				
				output =""; //conte un String amb la informació del video que es vol mostrar per consola
				output = i +". " + "TITOL = [" + video.getTitle() + "], URL = ["+video.getURL()+"] ";
				output += ", TAGS = [";
				
				for (String s : video.getTagsList()) {
					output += s +",";
				}
				
				output += "]";
				
				System.out.println(output); //mostra un video de la llista per pantalla
			}
			if(list.size()==0) System.out.println("Aquest usuari no ha creat cap video.");
			pause();
		}
	}
	
	/**
	 * Metode reutilitzable per a demanar a l'usuari introduir un STRING per consola
	 * Previament mostra per consola el missatge passat per parametre 
	 * 
	 * @param questionMessage
	 * @return
	 */
	public String askString(String questionMessage) {
		
		System.out.println(questionMessage);
		String answer = this.input.nextLine();
		
		return answer;
	}
	
	/**
	 * Metode reutilitzable per a demanar a l'usuari introduir una DATA per consola
	 * Previament mostra per consola el missatge passat per parametre 
	 * Si el format de la data es incorrecte es torna a demanar la data de nou per consola
	 * 
	 * @param questionMessage
	 * @return
	 */
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
	
	/**
	 * Es demana un número de tipus enter per consola
	 * Si no té un format de tipus enter vàlid o el número no es troba dins del rang dels 
	 * parametres "minInt" i "maxInt" s'informa de l'error per pantalla i es torna a demanar
	 * un número a l'usuari
	 */
	public int askOption(int minInt, int maxInt) {
		
		return askInt("Selecciona una opció:",minInt,maxInt);
		
	}
	
	/**
	 * Es demana un número de tipus enter per consola
	 * Previament es mostra el missatge passat per parametre "questionMessage"
	 * Si no té un format de tipus enter vàlid o el número no es troba dins del rang dels 
	 * parametres "minInt" i "maxInt" s'informa de l'error per pantalla i es torna a demanar
	 * un número a l'usuari
	 * 
	 * @param questionMessage
	 * @param minInt
	 * @param maxInt
	 * @return
	 */
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
	
	/**
	 *  Es fa una pausa per consola per a poder veure els últims missatges fins que l'usuari
	 *  premi una tecla per consola
	 *  
	 */
	
	public void pause() {
		System.out.println("Prem qualsevol tecla per a tornar al menu...");
		this.input.nextLine();
	}
	
}
