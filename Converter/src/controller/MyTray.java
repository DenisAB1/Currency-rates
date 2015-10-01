package controller;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;

import javafx.application.Platform;
import application.MyStage;

public class MyTray {
	private static SystemTray tray = null;
	private static TrayIcon trayIcon = null;
	
	public static void CreateTrayIcon(){

		if (SystemTray.isSupported()) {
			tray = SystemTray.getSystemTray();
			
			Image image = Toolkit.getDefaultToolkit().getImage("D:/valve16.jpg");

			ActionListener listener1 = new ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.exit(0);
				}
			};
			PopupMenu popup = new PopupMenu();
			MenuItem defaultItem1 = new MenuItem();
			defaultItem1.addActionListener(listener1);
			defaultItem1.setLabel("Exit");
			popup.add(defaultItem1);
			
			ActionListener listener2 = new ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Platform.runLater(new Runnable() {
					      @Override public void run() {
					    	  destroyTrayIcon();
					    	  MyStage.setIconifiedFalse();
					      }
					    });
				}
			};
			MenuItem defaultItem2 = new MenuItem();
			defaultItem2.addActionListener(listener2);
			defaultItem2.setLabel("Open");
			popup.addSeparator();
			popup.add(defaultItem2);
			
			trayIcon = new TrayIcon(image, "Currency", popup);

			trayIcon.addActionListener(listener1);
			trayIcon.addActionListener(listener2);
			try {
	             tray.add(trayIcon);
	        } catch (AWTException e) {
	             System.err.println(e);
	        }
		}else{
			System.exit(0);
		}
	}
	
	public static void destroyTrayIcon(){
		tray.remove(trayIcon);

		
	}
}
