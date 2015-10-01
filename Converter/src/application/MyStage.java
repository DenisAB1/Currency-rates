package application;

import javafx.stage.Stage;

public class MyStage{
	private static Stage primaryStage;
	
	public static void init(Stage stage){
		primaryStage = stage;
	}
	public static void setIconifiedTrue(){
		primaryStage.setIconified(true);
	}
	public static void setIconifiedFalse(){
		primaryStage.setIconified(false);
	}
}
