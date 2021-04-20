package br.com.SeniorLiving.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.ftt.ec6.seniorLiving.entities.User;
import br.com.ftt.ec6.seniorLiving.service.UserService;
import br.com.ftt.ec6.seniorLiving.service.impl.UserServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class UserController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/UserList.fxml";
	private final static UserService userService =  UserServiceImpl.getInstance();
	
	@FXML
	private TableView<User> userTable;
	
	@FXML
	private TableColumn<User, String> emailColumn;
	
	@FXML
	private TableColumn<User, String> nicknameColumn;
	
	@FXML
	private TableColumn<User, Boolean> activeColumn;
	
	@FXML
	private TableColumn<User, String> actionsColumn;

    
    @Override
    public void initialize(URL url, ResourceBundle db) {
    	initializeNodes();
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
		activeColumn.setCellValueFactory(new PropertyValueFactory<>("active"));
		
		users.forEach(user -> {
			userTable.getItems().add(user);
		});
		
		Controller.updateCurrentStage();
		
	}
    
}
