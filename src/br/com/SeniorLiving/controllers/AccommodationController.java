package br.com.SeniorLiving.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AccommodationController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/AcommodationList.fxml";

	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtDescricao;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
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