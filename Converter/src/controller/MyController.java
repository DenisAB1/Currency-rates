package controller;

import controller.MyTray;
import application.MyStage;

public class MyController {
	public static void Minimize(){
		MyTray.CreateTrayIcon();
		MyStage.setIconifiedTrue();

		System.out.println("minimize");
	}
}
