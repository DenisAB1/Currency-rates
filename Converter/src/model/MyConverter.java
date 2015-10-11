package model;

import javafx.scene.control.IndexRange;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import org.w3c.dom.NodeList;

import view.MyView;

public class MyConverter {
		public static void updateTextFields(KeyEvent event, boolean isLeft, NodeList listOfCurrency){

		int length = MyView.getLengthTFLeft(isLeft);
		IndexRange selectionRangeTFLeft = MyView.getSelectionTFLeft(isLeft);
		if(event.getCode().equals(KeyCode.BACK_SPACE) && length > 0){
			if(selectionRangeTFLeft.getLength() > 0)
				MyView.deleteTextFromTFLeft(selectionRangeTFLeft.getStart(), selectionRangeTFLeft.getEnd(), isLeft);
			else
				MyView.deleteTextFromTFLeft(length-1, length, isLeft);
			if(MyView.getLengthTFLeft(isLeft) == 0)
				MyView.cleartextFieldRight(isLeft);
			try{
				findAndUpdate(listOfCurrency, isLeft);
			} catch(NumberFormatException e){
				event.consume();
			}
		}
		else try{
			Integer.parseInt(event.getText());
			if(selectionRangeTFLeft.getLength() > 0)
				MyView.deleteTextFromTFLeft(selectionRangeTFLeft.getStart(), selectionRangeTFLeft.getEnd(), isLeft);
			MyView.appendTFLeft(event.getText(), isLeft);

			findAndUpdate(listOfCurrency, isLeft);
		} catch(NumberFormatException e){
			event.consume();
		}
	}
	public static void findAndUpdate(NodeList listOfCurrency, boolean isLeft){
		boolean leftFind = false;
		boolean rightFind = false;
		Double rateLeft = 1.0;
		Double rateRight = 1.0;
		
		if(MyView.getComboBoxLeftValue(isLeft) == null || MyView.getComboBoxRightValue(isLeft) == null)
			return;
		
		for (int i = 0; i < listOfCurrency.getLength(); i++){
			if(MyView.getTextTFLeft(isLeft) == null)
				return;
			if(MyView.getComboBoxLeftValue(isLeft) == "BYR")
			{
				rateLeft = 1.0;
				leftFind = true;
			}
			if(MyView.getComboBoxRightValue(isLeft) == "BYR")
			{
				rateRight = 1.0;
				rightFind = true;
			}
			if(listOfCurrency.item(i).getChildNodes().item(3).getTextContent().compareTo(MyView.getComboBoxLeftValue(isLeft)) == 0){
				rateLeft = Double.valueOf(listOfCurrency.item(i).getChildNodes().item(9).getTextContent());
				leftFind = true;
			}
			if(listOfCurrency.item(i).getChildNodes().item(3).getTextContent().compareTo(MyView.getComboBoxRightValue(isLeft)) == 0){
				rateRight = Double.valueOf(listOfCurrency.item(i).getChildNodes().item(9).getTextContent());
				rightFind = true;
			}
			if(leftFind && rightFind){
				MyView.setTextTFRight(String.valueOf(Double.parseDouble(MyView.getTextTFLeft(isLeft)) * rateLeft / rateRight), isLeft);
				break;
			}
		}
	}
}
