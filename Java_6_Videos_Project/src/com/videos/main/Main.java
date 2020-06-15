package com.videos.main;

import com.videos.ui.InputManager;

public class Main {

	public static void main(String[] args) {


		InputManager ui = new InputManager(); //encapsula la gestió d'entrada de dades per consola
	
		ui.showMenu(); //Mostra el menu per pantalla
		
		ui.close();
		
	}
	
	

}
