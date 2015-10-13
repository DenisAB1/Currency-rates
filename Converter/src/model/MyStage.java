package model;

import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MyStage{
	private static Stage primaryStage;
	
	public static void init(Stage stage){
		primaryStage = stage;
		
	}

	public static void setIconifiedFalse(){
		primaryStage.setOpacity(0.9);
	}
	public static void setOnClosing(){
		primaryStage.setOnCloseRequest(event -> {
            event.consume();
            if(MyTray.CreateTrayIcon())
                primaryStage.setOpacity(0);
            else
                System.exit(0);
        });
	}
}
