package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.ftt.ec6.seniorLiving.entities.Role;
import br.com.ftt.ec6.seniorLiving.utils.Colors;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuController extends Controller implements Initializable {

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
	
	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));
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
			pane.setStyle("-fx-background-color:"+Colors.LIGHT_PURPLE.getHexColor()+";");
			pane.setOnMouseClicked(getEventButton(role));
			pane.setCursor(Cursor.HAND);
			
			ImageView imageView = new ImageView();
			Image image = new Image("/br/com/SeniorLiving/images/user.png");
			imageView.setImage(image);
			imageView.setFitHeight(100.0);
			imageView.setFitWidth(150.0);
			imageView.setLayoutX(80.0);
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
	
	private EventHandler<Event> getEventButton(Role role){
		if(role == null) { return null; }
		
		String roleName = role.getName();
		
		switch (roleName) {
		case "ADMIN_GERAL":
			return enterAdminGeralMenuEvent();
		case "ADMIN_LOCAL":
			return enterAdminLocalMenuEvent();
		default:
			return null;
		}
	}
	
	@FXML
	private EventHandler<Event> enterAdminGeralMenuEvent() {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				try {
					MenuAdminGeralController menuAdminGeralController = new MenuAdminGeralController();
					FXMLLoader loader = menuAdminGeralController.getFXMLLoader();
					AnchorPane anchorPane = loader.load();
					loader.getController();
					Scene futureScene = new Scene(anchorPane);
					
					Stage newStage = new Stage();
					newStage.setScene(futureScene);
					
					Controller.goToNextScene(Controller.getCurrentStage(), true, newStage, true);
				}catch(IOException e) { e.printStackTrace(); }	
			}
	
		};
	}
	
	@FXML
	private EventHandler<Event> enterAdminLocalMenuEvent() {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				try {
					MenuAdminLocalController menuAdminGeralController = new MenuAdminLocalController();
					FXMLLoader loader = menuAdminGeralController.getFXMLLoader();
					AnchorPane anchorPane = loader.load();
					loader.getController();
					Scene futureScene = new Scene(anchorPane);
					
					Stage newStage = new Stage();
					newStage.setScene(futureScene);
					
					Controller.goToNextScene(Controller.getCurrentStage(), true, newStage, true);
				}catch(IOException e){ e.printStackTrace(); }
			}
			
		};
		
	}
	
	@FXML
	private void logoutEvent() throws IOException {
		LoginController loginController = new LoginController();
		FXMLLoader loader = loginController.getFXMLLoader();
		AnchorPane anchorPane = loader.load();
		loader.getController();
		Scene futureScene = new Scene(anchorPane);
					
		Stage newStage = new Stage();
		newStage.setScene(futureScene);
					
		Controller.goToNextScene(Controller.getCurrentStage(), true, newStage, true);
	}

}
