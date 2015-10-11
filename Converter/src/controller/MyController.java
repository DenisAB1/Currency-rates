package controller;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.MyConverter;
import model.MyCurrency;

import org.w3c.dom.NodeList;

import view.MyView;

public class MyController {
	
	private static NodeList listOfCurrency;
	
	public static void initialize(){
		
		MyView.setCurrentDate();
		MyView.setCells();
		
		listOfCurrency = MyCurrency.GetList(MyView.getDateValue());
		
		if(listOfCurrency == null)
			MyView.changeStateToError();
		else{
			MyView.changeStateToNormal();
			setAndUpdateComboBoxes();
			MyView.setComboBoxes234VisFalse();
		}	

		MyView.setImageFromTo();
		MyView.setImageEqual();
	}
	
	private static void setItemsToComboBox(){
		String str[] = MyView.getComboBoxesValues();
		
		MyView.setItemsToComboBoxesR(MyCurrency.getListOfCurrencyRates());
		MyView.setItemsToComboBoxesC(MyCurrency.getListOfCurrencyConverter());
		
		MyView.checkContains(MyCurrency.getListOfCurrencyRates(), str);	
	}

	private static void setAndUpdateComboBoxes(){

		setItemsToComboBox();
		
		for (int i = 0; i < 4; i++) {
			MyView.updateComboBox(i, listOfCurrency);
		}
	}
	public void comboBox1Hiding(Event event){
		MyView.updateComboBox(0, listOfCurrency);
	}
	public void comboBox2Hiding(Event event){
		MyView.updateComboBox(1, listOfCurrency);
	}
	public void comboBox3Hiding(Event event){
		MyView.updateComboBox(2, listOfCurrency);
	}
	public void comboBox4Hiding(Event event){
		MyView.updateComboBox(3, listOfCurrency);
	}
	public void dateHiding(Event event){
		if(LocalDate.now().plusDays(1).compareTo(MyView.getDateValue()) >= 0)
			listOfCurrency = MyCurrency.GetList(MyView.getDateValue());
		else{
			MyView.setCurrentDate();
			listOfCurrency = MyCurrency.GetList(MyView.getDateValue());
		}
		if(listOfCurrency != null){
			MyView.changeStateToNormal();
			setAndUpdateComboBoxes();
		}else 
			MyView.changeStateToError();
	}
	public void updateButtonAction(ActionEvent event){
		if((listOfCurrency = MyCurrency.GetList(MyView.getDateValue())) != null){
			if(MyView.getLabelErrorVisibility())
			{
				MyView.changeStateToNormal();
				setAndUpdateComboBoxes();
				//System.out.println("update data");
			}
		}	
	}
	
	public void keyReleased(KeyEvent event){
		if(MyView.getTextFieldLeftFocused())
		{
			MyConverter.updateTextFields(event, true, listOfCurrency);
		}
		if(MyView.getTextFieldRightFocused())
		{
			MyConverter.updateTextFields(event, false, listOfCurrency);
		}
	}
	public void comboBoxLeftHiding(Event event){
		MyConverter.findAndUpdate(listOfCurrency, true);
	}
	public void comboBoxRightHiding(Event event){
		MyConverter.findAndUpdate(listOfCurrency, false);
	}
	
	public void TFLeftMouseClicked(MouseEvent event){
		MyView.selectAllTFLeft();
	}
	public void TFRightMouseClicked(MouseEvent event){
		MyView.selectAllTFRight();
	}
}
