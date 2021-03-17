package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Alerts;
//import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.services.UserService;


public class LoginController implements Initializable {
    
    @FXML
    private TextField textEmail;
    
    @FXML
    private PasswordField textPassword;
    
    @FXML
	private Button btLogar;

	@FXML
	private Button btFechar;
	
	private UserService service;

    
	@Override
    public void initialize(URL url, ResourceBundle db) {
		initializeNodes();
    }
    private void initializeNodes() {
		//Constraints.setTextFieldMaxLength(textEmail, 30);
		//Constraints.setTextFieldMaxLength(textPassword, 30);		
	}
    
    public void setUserSevice(UserService service) {
		this.service = service;
	}    
        
    public void fazerLogin(){  
       
    String email = textEmail.getText().toString();
    String password = textPassword.getText().toString();
        
    boolean logar = service.logar(email, password);
    
    try {
    	if(logar == true)
    	{    		
                infoBox("Login Successfull",null,"Success" );
                System.out.println(email);
				System.out.println(password);                
        }
    	else
    		infoBox("Please enter correct Email and Password", null, "Failed");    	

       }
        catch(Exception e){
			Alerts.showAlert("Error", null, e.getMessage(), AlertType.ERROR);
        }
        
    }
    
    public static void infoBox(String infoMessage, String headerText, String title){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    } 
    
    @FXML
	public void onBtCloseAction(ActionEvent event) {
		Utils.currentStage(event).close();
	} 
    
}




 
    
