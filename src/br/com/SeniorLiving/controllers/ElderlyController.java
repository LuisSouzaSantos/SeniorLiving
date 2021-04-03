package br.com.SeniorLiving.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ElderlyController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/Elderly.fxml";

	@FXML
	private TextField txtNome;
	
	@FXML
	private TextField txtEstadoCivil;
	
	@FXML
	private TextField dpBirthDate;
	
	@FXML
	private TextField txtRg;
	
	@FXML
	private TextField txtPgMensal;
	
	@FXML
	private TextField txtCpf;
	
	@FXML
	private TextField txtNacionalidade;
	
	@FXML
	private ComboBox cbCurador;
	
	@FXML
	private ComboBox cbSolidario;
	
	@FXML
	private ComboBox cbAcomodacao;
		
	@FXML
	private Label labelErrorName;
	
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