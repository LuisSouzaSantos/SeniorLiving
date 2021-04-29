package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.ftt.ec6.seniorLiving.entities.User;
import br.com.ftt.ec6.seniorLiving.service.UserService;
import br.com.ftt.ec6.seniorLiving.service.impl.UserServiceImpl;
import br.com.ftt.ec6.seniorLiving.utils.ViewUtils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UserController extends Controller implements Initializable, Runnable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/UserList.fxml";
	private final static String UI_USER_FORM_PATH = "/br/com/SeniorLiving/gui/UserForm.fxml";
	
	private final static String EDIT_IMAGE = "/br/com/SeniorLiving/images/edit.png";
	private final static String DELETE_IMAGE = "/br/com/SeniorLiving/images/delete.png";
	
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
	private TableColumn<User, Pane> actionsColumn;
	
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
		
		actionsColumn.setCellFactory(param -> new TableCell<User, Pane>() {
			@Override
	        protected void updateItem(Pane item, boolean empty) {
	            super.updateItem(item, empty);
	            if(empty == false) {
	            	User user = userTable.getItems().get(getIndex());
	            	
	            	Pane pane = new Pane();
	            	ImageView editImage = ViewUtils.createImageView("EDIT:"+Long.toString(user.getId()), EDIT_IMAGE, 25.0, 25.0, true, true, Cursor.HAND);
	    			ImageView deleteImage = ViewUtils.createImageView("DELETE:"+Long.toString(user.getId()), DELETE_IMAGE, 25.0, 25.0, true, true, Cursor.HAND);
	    			editImage.setLayoutX(10.0);
	            	deleteImage.setLayoutX(60.0);
	            	
	    			editImage.setOnMouseClicked(editUserButtonAction(user));
	    			deleteImage.setOnMouseClicked(deleteUserButtonAction(user));
	            	
	            	pane.getChildren().addAll(editImage, deleteImage);
	            	setGraphic(pane);
	            	this.setItem(item);
	            }  
	        }
		});

	}
	
	@FXML
	private void createNewUserButtonAction() throws IOException {
		UserFormController userFormController = new UserFormController();
		FXMLLoader loader = userFormController.getFXMLLoader();
		Pane pane = loader.load();
		
		UserFormController userFormControllerLoaded = loader.getController();
		userFormControllerLoaded.setCreatedForm(true);
		userFormControllerLoaded.setUser(null);
		userFormControllerLoaded.performReload();
		
		Stage stage = new Stage();
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
		
		userFormControllerLoaded.setStageMe(stage);
	}
	
	private EventHandler<Event> editUserButtonAction(User user) {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				try {
					UserFormController userFormController = new UserFormController();
					FXMLLoader loader = userFormController.getFXMLLoader();
					Pane pane;
					pane = loader.load();
					
					UserFormController userFormControllerLoaded = loader.getController();
					userFormControllerLoaded.setCreatedForm(false);
					userFormControllerLoaded.setUser(user);
					userFormControllerLoaded.performReload();
					
					Stage stage = new Stage();
					Scene scene = new Scene(pane);
					stage.setScene(scene);
					stage.show();
					
					userFormControllerLoaded.setStageMe(stage);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	private EventHandler<Event> deleteUserButtonAction(User user) {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				System.out.println(user);
			}
		};
	}


    
}
