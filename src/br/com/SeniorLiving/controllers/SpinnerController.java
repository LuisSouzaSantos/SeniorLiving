package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

public class SpinnerController extends Controller implements Initializable {
	
	private final static String UI_PATH = "/br/com/SeniorLiving/gui/Spinner.fxml";

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {}
	
	public static Pane getPane() throws IOException {
		SpinnerController spinnerController = new SpinnerController();
		FXMLLoader loader = spinnerController.getFXMLLoader();
		return loader.load();
	}
	
	public static void closeSpinner() {}

	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));	
	}

}
