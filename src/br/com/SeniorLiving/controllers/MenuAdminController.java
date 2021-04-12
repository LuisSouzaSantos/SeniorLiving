package br.com.SeniorLiving.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.ftt.ec6.seniorLiving.entities.Role;
import br.com.ftt.ec6.seniorLiving.utils.Colors;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class MenuAdminController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/MenuAdmin.fxml";
	private final static String[] CONTROLLERS_ADMIN_GERAL_LIST = {"RoleController", "UserController", "RestHomeController", "AboutController"};
	private final static String[] CONTROLLERS_ADMIN_LOCAL_LIST = {"AccommodationController", "TypeController", "ProductController", "PersonController", "ElderlyController", "BillingController","AboutController"};
	private final static Integer[] GRID_PANE_AVAILABLE_POSITIONS_X = {1, 1, 1, 1, 1, 1, 1, 1};
	private final static Integer[] GRID_PANE_AVAILABLE_POSITIONS_Y = {0, 1, 2, 3, 4, 5, 6, 7};
	
	@FXML
	private Text menuUserNameText;
	
	@FXML
	private Text menuVersionText;
	
	@FXML
	private GridPane menuGridOption;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initText();
		createTabPaneAccordingRole();
	}

	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));	
	}
	
	private void createTabPaneAccordingRole() {
		Role role = getRoleActived();
		
		switch (role.getName()) {
		case "ADMIN_GERAL":
			createGrid(CONTROLLERS_ADMIN_GERAL_LIST);
			break;
		case "ADMIN_LOCAL":
			createGrid(CONTROLLERS_ADMIN_LOCAL_LIST);
			break;
		default:
			break;
		}
		
		
	}
	
	private void createGrid(String[] menuList) {
		
		for (int i = 0; i < menuList.length; i++) {
			Pane pane = createOption(menuList[i]);
			
			Integer positionX = GRID_PANE_AVAILABLE_POSITIONS_X[i];
			Integer positionY = GRID_PANE_AVAILABLE_POSITIONS_Y[i];
			menuGridOption.add(pane,positionX, positionY);
		}
	}
	
	
	private Pane createOption(String ControllerName) {
		Pane pane = new Pane();
		pane.setId("menuPane"+ControllerName);
		//pane.setPrefHeight(50.0);
		//pane.setPrefWidth(50.0);
		//pane.setStyle("-fx-background-color:"+Colors.WHITE.getHexColor()+";");
		//pane.setOnMouseClicked(getEventButton(role));
		pane.setCursor(Cursor.HAND);
		
//		ImageView imageView = new ImageView();
//		Image image = new Image("/br/com/SeniorLiving/images/user.png");
//		imageView.setImage(image);
//		imageView.setFitHeight(100.0);
//		imageView.setFitWidth(150.0);
//		imageView.setLayoutX(80.0);
//		imageView.setLayoutY(34.0);
//		imageView.setPickOnBounds(true);
//		imageView.setPreserveRatio(true);
		
		Text textRoleName = new Text();
		textRoleName.setText("Papel");
		textRoleName.setLayoutX(90.0);
		textRoleName.setLayoutY(159.0);
		
//		pane.getChildren().add(imageView);
		pane.getChildren().add(textRoleName);
		
//		Integer positionX = GRID_PANE_AVAILABLE_POSITIONS_X[i];
//		Integer positionY = GRID_PANE_AVAILABLE_POSITIONS_Y[i];
		
		return pane;
		//gridPane.add(pane, positionX, positionY);
	}
	
	private void initText() {
		menuVersionText.setText(Controller.VERSION);
		menuUserNameText.setText(getUserLogged().getEmail());
	}

}
