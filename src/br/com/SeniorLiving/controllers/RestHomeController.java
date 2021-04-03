package br.com.SeniorLiving.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class RestHomeController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/RestHomeList.fxml";
	
	@FXML
	private TextField txtRazaoSocial;
	
	@FXML
	private TextField txtCnpj;
	
	@FXML
	private TextField txtEstado;
	
	@FXML
	private TextField txtRua;
	
	@FXML
	private TextField txtNumero;
	
	@FXML
	private TextField txtCep;	
	
	@FXML
	private TextField txtBairro;
	
	@FXML
	private DatePicker dtFaturamento;	
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	}

	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));
	}

}
