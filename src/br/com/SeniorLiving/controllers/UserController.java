package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.com.ftt.ec6.seniorLiving.entities.User;
import br.com.ftt.ec6.seniorLiving.exception.UserException;
import br.com.ftt.ec6.seniorLiving.service.UserService;
import br.com.ftt.ec6.seniorLiving.service.impl.UserServiceImpl;
import br.com.ftt.ec6.seniorLiving.utils.ViewUtils;
import javafx.concurrent.Task;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UserController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/UserList.fxml";
	private final static String UI_USER_FORM_PATH = "/br/com/SeniorLiving/gui/UserForm.fxml";
	
	private final static String EDIT_IMAGE = "/br/com/SeniorLiving/images/edit.png";
	private final static String DELETE_IMAGE = "/br/com/SeniorLiving/images/delete.png";
	
	private final static UserService userService = UserServiceImpl.getInstance();
	
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
	private TextField searchUserEmailField;
	@FXML
	private TextField searchUserNicknameField;
	@FXML
	private TextField searchUserActiveField;
	
	@FXML
	private Button newUserButton;
	
	@FXML
	private Pane userProgressIndicator;
	
    @Override
    public void initialize(URL url, ResourceBundle db) {
    	initializeNodes();
    }
    
    @Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));
	}
    
    public FXMLLoader getUserFormView() {
    	return new FXMLLoader(getClass().getResource(UI_USER_FORM_PATH));
    }
    
    public void addNewUserOnTable(User user) {
	    if((user == null) || (user.getId() == null)) { return; }
	   
	    if(userTable == null) { return; }

	    userTable.getItems().add(user);
	    userTable.refresh();
    }
    
    public void removeUserOnTable(User user) {
    	if((user == null) || (user.getId() == null)) { return; }
    	
    	if(userTable == null) { return; }
    	
    	userTable.getItems().remove(user);
    	userTable.refresh();
    }
    
    public void updateUserOnTable(User userUpdated) {
    	if(userTable == null) { return; }
    	
    	if(userUpdated == null) { return; }
    	
    	userTable.getItems().clear();
    	load(userService.getAll());
    }
	
	private void initializeNodes() {
		load(userService.getAll());
	}
	
	private void load(List<User> userList) {
		List<User> users = userList;
		
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		nicknameColumn.setCellValueFactory(new PropertyValueFactory<>("nickname"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("active"));
		
		users.forEach(user -> {
			userTable.getItems().add(user);
		});

		UserController currentUserController = this;
		
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
	            	
	    			editImage.setOnMouseClicked(editUserButtonAction(user, currentUserController));
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
		userFormControllerLoaded.setFather(this);
		userFormControllerLoaded.performReload();
		
		Stage stage = new Stage();
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
		
		userFormControllerLoaded.setStageMe(stage);
	}
	
	@FXML
	private void userSearchButtonAction() {
		Task<List<User>> task = new Task<List<User>>() {
			@Override
			protected List<User> call() throws Exception {
				initProgressIndicator();
				userTable.getItems().clear();
				return userService.getUserByFilter(searchUserEmailField.getText(), searchUserNicknameField.getText(), searchUserActiveField.getText());
			}
		};
		
		task.setOnSucceeded(e -> {
			List<User> userList = task.getValue();
			
			load(userList);

			stopProgressIndicator();
		});
		
		new Thread(task).start();
	}
	
	@FXML
	private void cleanSearchButtonAction() {
		searchUserEmailField.clear();
		searchUserNicknameField.clear();
		searchUserActiveField.clear();
	}
	
	private EventHandler<Event> editUserButtonAction(User user, UserController father) {
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
					userFormControllerLoaded.setFather(father);
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
				int optionChosen = JOptionPane.showConfirmDialog(null,"Deseja excluir o usuário "+ user.getEmail());
				
				if(optionChosen != JOptionPane.YES_OPTION) { return; }
				
				try {
					deleteUser(user);
					JOptionPane.showMessageDialog(null, "Usuário "+user.getEmail()+" deletado com sucesso.");
					removeUserOnTable(user);
				}catch (UserException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
				
			}
		};
	}
	
	private void deleteUser(User user) throws UserException {
		String messageInfo = userService.delete(user.getId());
		
		if(messageInfo == "ERROR") { throw new UserException("Erro ao excluir o usuário "+user.getEmail()); }
	}
	
	private void initProgressIndicator() {
		userProgressIndicator.setVisible(true);
	}
	
	private void stopProgressIndicator() {
		userProgressIndicator.setVisible(false);
	}
    
}