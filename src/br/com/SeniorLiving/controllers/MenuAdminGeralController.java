package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.apache.commons.collections4.Get;

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
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	@FXML
	private void roleButtonClick() throws IOException {
		RoleListController menuAdminGeralController = new RoleListController();
		FXMLLoader loader = menuAdminGeralController.getFXMLLoader();
		VBox anchorPane = loader.load();
		loader.getController();
		Scene futureScene = new Scene(anchorPane);
		
		Stage newStage = new Stage();
		newStage.setScene(futureScene);
		Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
		newStage.getIcons().add(anotherIcon);
		
		Main.changeStage(newStage);
		Main.getCurrentStage().close();
	}
	
	@FXML
	private void userButtonClick() throws IOException {
		UserController menuAdminGeralController = new UserController();
		FXMLLoader loader = menuAdminGeralController.getFXMLLoader();
		AnchorPane anchorPane = loader.load();
		loader.getController();
		Scene futureScene = new Scene(anchorPane);
		
		Stage newStage = new Stage();
		newStage.setScene(futureScene);
		Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
		newStage.getIcons().add(anotherIcon);
		
		Main.changeStage(newStage);
		Main.getCurrentStage().close();
	}
	
	@FXML
	private void restHomeButtonClick() throws IOException {
		RestHomeController menuAdminGeralController = new RestHomeController();
		FXMLLoader loader = menuAdminGeralController.getFXMLLoader();
		AnchorPane anchorPane = loader.load();
		loader.getController();
		Scene futureScene = new Scene(anchorPane);
		
		Stage newStage = new Stage();
		newStage.setScene(futureScene);
		Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
		newStage.getIcons().add(anotherIcon);
		
		Main.changeStage(newStage);
		Main.getCurrentStage().close();
	}
	
	@FXML
	private void aboutButtonClick() throws IOException {
		AboutController menuAdminGeralController = new AboutController();
		FXMLLoader loader = menuAdminGeralController.getFXMLLoader();
		AnchorPane anchorPane = loader.load();
		loader.getController();
		Scene futureScene = new Scene(anchorPane);
		
		Stage newStage = new Stage();
		newStage.setScene(futureScene);
		Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
		newStage.getIcons().add(anotherIcon);
		
	//	Main.changeScene(new Scene (newStage));
		Main.changeStage(newStage);
		Main.getCurrentStage().close();
	}
	@FXML
	private void logoutButtonClick(MouseEvent event) {
		System.exit(0);
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
	
	
