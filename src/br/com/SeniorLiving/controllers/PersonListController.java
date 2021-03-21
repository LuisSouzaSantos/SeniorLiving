package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import br.com.SeniorLiving.application.Main;
import br.com.SeniorLiving.controllers.listeners.DataChangeListener;
import br.com.SeniorLiving.controllers.util.Alerts;
import br.com.SeniorLiving.controllers.util.Utils;
import br.com.SeniorLiving.db.DbIntegrityException;
import br.com.SeniorLiving.model.entities.Department;
import br.com.SeniorLiving.model.entities.Person;
import br.com.SeniorLiving.model.services.DepartmentService;
import br.com.SeniorLiving.model.services.PersonService;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PersonListController implements Initializable, DataChangeListener {

	private PersonService service;

	@FXML
	private TableView<Person> tableViewPerson;

	@FXML
	private TableColumn<Person, Integer> tableColumnId;

	@FXML
	private TableColumn<Person, String> tableColumnName;
	
	@FXML
	private TableColumn<Department, String> tableColumnEmail;
	
	@FXML
	private TableColumn<Department, Date> tableColumnBirthDate;

	@FXML
	private TableColumn<Department, Double> tableColumnBaseSalary;

	@FXML
	private TableColumn<Person, Person> tableColumnEDIT;
	
	@FXML
	private TableColumn<Person, Person> tableColumnREMOVE;

	@FXML
	private Button btNew;
	
	@FXML
	private TextField textSearch;

	@FXML
	private Button btSearch;
	
	private ObservableList<Person> obsList;

	public void setPersonService(PersonService service) {
		this.service = service;
	}

	@FXML
	private void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Person obj = new Person();
		createDialogForm(obj, "/br/com/SeniorLiving/gui/PersonForm.fxml", parentStage);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		Utils.formatTableColumnDate(tableColumnBirthDate, "dd/MM/yyyy");
		tableColumnBaseSalary.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));
		Utils.formatTableColumnDouble(tableColumnBaseSalary, 2);
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewPerson.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		List<Person> list = service.findALL();
		obsList = FXCollections.observableArrayList(list);
		tableViewPerson.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	private void createDialogForm(Person obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			PersonFormController controller = loader.getController();
			controller.setPerson(obj);
			controller.setServices(new PersonService(), new DepartmentService());
			controller.loadAssociateObjects();
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Person data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
			dialogStage.getIcons().add(anotherIcon);
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
		tableColumnEDIT.setCellFactory(param -> new TableCell<Person, Person>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Person obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/br/com/SeniorLiving/gui/PersonForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Person, Person>() {
			private final Button button = new Button("Excluir");

			@Override
			protected void updateItem(Person obj, boolean empty) {
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

	protected void removeEntity(Person obj) {
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
