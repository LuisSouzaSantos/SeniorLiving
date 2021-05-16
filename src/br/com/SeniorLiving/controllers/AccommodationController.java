package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.com.ftt.ec6.seniorLiving.entities.Accommodation;
import br.com.ftt.ec6.seniorLiving.exception.UserException;
import br.com.ftt.ec6.seniorLiving.service.AccommodationService;
import br.com.ftt.ec6.seniorLiving.service.impl.AccommodationServiceImpl;
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

public class AccommodationController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/AccommodationList.fxml";
	
	private final static AccommodationService accommodationService = (AccommodationService) ServiceProxy.newInstance(AccommodationServiceImpl.getInstance());

	@FXML
	private TableView<Accommodation> accommodationTable;
	
	@FXML
	private TableColumn<Accommodation, String> nameColumn;
	@FXML
	private TableColumn<Accommodation, Pane> actionsColumn;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
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
    	accommodationTable.getItems().clear();
    	load();
    }
    
    public void addNewAccommodationOnTable(Accommodation accommodation) {
	    if((accommodation == null) || (accommodation.getId() == null)) { return; }
	   
	    if(accommodationTable == null) { return; }

	    accommodationTable.getItems().add(accommodation);
    }
    
    public void removeAccommodationOnTable(Accommodation accommodation) {
    	if((accommodation == null) || (accommodation.getId() == null)) { return; }
    	
    	if(accommodationTable == null) { return; }
    	
    	accommodationTable.getItems().remove(accommodation);
    }
	
	private void load() {
		List<Accommodation> accommodations = accommodationService.getAccommodationByRestHome(getRestHomeActived());
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		accommodations.forEach(accommodation -> {
			accommodationTable.getItems().add(accommodation);
		});

		AccommodationController currentAccommodationController = this;
		
		actionsColumn.setCellFactory(param -> new TableCell<Accommodation, Pane>() {
			@Override
	        protected void updateItem(Pane item, boolean empty) {
	            super.updateItem(item, empty);
	            if(empty == false) {
	            	Accommodation accommodation = accommodationTable.getItems().get(getIndex());
	            	
	            	Pane pane = new Pane();
	            	ImageView editImage = ViewUtils.createImageView("EDIT:"+Long.toString(accommodation.getId()), EDIT_IMAGE, 25.0, 25.0, true, true, Cursor.HAND);
	    			ImageView deleteImage = ViewUtils.createImageView("DELETE:"+Long.toString(accommodation.getId()), DELETE_IMAGE, 25.0, 25.0, true, true, Cursor.HAND);
	    			editImage.setLayoutX(10.0);
	            	deleteImage.setLayoutX(60.0);
	            	
	    			editImage.setOnMouseClicked(editAccommodationButtonAction(accommodation, currentAccommodationController));
	    			deleteImage.setOnMouseClicked(deleteAccommodationButtonAction(accommodation));
	            	
	            	pane.getChildren().addAll(editImage, deleteImage);
	            	setGraphic(pane);
	            	this.setItem(item);
	            }  
	        }
		});
	}
	
	@FXML
	private void openAccommodationFormButtonAction() throws IOException {
		AccommodationFormController accommodationFormController = new AccommodationFormController();
		FXMLLoader loader = accommodationFormController.getFXMLLoader();
		Pane pane = loader.load();
		
		AccommodationFormController accommodationFormControllerLoaded = loader.getController();
		accommodationFormControllerLoaded.setCreatedForm(true);
		accommodationFormControllerLoaded.setAccommodation(null);
		accommodationFormControllerLoaded.setFather(this);
		accommodationFormControllerLoaded.performReload();
		
		Stage stage = new Stage();
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
		
		accommodationFormControllerLoaded.setMe(stage);
	}
	
	private EventHandler<Event> editAccommodationButtonAction(Accommodation accommodation, AccommodationController father) {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				try {
					AccommodationFormController accommodationFormController = new AccommodationFormController();
					FXMLLoader loader = accommodationFormController.getFXMLLoader();
					Pane pane = loader.load();
					
					AccommodationFormController accommodationFormControllerLoaded = loader.getController();
					accommodationFormControllerLoaded.setCreatedForm(false);
					accommodationFormControllerLoaded.setAccommodation(accommodation);
					accommodationFormControllerLoaded.setFather(father);
					accommodationFormControllerLoaded.performReload();
					
					Stage stage = new Stage();
					Scene scene = new Scene(pane);
					stage.setScene(scene);
					stage.show();
					
					accommodationFormControllerLoaded.setMe(stage);
					} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	private EventHandler<Event> deleteAccommodationButtonAction(Accommodation accommodation) {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				int optionChosen = JOptionPane.showConfirmDialog(null,"Deseja excluir a acomodação "+ accommodation.getName());
				
				if(optionChosen != JOptionPane.YES_OPTION) { return; }
				
				try {
					deleteAccommodation(accommodation);
					JOptionPane.showMessageDialog(null, "Acomodação "+accommodation.getName()+" deletada com sucesso.");
					removeAccommodationOnTable(accommodation);
				}catch (UserException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
				
			}
		};
	}
	
	private void deleteAccommodation(Accommodation accommodation) throws UserException {
		String messageInfo = accommodationService.delete(accommodation.getId());
		
		if(messageInfo == "ERROR") { throw new UserException("Erro ao excluir a acomodação "+accommodation.getName()); }
	}
}