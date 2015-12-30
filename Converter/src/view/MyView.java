package view;

import java.time.LocalDate;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import model.MyStage;

import org.w3c.dom.NodeList;

public class MyView{
	private static TabPane tabPane;
	private static Tab tabRates, tabConverter;
	private static AnchorPane APRates, APConverter; 
	
// initialize currency rates
	private static ComboBox<String>[] comboBoxRates;
	private static Label[] labels;
	private static Label labelError;
	private static DatePicker date;
	private static Button updateButton;

// initialize currency converter	
	private static ComboBox<String> comboBoxLeft, comboBoxRight;
	private static ImageView imageFromTo, imageEqual;
	private static TextField textFieldLeft, textFieldRight;
	
	@SuppressWarnings("unchecked")
	private static void init123(Parent root){

		tabPane = (TabPane) root.getChildrenUnmodifiable().get(0);
		tabRates = tabPane.getTabs().get(0);
		tabConverter = tabPane.getTabs().get(1);
		APRates = (AnchorPane) tabRates.getContent();
		APConverter = (AnchorPane) tabConverter.getContent();

		comboBoxRates = new ComboBox[4];
		for (int i = 0; i < comboBoxRates.length; i++) {
			comboBoxRates[i] = (ComboBox<String>) APRates.getChildren().get(i);
		}
		labels = new Label[4];
		for (int i = 0; i < comboBoxRates.length; i++) {
			labels[i] = (Label) APRates.getChildren().get(i+4);
		}
		date = (DatePicker) APRates.getChildren().get(8);
		labelError = (Label) APRates.getChildren().get(9);
		updateButton = (Button) APRates.getChildren().get(10);
		comboBoxLeft = (ComboBox<String>) APConverter.getChildren().get(0);
		comboBoxRight =(ComboBox<String>) APConverter.getChildren().get(1);
		textFieldLeft = (TextField) APConverter.getChildren().get(2);
		textFieldRight = (TextField) APConverter.getChildren().get(3);
		imageFromTo = (ImageView) APConverter.getChildren().get(4);
		imageEqual = (ImageView) APConverter.getChildren().get(5);
	}
	
	public static void start(Stage primaryStage){
		Parent root = null;
		try {		
			root = FXMLLoader.load(MyView.class.getResource("/application/Main.fxml"));
		}
		catch(Exception e) {
			System.err.println("Error. File Main.fxml not found. Exception: " + e);
			System.exit(-1);
		}
			init123(root);
			Scene scene = new Scene(root);

		try{	
			scene.getStylesheets().add(MyView.class.getResource("/application/application.css").toExternalForm());
		} catch(Exception e) {
			System.err.println("Error. File application.css not found. Exception: " + e);
		}	
			MyStage.init(primaryStage);
			//some changes
			primaryStage.setHeight(195);
			primaryStage.setResizable(false);
			primaryStage.setWidth(250);
			primaryStage.setTitle("Currency");
			primaryStage.initStyle(StageStyle.UTILITY);
			primaryStage.setScene(scene);
			primaryStage.setOpacity(0.9);
	}

	public static void show(Stage stage) {
		stage.show();
	}

	public static void setCurrentDate() {
		date.setValue(LocalDate.now());
	}
	public static void setCells() {
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
	}

	public static LocalDate getDateValue() {
		return date.getValue();
	}

	public static void setItemsToComboBoxesR(ObservableList<String> ListOfCurrencyRates) {
		for (ComboBox<String> comboBoxRate : comboBoxRates) {
			comboBoxRate.setItems(ListOfCurrencyRates);
		}
	}

	public static void changeStateToError() {
		for (int i = 0; i < comboBoxRates.length; i++) {
			labels[i].setVisible(false);
			comboBoxRates[i].setVisible(false);
		}
		labelError.setVisible(true);
		updateButton.setVisible(true);
		tabConverter.setDisable(true);
	}

	public static void changeStateToNormal() {
		for (int i = 0; i < comboBoxRates.length; i++) {
			labels[i].setVisible(true);
			if(comboBoxRates[i].getValue() != null)
				comboBoxRates[1].setVisible(true);
		}
		comboBoxRates[0].setVisible(true);
		labelError.setVisible(false);
		updateButton.setVisible(false);
		tabConverter.setDisable(false);
	}

	public static void setComboBoxes234VisFalse() {
		for (int i = 1; i < comboBoxRates.length; i++) {
			comboBoxRates[i].setVisible(false);
		}
	}

	public static String[] getComboBoxesValues() {
		String[] str = new String[6];
		for (int i = 0; i < comboBoxRates.length; i++) {
			str[i] = comboBoxRates[i].getValue();
		}
		str[4] = comboBoxLeft.getValue();
		str[5] = comboBoxRight.getValue();
		return str;
	}

	public static void checkContains(ObservableList<String> ListOfCurrencyRates, String[] str) {
		for (int i = 0; i < comboBoxRates.length; i++) {
			if(ListOfCurrencyRates.contains(str[i]))
				comboBoxRates[i].setValue(str[i]);
			else
				labels[i].setText(null);
		}
		if(ListOfCurrencyRates.contains(str[4]))
			comboBoxLeft.setValue(str[4]);
		else
			textFieldLeft.setText(null);
		if(ListOfCurrencyRates.contains(str[5]))
			comboBoxRight.setValue(str[5]);
		else
			textFieldRight.setText(null);
	}

	public static void updateComboBox(int index, NodeList listOfCurrency){	
		if(comboBoxRates[index].getValue() != null)
			for (int i = 0; i < listOfCurrency.getLength(); i++)	
				if(listOfCurrency.item(i).getChildNodes().item(3).getTextContent().compareTo(comboBoxRates[index].getValue()) == 0){
					labels[index].setText(listOfCurrency.item(i).getChildNodes().item(9).getTextContent());
					if(index < 3){
						if(comboBoxRates[index+1] != null)
							comboBoxRates[index+1].setVisible(true);
						break;
					}
				}
	}

	public static void setLabelError() {
		labelError.setWrapText(true);
		labelError.setText("Server is unavailiable or internet connection is closed.");
	}

	public static void setImageFromTo() {
		try{
			imageFromTo.setImage(new Image(MyView.class.getResourceAsStream("/fromTo.png")));
		}catch(NullPointerException e){
			System.err.println("Error. Image 'fromTo.png' not found. Exception: " + e);
		}
	}
	public static void setImageEqual() {
		try {
			imageEqual.setImage(new Image(MyView.class.getResourceAsStream("/equal.png")));
        } catch (NullPointerException e) {
        	System.err.println("Error. Image 'equal.png' not found. Exception: " + e);
        }
	}

	public static void setItemsToComboBoxesC(ObservableList<String> listOfCurrencyConverter) {
		comboBoxLeft.setItems(listOfCurrencyConverter);
		comboBoxRight.setItems(listOfCurrencyConverter);
	}

	public static boolean getLabelErrorVisibility() {
		return labelError.isVisible();
	}

	public static boolean getTextFieldLeftFocused() {
		return textFieldLeft.isFocused();
	}

	public static boolean getTextFieldRightFocused() {
		return textFieldRight.isFocused();
	}

	public static void selectAllTFLeft() {
		if(textFieldLeft.getSelectedText().length() == 0)
			textFieldLeft.selectAll();
		else
			textFieldLeft.deselect();
	}

	public static void selectAllTFRight() {
		if(textFieldRight.getSelectedText().length() == 0)
			textFieldRight.selectAll();
		else
			textFieldRight.deselect();
	}

	public static int getLengthTFLeft(boolean isLeft) {
		return isLeft ? textFieldLeft.getLength() : textFieldRight.getLength();
	}

	public static IndexRange getSelectionTFLeft(boolean isLeft) {
		return isLeft ? textFieldLeft.getSelection() : textFieldRight.getSelection();
	}

	public static void deleteTextFromTFLeft(int start, int end, boolean isLeft) {
		if(isLeft)
			textFieldLeft.deleteText(start, end); 
		else 
			textFieldRight.deleteText(start, end);
	}

	public static void cleartextFieldRight(boolean isLeft) {
		if(isLeft)
			textFieldRight.clear();
		else
			textFieldLeft.clear();
	}

	public static void appendTFLeft(String text, boolean isLeft) {
		if(isLeft)
			textFieldLeft.appendText(text);
		else
			textFieldRight.appendText(text);
	}

	public static String getComboBoxLeftValue(boolean isLeft) {
		return isLeft ? comboBoxLeft.getValue() : comboBoxRight.getValue();
	}

	public static String getComboBoxRightValue(boolean isLeft) {
		return isLeft ? comboBoxRight.getValue() : comboBoxLeft.getValue();
	}

	public static String getTextTFLeft(boolean isLeft) {
		return isLeft ? textFieldLeft.getText() : textFieldRight.getText();
	}

	public static void setTextTFRight(String text, boolean isLeft) {
		if(isLeft)
			textFieldRight.setText(text);
		else
			textFieldLeft.setText(text);
	}
}
