package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.entities.User;
import br.com.ftt.ec6.seniorLiving.exception.RestHomeException;
import br.com.ftt.ec6.seniorLiving.service.RestHomeService;
import br.com.ftt.ec6.seniorLiving.service.UserService;
import br.com.ftt.ec6.seniorLiving.service.impl.RestHomeServiceImpl;
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

public class RestHomeController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/RestHomeList.fxml";
	private final static RestHomeService restHomeService =  RestHomeServiceImpl.getInstance();
	private final static UserService userService = UserServiceImpl.getInstance();
	
	
	@FXML
	private TableView<RestHome> restHomeTable;
	
	@FXML
	private TableColumn<RestHome, String> socialReasonColumn;
	@FXML
	private TableColumn<RestHome, String> cnpjColumn;
	@FXML
	private TableColumn<RestHome, String> addressStateColumn;
	@FXML
	private TableColumn<RestHome, String> addressCepColumn;
	@FXML
	private TableColumn<RestHome, Pane> actionsColumn;
	
	@FXML
	private Button createNewRestHomeButton;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));
	}
	
	private void initializeNodes() {
		load();
	}
	
	public void reloadMe() {
		restHomeTable.getItems().clear();
    	load();
    }
    
    public void addNewRestHomeOnTable(RestHome restHome) {
	    if((restHome == null) || (restHome.getId() == null)) { return; }
	   
	    if(restHomeTable == null) { return; }

	    restHomeTable.getItems().add(restHome);
    }
    
    public void removeRestHomeOnTable(RestHome restHome) {
    	if((restHome == null) || (restHome.getId() == null)) { return; }
    	
    	if(restHomeTable == null) { return; }
    	
    	restHomeTable.getItems().remove(restHome);
    }
    
    public void updateRestHomeOnTable(RestHome oldRestHome, RestHome restHomeUpdated) {
    	if(restHomeTable == null) { return; }
    	
    	if((oldRestHome == null) || (restHomeUpdated == null)) { return; }
    	
    	if(oldRestHome.getId().equals(restHomeUpdated.getId()) == false) {
    		restHomeTable.getItems().clear();
        	load();
    		return; 
    	}
    	
    	restHomeTable.getItems().clear();
    	load();
    }
	
	private void load() {
		List<RestHome> restHomeList = restHomeService.getAll();
		
		socialReasonColumn.setCellValueFactory(new PropertyValueFactory<>("socialReason"));
		cnpjColumn.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
		addressStateColumn.setCellValueFactory(new PropertyValueFactory<>("addressState"));
		addressCepColumn.setCellValueFactory(new PropertyValueFactory<>("addressCep"));
		
		restHomeList.forEach(user -> {
			restHomeTable.getItems().add(user);
		});

		RestHomeController currentRestHomeController = this;
		
		actionsColumn.setCellFactory(param -> new TableCell<RestHome, Pane>() {
			@Override
	        protected void updateItem(Pane item, boolean empty) {
	            super.updateItem(item, empty);
	            if(empty == false) {
	            	RestHome restHome = restHomeTable.getItems().get(getIndex());
	            	
	            	Pane pane = new Pane();
	            	ImageView editImage = ViewUtils.createImageView("EDIT:"+Long.toString(restHome.getId()), EDIT_IMAGE, 25.0, 25.0, true, true, Cursor.HAND);
	    			ImageView deleteImage = ViewUtils.createImageView("DELETE:"+Long.toString(restHome.getId()), DELETE_IMAGE, 25.0, 25.0, true, true, Cursor.HAND);
	    			editImage.setLayoutX(10.0);
	            	deleteImage.setLayoutX(60.0);
	            	
	    			editImage.setOnMouseClicked(editRestHomeButtonAction(restHome, currentRestHomeController));
	    			deleteImage.setOnMouseClicked(deleteRestHomeButtonAction(restHome));
	            	
	            	pane.getChildren().addAll(editImage, deleteImage);
	            	setGraphic(pane);
	            	this.setItem(item);
	            }  
	        }
		});
		
	}
	
	@FXML
	private void openNewRestHomeFormButton() throws IOException {
		RestHomeFormController restHomeFormController = new RestHomeFormController();
		FXMLLoader loader = restHomeFormController.getFXMLLoader();
		Pane pane = loader.load();
		
		RestHomeFormController restHomeFormControllerLoaded = loader.getController();
		restHomeFormControllerLoaded.setCreatedForm(true);
		restHomeFormControllerLoaded.setRestHome(null);
		restHomeFormControllerLoaded.setFather(this);
		restHomeFormControllerLoaded.performReload();
		
		Stage stage = new Stage();
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
		
		restHomeFormControllerLoaded.setStageMe(stage);
	}
	
	private EventHandler<Event> editRestHomeButtonAction(RestHome restHome, RestHomeController father) {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
//				try {
//					UserFormController userFormController = new UserFormController();
//					FXMLLoader loader = userFormController.getFXMLLoader();
//					Pane pane;
//					pane = loader.load();
//					
//					UserFormController userFormControllerLoaded = loader.getController();
//					userFormControllerLoaded.setCreatedForm(false);
//					userFormControllerLoaded.setUser(user);
//					userFormControllerLoaded.setFather(father);
//					userFormControllerLoaded.performReload();
//					
//					Stage stage = new Stage();
//					Scene scene = new Scene(pane);
//					stage.setScene(scene);
//					stage.show();
//					
//					userFormControllerLoaded.setStageMe(stage);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
			}
		};
	}
	
	private EventHandler<Event> deleteRestHomeButtonAction(RestHome restHome) {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
//				int optionChosen = JOptionPane.showConfirmDialog(null,"Deseja excluir o usuário "+ user.getEmail());
//				
//				if(optionChosen != JOptionPane.YES_OPTION) { return; }
//				
//				try {
//					deleteUser(user);
//					JOptionPane.showMessageDialog(null, "Usuário "+user.getEmail()+" deletado com sucesso.");
//					removeUserOnTable(user);
//				}catch (UserException e) {
//					JOptionPane.showMessageDialog(null, e.getMessage());
//				}
				
			}
		};
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
