package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.com.ftt.ec6.seniorLiving.entities.Person;
import br.com.ftt.ec6.seniorLiving.exception.UserException;
import br.com.ftt.ec6.seniorLiving.service.PersonService;
import br.com.ftt.ec6.seniorLiving.service.impl.PersonServiceImpl;
import br.com.ftt.ec6.seniorLiving.service.impl.ServiceProxy;
import br.com.ftt.ec6.seniorLiving.utils.ViewUtils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PersonController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/PersonList.fxml";
	private final static PersonService personService = (PersonService) ServiceProxy.newInstance(PersonServiceImpl.getInstance());
	
	@FXML
	private TableView<Person> personTable;
	
	@FXML
	private TableColumn<Person, String> nameColumn;
	@FXML
	private TableColumn<Person, String> rgColumn;
	@FXML
	private TableColumn<Person, String> cpfColumn;
	@FXML
	private TableColumn<Person, String> emailColumn;
	@FXML
	private TableColumn<Person, Pane> actionsColumn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		load();
	}

	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));
	}
	
	public void reloadMe() {
		personTable.getItems().clear();
    	load();
    }
    
    public void addNewTypeOnTable(Person person) {
	    if((person == null) || (person.getId() == null)) { return; }
	   
	    if(personTable == null) { return; }

	    personTable.getItems().add(person);
    }
    
    public void removeTypeOnTable(Person person) {
    	if((person == null) || (person.getId() == null)) { return; }
    	
    	if(personTable == null) { return; }
    	
    	personTable.getItems().remove(person);
    }
	
	private void load() {
		List<Person> personList = personService.getPersonByRestHome(getRestHomeActived());
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		rgColumn.setCellValueFactory(new PropertyValueFactory<>("rg"));
		cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

		personList.forEach(type -> {
			personTable.getItems().add(type);
		});

		PersonController currentPersonController = this;
		
		actionsColumn.setCellFactory(param -> new TableCell<Person, Pane>() {
			@Override
	        protected void updateItem(Pane item, boolean empty) {
	            super.updateItem(item, empty);
	            if(empty == false) {
	            	Person person = personTable.getItems().get(getIndex());
	            	
	            	Pane pane = new Pane();
	            	ImageView editImage = ViewUtils.createImageView("EDIT:"+Long.toString(person.getId()), EDIT_IMAGE, 25.0, 25.0, true, true, Cursor.HAND);
	    			ImageView deleteImage = ViewUtils.createImageView("DELETE:"+Long.toString(person.getId()), DELETE_IMAGE, 25.0, 25.0, true, true, Cursor.HAND);
	    			editImage.setLayoutX(10.0);
	            	deleteImage.setLayoutX(60.0);
	            	
	    			editImage.setOnMouseClicked(editPersonButtonAction(person, currentPersonController));
	    			deleteImage.setOnMouseClicked(deletePersonButtonAction(person));
	            	
	            	pane.getChildren().addAll(editImage, deleteImage);
	            	setGraphic(pane);
	            	this.setItem(item);
	            }  
	        }
		});
	}
	
	@FXML
	private void openPersonFormButtonAction() throws IOException {
		PersonFormController personFormController = new PersonFormController();
		FXMLLoader loader = personFormController.getFXMLLoader();
		Pane pane = loader.load();
		
		PersonFormController personFormControllerLoaded = loader.getController();
		personFormControllerLoaded.setCreatedForm(true);
		personFormControllerLoaded.setPerson(null);
		personFormControllerLoaded.setFather(this);
		personFormControllerLoaded.performReload();
		
		Stage stage = new Stage();
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
		
		personFormControllerLoaded.setMe(stage);
	}
	
	private EventHandler<Event> editPersonButtonAction(Person person, PersonController father) {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				try {
					PersonFormController personFormController = new PersonFormController();
					FXMLLoader loader = personFormController.getFXMLLoader();
					Pane pane = loader.load();
					
					PersonFormController personFormControllerLoaded = loader.getController();
					personFormControllerLoaded.setCreatedForm(false);
					personFormControllerLoaded.setPerson(person);
					personFormControllerLoaded.setFather(father);
					personFormControllerLoaded.performReload();
					
					Stage stage = new Stage();
					Scene scene = new Scene(pane);
					stage.setScene(scene);
					stage.show();
					
					personFormControllerLoaded.setMe(stage);
				}catch(IOException e) {}
			}
		};
	}
	
	private EventHandler<Event> deletePersonButtonAction(Person person) {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				int optionChosen = JOptionPane.showConfirmDialog(null,"Deseja excluir a pessoa "+person.getName()+"("+person.getCpf()+")");
				
				if(optionChosen != JOptionPane.YES_OPTION) { return; }
				
				try {
					deletePerson(person);
					JOptionPane.showMessageDialog(null, "Pessoa "+person.getName()+"("+person.getCpf()+")"+" deletada com sucesso.");
					removeTypeOnTable(person);
				}catch (UserException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
				
			}
		};
	}
	
	private void deletePerson(Person person) throws UserException {
		String messageInfo = personService.delete(person.getId());
		
		if(messageInfo == "ERROR") { throw new UserException("Erro ao excluir a pessoa "+person.getName()+"("+person.getCpf()+")"); }
	}

}