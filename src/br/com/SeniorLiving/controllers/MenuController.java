package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.ftt.ec6.seniorLiving.entities.Action;
import br.com.ftt.ec6.seniorLiving.entities.Audit;
import br.com.ftt.ec6.seniorLiving.entities.Role;
import br.com.ftt.ec6.seniorLiving.service.AuditService;
import br.com.ftt.ec6.seniorLiving.service.impl.AuditServiceImpl;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/Menu.fxml";
	private final static AuditService audit = AuditServiceImpl.getInstance();
	
	@FXML
	private Pane menuPane1;
	@FXML
	private Circle menuCircle1;
	@FXML
	private ImageView menuUserImage1;
	@FXML
	private Button menuButton1;
	
	@FXML
	private Pane menuPane2;
	@FXML
	private Circle menuCircle2;
	@FXML
	private ImageView menuUserImage2;
	@FXML
	private Button menuButton2;
	
	@FXML
	private Text menuSoftwareVersion;
	
	@FXML
	private Pane menuProgressIndicator;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}
	
	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));
	}
	
	private void initializeNodes() {
		initMenuConfig();
		initVersion();
		loadPane();
	}

	private void loadPane() {
		performAudit(MenuAudit.PERFORM_LOAD_ROLE_PANE);
		List<Role> userLoggedRoleList = getUserLogged().getRoleList();
		
		for (int i = 0; i < userLoggedRoleList.size(); i++) {
			Role role = userLoggedRoleList.get(i);
			
			if(i==0) {
				menuPane1.setVisible(true);
				EventHandler<Event> buttonEvent = getEventButton(role);
				menuCircle1.setOnMouseClicked(buttonEvent);
				menuUserImage1.setOnMouseClicked(buttonEvent);
				menuButton1.setOnMouseClicked(buttonEvent);
				menuButton1.setText(role.getName());
			}
			
			if(i==1) {
				menuPane2.setVisible(true);
				EventHandler<Event> buttonEvent = getEventButton(role);
				menuCircle2.setOnMouseClicked(buttonEvent);
				menuUserImage2.setOnMouseClicked(buttonEvent);
				menuButton2.setOnMouseClicked(buttonEvent);
				menuButton2.setText(role.getName());
			}
		}
	}
	
	private EventHandler<Event> getEventButton(Role role){
		if(role == null) { return null; }
		
		String roleName = role.getName();
		
		switch (roleName) {
			case "ADMIN_GERAL":
				return enterAdminGeralMenuEvent(role);
			case "ADMIN_LOCAL":
				return enterAdminLocalMenuEvent(role);
			default:
				return null;
		}
	}
	
	@FXML
	private EventHandler<Event> enterAdminGeralMenuEvent(Role role) {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				performAudit(MenuAudit.ADMIN_GERAL_BUTTON_CLICKED);
				initProgressIndicator();
				
				Task<AnchorPane> task = new Task<AnchorPane>() {
					@Override
					protected AnchorPane call() throws Exception {
						setRoleActived(role);
						
						MenuAdminController menuAdminController = new MenuAdminController();
						FXMLLoader loader = menuAdminController.getFXMLLoader();
						AnchorPane anchorPane = loader.load();
						
						return anchorPane;
					}
				};
				
				task.setOnSucceeded(e -> {
					AnchorPane anchorPane = task.getValue();
					
					Scene futureScene = new Scene(anchorPane);
					Stage newStage = Controller.getCurrentStage();
					newStage.setScene(futureScene);
					Controller.goToNextScene(Controller.getCurrentStage(), true, newStage, false);
					stopProgressIndicator();
				});
				
				new Thread(task).start();
			}
		};
	}
	
	@FXML
	private EventHandler<Event> enterAdminLocalMenuEvent(Role role) {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				performAudit(MenuAudit.ADMIN_LOCAL_BUTTON_CLICKED);
				initProgressIndicator();
				
				Task<AnchorPane> task = new Task<AnchorPane>() {
					@Override
					protected AnchorPane call() throws Exception {
						setRoleActived(role);
						
						MenuAdminController menuAdminController = new MenuAdminController();
						FXMLLoader loader = menuAdminController.getFXMLLoader();
						AnchorPane anchorPane = loader.load();
						
						return anchorPane;
					}
				};
				
				task.setOnSucceeded(e -> {
					AnchorPane anchorPane = task.getValue();
					
					Scene futureScene = new Scene(anchorPane);
					Stage newStage = Controller.getCurrentStage();
					newStage.setScene(futureScene);
					Controller.goToNextScene(Controller.getCurrentStage(), true, newStage, false);
					stopProgressIndicator();
				});
				
				new Thread(task).start();
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
					
		Controller.goToNextScene(Controller.getCurrentStage(), true, newStage, false);
	}
	
	private void initMenuConfig() {
		menuPane1.setVisible(false);
		menuPane2.setVisible(false);
	}
	
	private void initVersion() {
		menuSoftwareVersion.setText(Controller.VERSION);
	}
	
	private void initProgressIndicator() {
		menuProgressIndicator.setVisible(true);
	}
	
	private void stopProgressIndicator() {
		menuProgressIndicator.setVisible(false);
	}
	
	private void performAudit(MenuAudit menuAudit) {
		switch (menuAudit) {
		case PERFORM_LOAD_ROLE_PANE:
			audit.addToWaitingAuditList(new Audit(System.currentTimeMillis(), "loadPane()", Action.LOADER, getUserLogged().getEmail(), null));
			break;
		case ADMIN_GERAL_BUTTON_CLICKED:
			audit.addToWaitingAuditList(new Audit(System.currentTimeMillis(), "enterAdminGeralMenuEvent()", Action.CHOICE, getUserLogged().getEmail(), null));
			break;
		case ADMIN_LOCAL_BUTTON_CLICKED:
			audit.addToWaitingAuditList(new Audit(System.currentTimeMillis(), "enterAdminLocalMenuEvent()", Action.CHOICE, getUserLogged().getEmail(), null));
			break;
		default:
			break;
		}
	}
	
	private enum MenuAudit{
		PERFORM_LOAD_ROLE_PANE,
		ADMIN_GERAL_BUTTON_CLICKED,
		ADMIN_LOCAL_BUTTON_CLICKED;
	}

}