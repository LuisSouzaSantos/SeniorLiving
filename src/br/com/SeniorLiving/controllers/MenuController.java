package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.SeniorLiving.application.Main;
import br.com.ftt.ec6.seniorLiving.entities.Role;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuController extends Controller implements  Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/Menu.fxml";
	
	private final static String USERNAME_MENU_TEXT_TO_CHANGE = "{username}";
	private final static Integer[] GRID_PANE_AVAILABLE_POSITIONS_X = {0, 2};
	private final static Integer[] GRID_PANE_AVAILABLE_POSITIONS_Y = {1, 1};
	
	@FXML
	private Pane adminGeneralMenuPane;
	@FXML
	private Pane enterInAdminLocalMenuPane;
	
	@FXML
	private Text usernameMenu;
	
	@FXML
	private GridPane gridPane;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		loadUserName();
		loadPane();
	}
	
	private void loadUserName() {
		String currentText = usernameMenu.getText();
		String menuTextUpdate = currentText.replace(USERNAME_MENU_TEXT_TO_CHANGE, userLogged.getEmail());
		usernameMenu.setText(menuTextUpdate);
	}
	
	private void loadPane() {
		List<Role> userLoggedRoleList = userLogged.getRoleList();
		
		for (int i = 0; i < userLoggedRoleList.size(); i++) {
			Role role = userLoggedRoleList.get(i);
			Pane pane = new Pane();
			pane.setId(role.getName().toLowerCase()+"MenuPane");
			pane.setPrefHeight(50.0);
			pane.setPrefWidth(50.0);
			pane.setStyle("-fx-background-color: #4A5796;");
			
			ImageView imageView = new ImageView();
			Image image = new Image("/br/com/SeniorLiving/images/user.png");
			imageView.setImage(image);
			imageView.setFitHeight(100.0);
			imageView.setFitWidth(150.0);
			imageView.setLayoutX(50.0);
			imageView.setLayoutY(34.0);
			imageView.setPickOnBounds(true);
			imageView.setPreserveRatio(true);
			
			Text textRoleName = new Text();
			textRoleName.setText(role.getName());
			textRoleName.setLayoutX(90.0);
			textRoleName.setLayoutY(159.0);
			
			pane.getChildren().add(imageView);
			pane.getChildren().add(textRoleName);
			
			Integer positionX = GRID_PANE_AVAILABLE_POSITIONS_X[i];
			Integer positionY = GRID_PANE_AVAILABLE_POSITIONS_Y[i];
			
			gridPane.add(pane, positionX, positionY);
		}
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
