package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import br.com.SeniorLiving.application.Main;
import br.com.SeniorLiving.controllers.listeners.DataChangeListener;
import br.com.SeniorLiving.db.DbIntegrityException;
import br.com.ftt.ec6.seniorLiving.entities.User;
import br.com.ftt.ec6.seniorLiving.service.UserService;
import br.com.ftt.ec6.seniorLiving.utils.Alerts;
import br.com.ftt.ec6.seniorLiving.utils.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UserListController implements Initializable, DataChangeListener {

	//private UserService service;

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

	
	@FXML
	private void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		User obj = new User();
		//createDialogForm(obj, "/br/com/SeniorLiving/gui/UserForm.fxml", parentStage);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("full_name"));
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email_id"));

		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewUser.prefHeightProperty().bind(stage.heightProperty());
	}

	

	@Override
	public void onDataChanged() {
		//updateTableView();

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
				//button.setOnAction(
						//event -> createDialogForm(obj, "/br/com/SeniorLiving/gui/UserForm.fxml", Utils.currentStage(event)));
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
				//button.setOnAction(event -> removeEntity(obj));
			}
		});
	}
}
