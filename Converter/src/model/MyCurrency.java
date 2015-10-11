package model;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import view.MyView;

public class MyCurrency {
	private static NodeList list = null;
	public static NodeList GetList(LocalDate date){
		NodeList listOfCurrency = null;
		Document doc;
		String URLStringBase = "http://www.nbrb.by/Services/XmlExRates.aspx?ondate=";
		String URLString;
		URL xmlUrl;
		try {
			//(мес€ц/день/год)
			URLString = URLStringBase;
			URLString = URLString.concat(String.valueOf(date.getMonthValue()) + "/" + 
						String.valueOf(date.getDayOfMonth()) + "/" + String.valueOf(date.getYear()));

			xmlUrl = new URL(URLString);
			InputStream in = xmlUrl.openStream();
			doc = parse(in);

			doc.getDocumentElement().normalize();
			listOfCurrency = doc.getElementsByTagName("Currency");
		} catch (Exception e) {
			MyView.setLabelError();
			return null;
		}
		list = listOfCurrency;
		return listOfCurrency;
	}
	private static Document parse (InputStream is) {
        Document documentTemp = null;
        DocumentBuilderFactory docBuilderFactory;
        DocumentBuilder docBuilder;
        try {
        	docBuilderFactory = DocumentBuilderFactory.newInstance();
        	//docBuilderFactory.setValidating(false);
        	//docBuilderFactory.setNamespaceAware(false);
        	docBuilder = docBuilderFactory.newDocumentBuilder();
 
            documentTemp = docBuilder.parse(is);
        }
        catch (Exception e) {
            System.err.println("Error. Unable to get XML. Exception: " + e);
            return null;
        }
        return documentTemp;
    }
	public static ObservableList<String> getListOfCurrencyRates(){
		ObservableList<String> ListOfCurrencyRates = FXCollections.observableArrayList();
		for (int i = 0; i < list.getLength(); i++){
			ListOfCurrencyRates.add(list.item(i).getChildNodes().item(3).getTextContent());
		}
		return ListOfCurrencyRates;
	}
	public static ObservableList<String> getListOfCurrencyConverter(){
		ObservableList<String> ListOfCurrencyRates = FXCollections.observableArrayList();
		ListOfCurrencyRates = getListOfCurrencyRates();
		ListOfCurrencyRates.add("BYR");
		return ListOfCurrencyRates;
	}
}
