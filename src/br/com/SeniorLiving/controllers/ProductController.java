package br.com.SeniorLiving.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ProductController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/Product.fxml";

	@FXML
	private TextField txtNome;
	
	@FXML
	private TextField txtDescricao;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		//Constraints.setTextFieldMaxLength(txtEmail, 255);
		//Constraints.setTextFieldMaxLength(txtPassword, 255);
	}


	@Override
	public FXMLLoader getFXMLLoader() {
	return new FXMLLoader(getClass().getResource(UI_PATH));

}
}
