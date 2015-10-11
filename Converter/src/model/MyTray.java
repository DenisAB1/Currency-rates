package model;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;

import javafx.application.Platform;

import javax.imageio.ImageIO;

public class MyTray {
	private static SystemTray tray = null;
	private static TrayIcon trayIcon = null;
	
	public static boolean CreateTrayIcon(){

		if (SystemTray.isSupported()) {
			tray = SystemTray.getSystemTray();

			Image image = null;
	        try {
	            InputStream is = MyTray.class.getResourceAsStream("/icon16.png");
	            if(is == null)
	            	return false;
	            image = ImageIO.read(is);
	        } catch (IOException e) {
	        	return false;
	        }
	        MouseListener mListener = new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getButton() == 1){
						Platform.runLater(new Runnable() {
						      @Override public void run() {
						    	  destroyTrayIcon();
						    	  MyStage.setIconifiedFalse();
						      }
						    });
					}
				}
				@Override
				public void mouseEntered(MouseEvent e) {}
				@Override
				public void mouseExited(MouseEvent e) {}
				@Override
				public void mousePressed(MouseEvent e) {}
				@Override
				public void mouseReleased(MouseEvent e) {}
			};
			ActionListener listener1 = new ActionListener() {
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
			ActionListener listener2 = new ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.exit(0);
				}
			};
			PopupMenu popup = new PopupMenu();
			MenuItem defaultItem1 = new MenuItem();
			MenuItem defaultItem2 = new MenuItem();
			defaultItem1.addActionListener(listener1);
			defaultItem2.addActionListener(listener2);
			defaultItem1.setLabel("Open");
			defaultItem2.setLabel("Exit");
			
			popup.add(defaultItem1);
			popup.addSeparator();
			popup.add(defaultItem2);
			
			trayIcon = new TrayIcon(image, "Currency", popup);
			
			trayIcon.addActionListener(listener1);
			trayIcon.addActionListener(listener2);
			trayIcon.addMouseListener(mListener);
			
			try {
	             tray.add(trayIcon);
	        } catch (AWTException e) {
	             return false;
	        }
		}else{
			return false;
		}
		return true;
	}
	
	private static void destroyTrayIcon(){
		tray.remove(trayIcon);
	}
}
