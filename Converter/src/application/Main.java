package application;

import java.awt.Dialog;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Semaphore;

import javafx.application.Application;
import javafx.stage.Stage;
import model.MyStage;
import sun.misc.resources.Messages;
import sun.plugin2.message.Message;
import sun.plugin2.message.Serializer;
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
		//рабочая версия

		try {
			ServerSocket serverSocket = new ServerSocket(29073);
		} catch (IOException e) {
            System.err.println("Application already run.");
			System.exit(1);
		}
		launch(args);
	}
}
