package controller;

import java.io.File;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import org.w3c.dom.NodeList;

import com.sun.javafx.scene.paint.GradientUtils.Parser;

import application.Main;
import application.MyStage;

public  class MyController {

// initialize currency rates
	
	@FXML
	ComboBox<String> comboBox1, comboBox2, comboBox3, comboBox4;
	@FXML
	Label label1, label2, label3, label4, labelError;
	@FXML
	DatePicker date;
	@FXML
	Button updateButton;
	
// initialize currency converter
	
	@FXML
	AnchorPane converterPane;
	@FXML
	ComboBox<String> comboBoxLeft, comboBoxRight;
	@FXML
	ImageView imageFromTo;
	@FXML
	TextField textFieldLeft, textFieldRight;
	
	private static ObservableList<String> ListOfCurrencyRates = FXCollections.observableArrayList();
	private static ObservableList<String> ListOfCurrencyConverter = FXCollections.observableArrayList();
	
	private static NodeList listOfCurrency;
	
	public void initialize(){
		
// initialize currency rates

		date.setValue(LocalDate.now());
		
		final Callback<DatePicker, DateCell> dayCellFactory = 
	            new Callback<DatePicker, DateCell>() {
	                @Override
	                public DateCell call(final DatePicker datePicker) {
	                    return new DateCell() {
	                        @Override
	                        public void updateItem(LocalDate item, boolean empty) {
	                            super.updateItem(item, empty);
	                           
	                            if (item.isAfter((LocalDate.now().plusDays(1)))) {
	                                    setDisable(true);
	                                    setStyle("-fx-background-color: #dcdcdc;");
	                            }
	                            if (item.isBefore((LocalDate.of(1996, 3, 29)))) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #dcdcdc;");
                            }
	                    }
	                };
	            }
	        };
	    date.setDayCellFactory(dayCellFactory);
	    
	    comboBox1.setItems(ListOfCurrencyRates);
		comboBox2.setItems(ListOfCurrencyRates);
		comboBox3.setItems(ListOfCurrencyRates);
		comboBox4.setItems(ListOfCurrencyRates);
	        
		listOfCurrency = MyCurrency.GetList(date, labelError);
		if(listOfCurrency == null)
			changeStateToError();
		else{	
			changeStateToNormal();
			comboBox2.setVisible(false);
			comboBox3.setVisible(false);
			comboBox4.setVisible(false);
		}	

// initialize currency converter
		
        imageFromTo.setImage(new Image(MyController.class.getResourceAsStream("/Images/fromTo.png")));
		
		comboBoxLeft.setItems(ListOfCurrencyConverter);
		comboBoxRight.setItems(ListOfCurrencyConverter);
	}
	
	private void setItemsToComboBox(){
		String string1 = comboBox1.getValue();
		String string2 = comboBox2.getValue();
		String string3 = comboBox3.getValue();
		String string4 = comboBox4.getValue();
		String string5 = comboBoxLeft.getValue();
		String string6 = comboBoxRight.getValue();
		ListOfCurrencyRates.clear();
		ListOfCurrencyConverter.clear();
		String temp;
		for (int i = 0; i < listOfCurrency.getLength(); i++){
			temp = listOfCurrency.item(i).getChildNodes().item(3).getTextContent();
			ListOfCurrencyConverter.add(temp);
			ListOfCurrencyRates.add(temp);
		}
		ListOfCurrencyConverter.add("BYR");
		System.out.println("setting items");
		
		
		checkContainsRates(comboBox1, label1, string1);
		checkContainsRates(comboBox2, label2, string2);
		checkContainsRates(comboBox3, label3, string3);
		checkContainsRates(comboBox4, label4, string4);
		checkContainsConverter(comboBoxLeft, textFieldLeft, string5);
		checkContainsConverter(comboBoxRight, textFieldRight, string6);
		
	}
	private void checkContainsRates(ComboBox<String> comboBox, Label label, String str){
		if(ListOfCurrencyRates.contains(str))
			comboBox.setValue(str);
		else
			label.setText(null);
	}
	private void checkContainsConverter(ComboBox<String> comboBox, TextField tf, String str){
		if(ListOfCurrencyRates.contains(str))
			comboBox.setValue(str);
		else
			tf.setText(null);
	}
	
	private void changeStateToError(){
		label1.setVisible(false);
		label2.setVisible(false);
		label3.setVisible(false);
		label4.setVisible(false);
		comboBox1.setVisible(false);
		comboBox2.setVisible(false);
		comboBox3.setVisible(false);
		comboBox4.setVisible(false);
		labelError.setVisible(true);
		updateButton.setVisible(true);
	}
	private void changeStateToNormal(){
		label1.setVisible(true);
		label2.setVisible(true);
		label3.setVisible(true);
		label4.setVisible(true);
		comboBox1.setVisible(true);
		if(comboBox2.getValue() != null)
		comboBox2.setVisible(true);
		if(comboBox3.getValue() != null)
		comboBox3.setVisible(true);
		if(comboBox4.getValue() != null)
		comboBox4.setVisible(true);
		labelError.setVisible(false);
		updateButton.setVisible(false);
		
		setItemsToComboBox();
		
		System.out.println(comboBox1.getValue());
		
		MyCurrency.updateComboBox(comboBox1, comboBox2, label1, listOfCurrency);
		MyCurrency.updateComboBox(comboBox2, comboBox3, label2, listOfCurrency);
		MyCurrency.updateComboBox(comboBox3, comboBox4, label3, listOfCurrency);
		MyCurrency.updateComboBox(comboBox4, null, label4, listOfCurrency);
	}
	
	public static void Minimize(){
		MyTray.CreateTrayIcon();
		MyStage.setIconifiedTrue();
		System.out.println("minimize");
		
	}
	public void comboBox1Hiding(Event event){
		MyCurrency.updateComboBox(comboBox1, comboBox2, label1, listOfCurrency);
	}
	public void comboBox2Hiding(Event event){
		MyCurrency.updateComboBox(comboBox2, comboBox3, label2, listOfCurrency);
	}
	public void comboBox3Hiding(Event event){
		MyCurrency.updateComboBox(comboBox3, comboBox4, label3, listOfCurrency);
	}
	public void comboBox4Hiding(Event event){
		MyCurrency.updateComboBox(comboBox4, null, label4, listOfCurrency);
	}
	public void dateHiding(Event event){
		if(LocalDate.now().plusDays(1).compareTo(date.getValue()) >= 0)
			listOfCurrency = MyCurrency.GetList(date, labelError);
		else{
			date.setValue(LocalDate.now());
			listOfCurrency = MyCurrency.GetList(date, labelError);
		}
		if(listOfCurrency != null){
				changeStateToNormal();
			System.out.println("update data");
		}else changeStateToError();
	
	}
	public void updateButtonAction(ActionEvent event){
		if((listOfCurrency = MyCurrency.GetList(date, labelError)) != null){
			if(labelError.isVisible())
			{
				changeStateToNormal();
				System.out.println("update data");
			}
		}	
	}
	
	public void keyReleased(KeyEvent event){
		if(textFieldLeft.isFocused())
		{
			int length = textFieldLeft.getLength();
			if(event.getCode().equals(KeyCode.BACK_SPACE) && length > 0){
				textFieldLeft.deleteText(length-1, length);
			}
			try{
				Integer.parseInt(event.getText());
				textFieldLeft.appendText(event.getText());
			} catch(NumberFormatException e){
				event.consume();
			}
		}
		if(textFieldRight.isFocused())
		{
			int length = textFieldRight.getLength();
			if(event.getCode().equals(KeyCode.BACK_SPACE) && length > 0){
				textFieldRight.deleteText(length - 1, length);
			}
			try{
				Integer.parseInt(event.getText());
				textFieldRight.appendText(event.getText());
			} catch(NumberFormatException e){
				event.consume();
			}
		}
	}
}
