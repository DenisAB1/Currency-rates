package controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class MyCurrency {
	public static NodeList GetList(DatePicker date, Label label){
		NodeList listOfCurrency = null;
		Document doc;
		String URLStringBase = "http://www.nbrb.by/Services/XmlExRates.aspx?ondate=";
		String URLString;
		URL xmlUrl;
		try {
			//(мес€ц/день/год)

			URLString = URLStringBase;
			URLString = URLString.concat(String.valueOf(date.getValue().getMonthValue()) + "/");
			URLString = URLString.concat(String.valueOf(date.getValue().getDayOfMonth()) + "/");
			URLString = URLString.concat(String.valueOf(date.getValue().getYear()));
			xmlUrl = new URL(URLString);
			InputStream in = xmlUrl.openStream();
			doc = parse(in);
			doc.getDocumentElement().normalize();
			listOfCurrency = doc.getElementsByTagName("Currency");
		} catch (UnknownHostException e) {
			label.setWrapText(true);
			label.setText("Server is unavailible or internet connection is closed.");
			return null;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return listOfCurrency;
	}
	private static Document parse (InputStream is) {
        Document documentTemp = null;
        DocumentBuilderFactory domFactory;
        DocumentBuilder builder;
        try {
            domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setValidating(false);
            domFactory.setNamespaceAware(false);
            builder = domFactory.newDocumentBuilder();
 
            documentTemp = builder.parse(is);
        }
        catch (Exception ex) {
            System.err.println("unable to load XML: " + ex);
        }
        return documentTemp;
    }
	public static void updateComboBox(ComboBox<String> comboBox, ComboBox<String> comboBoxNext, Label label, NodeList listOfCurrency){
		if(comboBox.getValue() != null)
			for (int i = 0; i < listOfCurrency.getLength(); i++)	
				if(listOfCurrency.item(i).getChildNodes().item(3).getTextContent().compareTo(comboBox.getValue()) == 0){
					label.setText(listOfCurrency.item(i).getChildNodes().item(9).getTextContent());
					if(comboBoxNext != null)
						comboBoxNext.setVisible(true);
					break;
				}
		return;
	}
}
