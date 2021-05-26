package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.exception.RestHomeException;
import br.com.ftt.ec6.seniorLiving.service.RestHomeService;
import br.com.ftt.ec6.seniorLiving.service.impl.RestHomeServiceImpl;
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

public class RestHomeController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/RestHomeList.fxml";
	private final static RestHomeService restHomeService = RestHomeServiceImpl.getInstance();
	
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
	private TextField searchRestHomeSocialReasonField;
	@FXML
	private TextField searchRestHomeCNPJField;
	@FXML
	private TextField searchRestHomeUfField;
	@FXML
	private TextField searchRestHomeCEPField;

	@FXML
	private Button createNewRestHomeButton;
	
	@FXML
	private Pane restHomeProgressIndicator;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));
	}
	
	private void initializeNodes() {
		load(restHomeService.getAll());
	}
    
    public void addNewRestHomeOnTable(RestHome restHome) {
	    if((restHome == null) || (restHome.getId() == null)) { return; }
	   
	    if(restHomeTable == null) { return; }

	    restHomeTable.getItems().add(restHome);
	    restHomeTable.refresh();
    }
    
    public void removeRestHomeOnTable(RestHome restHome) {
    	if((restHome == null) || (restHome.getId() == null)) { return; }
    	
    	if(restHomeTable == null) { return; }
    	
    	restHomeTable.getItems().remove(restHome);
    	restHomeTable.refresh();
    }
    
    public void updateRestHomeOnTable(RestHome restHomeUpdated) {
    	if(restHomeTable == null) { return; }
    	
    	if(restHomeUpdated == null) { return; }
   	
    	restHomeTable.getItems().clear();
    	load(restHomeService.getAll());
    }
	
	private void load(List<RestHome> restHomeList) {
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
		Task<Object[]> task = new Task<Object[]>() {
			@Override
			protected Object[] call() throws Exception {
				initProgressIndicator();
				RestHomeFormController restHomeFormController = new RestHomeFormController();
				FXMLLoader loader = restHomeFormController.getFXMLLoader();
				Pane pane = loader.load();

				Object[] object = new Object[2];
				
				object[0] = pane;
				object[1] = loader.getController();
				
				return object;
			}
		};
		
		task.setOnSucceeded(e -> {
			Object[] object = task.getValue();
			
			Pane pane = (Pane) object[0];
			
			RestHomeFormController restHomeFormControllerLoaded = (RestHomeFormController) object[1];
			restHomeFormControllerLoaded.setCreatedForm(true);
			restHomeFormControllerLoaded.setRestHome(null);
			restHomeFormControllerLoaded.setFather(this);
			restHomeFormControllerLoaded.performReload();
			
			Stage stage = new Stage();
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			stage.show();
			
			restHomeFormControllerLoaded.setStageMe(stage);
			stopProgressIndicator();
		});
		
		new Thread(task).start();
	}
	
	private EventHandler<Event> editRestHomeButtonAction(RestHome restHome, RestHomeController father) {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				Task<Object[]> task = new Task<Object[]>() {
					@Override
					protected Object[] call() throws Exception {
						initProgressIndicator();
						RestHomeFormController restHomeFormController = new RestHomeFormController();
						FXMLLoader loader = restHomeFormController.getFXMLLoader();
						Pane pane = loader.load();

						Object[] object = new Object[2];
							
						object[0] = pane;
						object[1] = loader.getController();
							
						return object;
					}
				};
					
				task.setOnSucceeded(e -> {
					Object[] object = task.getValue();
						
					Pane pane = (Pane) object[0];
						
					RestHomeFormController restHomeFormControllerLoaded = (RestHomeFormController) object[1];
					restHomeFormControllerLoaded.setCreatedForm(false);
					restHomeFormControllerLoaded.setRestHome(restHome);
					restHomeFormControllerLoaded.setFather(father);
					restHomeFormControllerLoaded.performReload();
						
					Stage stage = new Stage();
					Scene scene = new Scene(pane);
					stage.setScene(scene);
					stage.show();
						
					restHomeFormControllerLoaded.setStageMe(stage);
					stopProgressIndicator();
				});
				
				new Thread(task).start();
			}
		};
	}
	
	private EventHandler<Event> deleteRestHomeButtonAction(RestHome restHome) {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				int optionChosen = JOptionPane.showConfirmDialog(null,"Deseja excluir a casa de repouso "+ restHome.getSocialReason()+"("+restHome.getCnpj()+")");
				
				if(optionChosen != JOptionPane.YES_OPTION) { return; }
				
				try {
					deleteRestHome(restHome);
					JOptionPane.showMessageDialog(null, "Casa de Repouso "+restHome.getSocialReason()+"("+restHome.getCnpj()+")"+" deletado com sucesso.");
					removeRestHomeOnTable(restHome);
				}catch (RestHomeException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
				
			}
		};
	}
	
	@FXML
	private void restHomeSearchButtonAction() {
		Task<List<RestHome>> task = new Task<List<RestHome>>() {
			@Override
			protected List<RestHome> call() throws Exception {
				initProgressIndicator();
				restHomeTable.getItems().clear();
				return restHomeService.getRestHomeByFilter(searchRestHomeSocialReasonField.getText(), 
															searchRestHomeCNPJField.getText(), 
																searchRestHomeUfField.getText(), searchRestHomeCEPField.getText());
			}
		};
		
		task.setOnSucceeded(e -> {
			List<RestHome> restHomeList = task.getValue();
			
			load(restHomeList);

			stopProgressIndicator();
		});
		
		new Thread(task).start();
		
	}
	
	@FXML
	private void cleanSearchButtonAction() {
		searchRestHomeSocialReasonField.clear();
		searchRestHomeCNPJField.clear();
		searchRestHomeUfField.clear();
		searchRestHomeCEPField.clear();
	}
	
	private void deleteRestHome(RestHome restHome) throws RestHomeException {
		String messageInfo = restHomeService.delete(restHome.getId());
		
		if(messageInfo == "ERROR") { throw new RestHomeException("Erro ao excluir a casa de repouso "+restHome.getSocialReason()+"("+restHome.getCnpj()+")"); }
	}
	
	private void initProgressIndicator() {
		restHomeProgressIndicator.setVisible(true);
	}
	
	private void stopProgressIndicator() {
		restHomeProgressIndicator.setVisible(false);
	}
	

}
