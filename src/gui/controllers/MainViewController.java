package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.services.AgendaService;
import model.services.DepartmentService;
import model.services.FinancialService;
import model.services.PersonService;
import model.services.UserService;

public class MainViewController implements Initializable{
	
	@FXML
	private MenuItem menuItemPerson;
	
	@FXML
	private MenuItem menuItemDepartment;
	
	@FXML
	private MenuItem menuItemUser;
	
	@FXML
	private MenuItem menuItemAgenda;
	
	@FXML
	private MenuItem loginItemAbout;
	
	@FXML
	private MenuItem menuItem;
	
	@FXML
	private MenuItem menuItemAbout;
	
	
	@FXML
	private void onMenuItemPersonAction() {
		loadView ("/gui/PersonList.fxml", (PersonListController controller) -> {
			controller.setPersonService(new PersonService());
			controller.updateTableView();
		});
	}
	
	@FXML
	private void onMenuItemDepartmentAction() {
		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) -> {
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();
		});
	}
	
	@FXML
	private void onMenuItemUserAction() {
		loadView("/gui/UserList.fxml", (UserListController controller) -> {
			controller.setUserService(new UserService());
			controller.updateTableView();
		});
	}
	
	@FXML
	private void onMenuItemLoginAction() {
		loadView("/gui/Principal.fxml", x -> {});
		
	}
		
	@FXML
	private void onMenuItemAgendaAction() {
		loadView("/gui/AgendaList.fxml", (AgendaListController controller) -> {
			controller.setAgendaService(new AgendaService());
			controller.updateTableView();
		});
	}
	
	@FXML
	private void onMenuItemFinancialAction() {
		loadView("/gui/FinancialControlList.fxml", (FinancialListController controller) -> {
			controller.setFinancialService(new FinancialService());
			controller.updateTableView();
		});
	}
	
	@FXML
	private void onMenuItemAboutAction() {
		loadView("/gui/About.fxml", x -> {});
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	
	}
	
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			T controller = loader.getController();
			initializingAction.accept(controller);
			
		}
		catch (IOException e) {
			Alerts.showAlert("IOException", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
	
	 @FXML
		private void onBtMenu (ActionEvent event) {
			Stage parentStage = Utils.currentStage(event);
			createDialogForm("/gui/Menu.fxml", parentStage);
		}
		
	    private void createDialogForm(String absoluteName, Stage parentStage) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
				Pane pane = loader.load();
				
				Stage dialogStage = new Stage();
				dialogStage.setTitle("Senior Living");
				dialogStage.setScene(new Scene(pane));
				dialogStage.setResizable(false);

				dialogStage.initOwner(parentStage);
				Image anotherIcon = new Image("/gui/images/icon.png");
	            dialogStage.getIcons().add(anotherIcon);
				dialogStage.initModality(Modality.WINDOW_MODAL);
				dialogStage.showAndWait();
			} catch (IOException e) {
				e.printStackTrace();
				Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
			}
		}

}
