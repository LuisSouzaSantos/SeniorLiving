package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.com.SeniorLiving.application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuAdminLocalController extends Controller implements Initializable {
	
	private final static String UI_PATH = "/br/com/SeniorLiving/gui/MenuAdminLocal.fxml";

	@FXML
	private AnchorPane containerToScreen;
	
	@FXML 
	private TextField txtVoltar;

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

	@FXML
	private void accommodationButtonClick() throws IOException {
		AccommodationController accommodationController = new AccommodationController();
		FXMLLoader loader = accommodationController.getFXMLLoader();
		VBox vBox = loader.load();
		loader.getController();
		
		containerToScreen.getChildren().clear();
		containerToScreen.getChildren().add(vBox);
		
		
//		Scene futureScene = new Scene(anchorPane);
//		
//		Stage newStage = new Stage();
//		newStage.setScene(futureScene);
//		Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
//        newStage.getIcons().add(anotherIcon);
//		
//		Main.changeStage(newStage);
//		Main.getCurrentStage().close();
	}
	
	@FXML
	private void typeButtonClick() throws IOException {
		TypeListController typeListController = new TypeListController();
		FXMLLoader loader = typeListController.getFXMLLoader();
		VBox vBox = loader.load();
		loader.getController();
		
		containerToScreen.getChildren().clear();
		containerToScreen.getChildren().add(vBox);
		
		
//		Scene futureScene = new Scene(anchorPane);
//		
//		Stage newStage = new Stage();
//		newStage.setScene(futureScene);
//		Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
//        newStage.getIcons().add(anotherIcon);
//		
//		Main.changeStage(newStage);
//		Main.getCurrentStage().close();
	}
	
	@FXML
	private void productButtonClick() throws IOException {
		ProductListController productListController = new ProductListController();
		FXMLLoader loader = productListController.getFXMLLoader();
		VBox vBox = loader.load();
		loader.getController();
		
		containerToScreen.getChildren().clear();
		containerToScreen.getChildren().add(vBox);
		
		
		
//		Scene futureScene = new Scene(anchorPane);
//		
//		Stage newStage = new Stage();
//		newStage.setScene(futureScene);
//		Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
//        newStage.getIcons().add(anotherIcon);
//		
//		Main.changeStage(newStage);
//		Main.getCurrentStage().close();
	}
	
	@FXML
	private void personButtonClick() throws IOException {
		PersonListController personListController = new PersonListController();
		FXMLLoader loader = personListController.getFXMLLoader();
		VBox vBox = loader.load();
		loader.getController();
		
		containerToScreen.getChildren().clear();
		containerToScreen.getChildren().add(vBox);
		
//		Scene futureScene = new Scene(anchorPane);
//		
//		Stage newStage = new Stage();
//		newStage.setScene(futureScene);
//		Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
//        newStage.getIcons().add(anotherIcon);
//		
//		Main.changeStage(newStage);
//		Main.getCurrentStage().close();
	}
	
	@FXML
	private void elderlyButtonClick() throws IOException {
		ElderlyListController elderlyListController = new ElderlyListController();
		FXMLLoader loader = elderlyListController.getFXMLLoader();
		VBox vBox = loader.load();
		loader.getController();
		
		containerToScreen.getChildren().clear();
		containerToScreen.getChildren().add(vBox);
		
//		Scene futureScene = new Scene(anchorPane);
//		
//		Stage newStage = new Stage();
//		newStage.setScene(futureScene);
//		Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
//        newStage.getIcons().add(anotherIcon);
//		
//		Main.changeStage(newStage);
//		Main.getCurrentStage().close();
	}
	
	@FXML
	private void reportButtonClick() throws IOException {
		ReportController reportController = new ReportController();
		FXMLLoader loader = reportController.getFXMLLoader();
		VBox vBox = loader.load();
		loader.getController();
		
		containerToScreen.getChildren().clear();
		containerToScreen.getChildren().add(vBox);
		
//		Scene futureScene = new Scene(anchorPane);
//		
//		Stage newStage = new Stage();
//		newStage.setScene(futureScene);
//		Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
//        newStage.getIcons().add(anotherIcon);
//		
//		Main.changeStage(newStage);
//		Main.getCurrentStage().close();
	}
	
	@FXML
	private void logoutButtonClick() throws IOException{
		LoginController loginController = new LoginController();
		FXMLLoader loader = loginController.getFXMLLoader();
		AnchorPane anchorPane = loader.load();
		loader.getController();
		
		Scene futureScene = new Scene(anchorPane);
		Stage newStage = new Stage();
		newStage.setScene(futureScene);
		Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
		newStage.getIcons().add(anotherIcon);
		loginController.performLogout();
		
		Main.changeStage(newStage);
		Main.getCurrentStage().close();
	}

	@FXML
	public void voltarAction(MouseEvent event) {
	try {
		MenuController menuController = new MenuController();
		FXMLLoader loader = menuController.getFXMLLoader();
		AnchorPane anchorPane = loader.load();
		loader.getController();
		Scene futureScene = new Scene(anchorPane);
		
		Stage newStage = new Stage();
		newStage.setScene(futureScene);
		Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
		newStage.getIcons().add(anotherIcon);
		
		Main.changeStage(newStage);
		Main.getCurrentStage().close();
	} catch (Exception e) {
		e.getMessage();
	}		
	}
}
	
