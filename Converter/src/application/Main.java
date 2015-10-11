package application;

import javafx.application.Application;
import javafx.stage.Stage;
import model.MyStage;
import view.MyView;
import controller.MyController;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		MyStage.init(primaryStage);
		MyStage.setOnClosing();
		
		MyView.start(primaryStage);
		
		MyController.initialize();
		
		MyView.show(primaryStage);
	}
	public static void main(String[] args) {
		launch(args);
	}
}
