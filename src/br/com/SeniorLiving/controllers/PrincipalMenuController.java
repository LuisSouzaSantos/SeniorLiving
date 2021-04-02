package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import br.com.SeniorLiving.application.Main;
import br.com.ftt.ec6.seniorLiving.utils.Alerts;
import br.com.ftt.ec6.seniorLiving.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PrincipalMenuController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/PrincipalMenu.fxml";
	
	@FXML
	private Button btPerson;
	
	//private Button btElderly;
	@FXML
	private Button btProduct;
	@FXML
	private Button btBilling;
	@FXML
	private Button btAccommodation;
	@FXML
	private Button btType;
	
	//private Button btUser;
	@FXML
	private Button btRole;
	
	//private Button btRestHome;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}
	
	private void initializeNodes() {}

	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));
	}
	

	 /*public void chooseView(String chooseView) throws IOException {
	       switch (chooseView) {
	           case "btPerfil":
	               FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Person.fxml"));
	   			   AnchorPane root1 = fxmlLoader.load();
	   			
	               Stage stage = new Stage();
	               stage.setScene(new Scene(root1));
	               stage.show();

	               break;


	               default:
	                   System.out.println("default");
	                   break;

	       }
	   }*/
}
