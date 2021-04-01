package br.com.SeniorLiving.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class PrincipalMenuController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/PrincipalMenu.fxml";
	
	@FXML
	private Button btPerson;
	@FXML
	private Button btElderly;
	@FXML
	private Button btProduct;
	@FXML
	private Button btBilling;
	@FXML
	private Button btAccommodation;
	@FXML
	private Button btType;
	@FXML
	private Button btUser;
	@FXML
	private Button btRole;
	@FXML
	private Button btRestHome;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}
	
	private void initializeNodes() {}

	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));
	}
	

}
