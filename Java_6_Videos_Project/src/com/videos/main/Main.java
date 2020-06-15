package com.videos.main;

import java.io.Console;
import java.util.Date;

import com.videos.tools.Tools;
import com.videos.ui.InputManager;

public class Main {

	public static void main(String[] args) {


		InputManager ui = new InputManager();
		//Date date = ui.askDate("Data de registre:");
		//System.out.println(Tools.parseDateToString(date));
		
		//ui.askOption(1, 4);
		ui.showMenu();
		
	}
	
	

}
