package br.com.SeniorLiving.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RoleController  implements Initializable {
	
	@FXML
	private TextField txtName;
	
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
}
