package br.com.SeniorLiving.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class AboutController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/About.fxml";
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {		
		initializeNodes();
	}
	
	private void initializeNodes() {
	}

	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));
	}

}
