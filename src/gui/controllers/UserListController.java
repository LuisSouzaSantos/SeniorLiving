package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.User;
import model.services.UserService;

public class UserListController implements Initializable, DataChangeListener {

	private UserService service;

	@FXML
	private TableView<User> tableViewUser;

	@FXML
	private TableColumn<User, Integer> tableColumnId;

	@FXML
	private TableColumn<User, String> tableColumnName;
	
	@FXML
	private TableColumn<User, String> tableColumnEmail;

	@FXML
	private TableColumn<User, User> tableColumnEDIT;
	
	@FXML
	private TableColumn<User, User> tableColumnREMOVE;

	@FXML
	private Button btNew;

	private ObservableList<User> obsList;

	public void setUserService(UserService service) {
		this.service = service;
	}

	@FXML
	private void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		User obj = new User();
		createDialogForm(obj, "/gui/UserForm.fxml", parentStage);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("full_name"));
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email_id"));

		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewUser.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<User> list = service.findALL();
		obsList = FXCollections.observableArrayList(list);
		tableViewUser.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	private void createDialogForm(User obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			UserController controller = loader.getController();
			controller.setUser(obj);
			controller.setUserSevice(new UserService());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter User data");
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

	@Override
	public void onDataChanged() {
		updateTableView();

	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<User, User>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(User obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/UserForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<User, User>() {
			private final Button button = new Button("Excluir");

			@Override
			protected void updateItem(User obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	protected void removeEntity(User obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");
		
		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Service was null");
			}
			try  {
				service.remove(obj);
				updateTableView();
			}
			catch (DbIntegrityException e) {
				Alerts.showAlert("Error removint object", null, e.getMessage(), AlertType.ERROR);
			}
		}
	
	}
}
