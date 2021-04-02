package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.com.SeniorLiving.application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MenuController extends Controller implements  Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/Menu.fxml";
	
	@FXML
	private Pane adminGeneralMenuPane;
	@FXML
	private Pane enterInAdminLocalMenuPane;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}
	
	private void initializeNodes() {
	}
	
	@FXML
	private void enterInAdminGeralMenu() throws IOException {
		MenuAdminGeralController menuAdminGeralController = new MenuAdminGeralController();
		FXMLLoader loader = menuAdminGeralController.getFXMLLoader();
		AnchorPane anchorPane = loader.load();
		loader.getController();
		Scene futureScene = new Scene(anchorPane);
		
		Stage newStage = new Stage();
		newStage.setScene(futureScene);
		
		Main.changeStage(newStage);
		Main.getCurrentStage().close();
	}
	
	@FXML
	private void enterInAdminLocalMenu() throws IOException {
		MenuAdminLocalController menuAdminGeralController = new MenuAdminLocalController();
		FXMLLoader loader = menuAdminGeralController.getFXMLLoader();
		AnchorPane anchorPane = loader.load();
		loader.getController();
		Scene futureScene = new Scene(anchorPane);
		
		Stage newStage = new Stage();
		newStage.setScene(futureScene);
		
		Main.changeStage(newStage);
		Main.getCurrentStage().close();
	}

	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));
	}

}
