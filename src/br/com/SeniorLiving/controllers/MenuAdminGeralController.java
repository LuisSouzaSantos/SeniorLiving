package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.com.SeniorLiving.application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuAdminGeralController extends Controller implements Initializable {
	
	private final static String UI_PATH = "/br/com/SeniorLiving/gui/MenuAdminGeral.fxml";

	@FXML
	private AnchorPane pane;
	
	@FXML
	private AnchorPane containerToScreen;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	@FXML
	private void roleButtonClick() throws IOException {
		RoleListController menuAdminGeralController = new RoleListController();
		FXMLLoader loader = menuAdminGeralController.getFXMLLoader();
		VBox vBox = loader.load();
		loader.getController();
//		Scene futureScene = new Scene(vBox);
		
		containerToScreen.getChildren().clear();
		containerToScreen.getChildren().add(vBox);
		
//		Stage newStage = new Stage();
//		newStage.setScene(futureScene);
//		Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
//		newStage.getIcons().add(anotherIcon);
//		
//		Main.changeStage(newStage);
//		Main.getCurrentStage().close();
	}
	
	@FXML
	private void userButtonClick() throws IOException {
		UserController userController = new UserController();
		FXMLLoader loader = userController.getFXMLLoader();
		VBox vBox = loader.load();
		loader.getController();
		
		containerToScreen.getChildren().clear();
		containerToScreen.getChildren().add(vBox);
		
//		Scene futureScene = new Scene(anchorPane);
//		
//		Stage newStage = new Stage();
//		newStage.setScene(futureScene);
//		Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
//		newStage.getIcons().add(anotherIcon);
//		
//		Main.changeStage(newStage);
//		Main.getCurrentStage().close();
	}
	
	@FXML
	private void restHomeButtonClick() throws IOException {
		RestHomeController restHomeController = new RestHomeController();
		FXMLLoader loader = restHomeController.getFXMLLoader();
		VBox vBox = loader.load();
		loader.getController();
		
		containerToScreen.getChildren().clear();
		containerToScreen.getChildren().add(vBox);
		
//		Scene futureScene = new Scene(anchorPane);
//		
//		Stage newStage = new Stage();
//		newStage.setScene(futureScene);
//		Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
//		newStage.getIcons().add(anotherIcon);
//		
//		Main.changeStage(newStage);
//		Main.getCurrentStage().close();
	}
	
	@FXML
	private void aboutButtonClick() throws IOException {
		AboutController aboutController = new AboutController();
		FXMLLoader loader = aboutController.getFXMLLoader();
		VBox vBox = loader.load();
		loader.getController();
		
		containerToScreen.getChildren().clear();
		containerToScreen.getChildren().add(vBox);
		
		
//		Scene futureScene = new Scene(anchorPane);
//		
//		Stage newStage = new Stage();
//		newStage.setScene(futureScene);
//		Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
//		newStage.getIcons().add(anotherIcon);
//		
//	//	Main.changeScene(new Scene (newStage));
//		Main.changeStage(newStage);
//		Main.getCurrentStage().close();
	}
	@FXML
	private void logoutButtonClick(MouseEvent event) throws IOException {
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
	
	private void loadPane(String page, String Controller){
	    
	    try {
	        // important: note the changes to the way the FXML is loaded,
	        // using an FXMLLoader instance and the no-arg, instance load()
	        // method, instead of the static load(URL) method:
	        FXMLLoader loader = new FXMLLoader(getClass().getResource(page+".fxml"));
	        

	       // Controller menuAdminGeralController = new Controller();
			//FXMLLoader loader = menuAdminGeralController.getFXMLLoader();
			AnchorPane anchorPane = loader.load();
			loader.getController();
			Scene futureScene = new Scene(anchorPane);
			
			Stage newStage = new Stage();
			newStage.setScene(futureScene);
			
			Main.changeStage(newStage);
			Main.getCurrentStage().close();
			
	    } catch (IOException ex) {
	    	JOptionPane.showMessageDialog(null, "Erro");
	        //Logger.getLogger(sidebarController.class.getName()).log(Level.SEVERE, null, ex);
	    }
	    
	}

	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));
	}
}
	
	
