package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import br.com.SeniorLiving.controllers.listeners.DataChangeListener;
import br.com.SeniorLiving.db.DbIntegrityException;
import br.com.SeniorLiving.model.services.DepartmentService;
import br.com.ftt.ec6.seniorLiving.application.Main;
import br.com.ftt.ec6.seniorLiving.entities.Department;
import br.com.ftt.ec6.seniorLiving.entities.Person;
import br.com.ftt.ec6.seniorLiving.service.PersonService;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PersonListController extends Controller implements Initializable {

	//private PersonService service;

	@FXML
	private TableView<Person> tableViewPerson;

	@FXML
	private TableColumn<Person, String> tableColumnName;	

	@FXML
	private TableColumn<Person, Integer> tableColumnState;

	@FXML
	private TableColumn<Person, String> tableColumnNacionality;
	
	@FXML
	private TableColumn<Person, String> tableColumnFunction;
	
	
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
	

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/PersonList.fxml";
	
	private ObservableList<Person> obsList;

	@Override
	public FXMLLoader getFXMLLoader() {
	return new FXMLLoader(getClass().getResource(UI_PATH));

	}

	@FXML
	private void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Person obj = new Person();
		createDialogForm(obj, "/br/com/SeniorLiving/gui/PersonForm.fxml", parentStage);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewPerson.prefHeightProperty().bind(stage.heightProperty());
	}

	private void initializeNodes() {
	}
	
	private void createDialogForm(Person obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			PersonFormController controller = loader.getController();
			//controller.setPerson(obj);
			//controller.setServices(new PersonService(), new DepartmentService());
			//controller.loadAssociateObjects();
			//controller.subscribeDataChangeListener(this);
			//controller.updateFormData();

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
				//button.setOnAction(event -> removeEntity(obj));
			}
		});
	}
}
