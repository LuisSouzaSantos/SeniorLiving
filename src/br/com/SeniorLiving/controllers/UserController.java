package br.com.SeniorLiving.controllers;

import java.awt.Button;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.ftt.ec6.seniorLiving.entities.User;
import br.com.ftt.ec6.seniorLiving.service.UserService;
import br.com.ftt.ec6.seniorLiving.service.impl.UserServiceImpl;
import br.com.ftt.ec6.seniorLiving.utils.Utils;
import br.com.ftt.ec6.seniorLiving.utils.ViewUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

public class UserController extends Controller implements Initializable, Runnable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/UserList.fxml";
	private final static String UI_USER_FORM_PATH = "/br/com/SeniorLiving/gui/UserForm.fxml";
	private final static UserService userService =  UserServiceImpl.getInstance();
	
	@FXML
	private TableView<User> userTable;
	
	@FXML
	private TableColumn<User, String> emailColumn;
	
	@FXML
	private TableColumn<User, String> nicknameColumn;
	
	@FXML
	private TableColumn<User, Boolean> statusColumn;
	
	@FXML
	private TableColumn<User, ImageView> actionsColumn;
	
	@FXML
	private Button newUserButton;

    
	@Override
	public void run() {
		initialize(null, null);
	}
	
    @Override
    public void initialize(URL url, ResourceBundle db) {
    	initializeNodes();
    }
    
    public FXMLLoader getUserFormView() {
    	return new FXMLLoader(getClass().getResource(UI_USER_FORM_PATH));
    }

	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));
	}
	
	private void initializeNodes() {
		load();
	}
	
	private void load() {
		List<User> users =  userService.getAll();
		
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		nicknameColumn.setCellValueFactory(new PropertyValueFactory<>("nickname"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("active"));
		
		users.forEach(user -> {
			userTable.getItems().add(user);
		});
		
		userTable.getColumns().forEach(tableColumn -> {
			if(tableColumn.getId() == "actionsColumn") {
				tableColumn.setGraphic(ViewUtils.createImageView("dasdas", "/br/com/SeniorLiving/images/edit.png",  5.0,  5.0, true, true, Cursor.HAND));
			}
		});
		
	}
	
	@FXML
	private void createNewUserButtonAction() throws IOException {
		UserFormController userFormController = new UserFormController();
		FXMLLoader loader = userFormController.getFXMLLoader();
		Pane pane = loader.load();
		
		Stage stage = new Stage();
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
		
		UserFormController userFormControllerControllerLoaded = loader.getController();
		userFormControllerControllerLoaded.setStageMe(stage);
		
	}


    
}
