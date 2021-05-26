package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.com.ftt.ec6.seniorLiving.entities.Elderly;
import br.com.ftt.ec6.seniorLiving.exception.UserException;
import br.com.ftt.ec6.seniorLiving.service.ElderlyService;
import br.com.ftt.ec6.seniorLiving.service.impl.ElderlyServiceImpl;
import br.com.ftt.ec6.seniorLiving.utils.ViewUtils;
import javafx.concurrent.Task;
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

public class ElderlyController extends Controller implements Initializable {

	private final static ElderlyService elderlyService = ElderlyServiceImpl.getInstance();
	private final static String UI_PATH = "/br/com/SeniorLiving/gui/ElderlyList.fxml";

	@FXML
	private TableView<Elderly> elderlyTable;
	
	@FXML
	private TableColumn<Elderly, String> nameColumn;
	@FXML
	private TableColumn<Elderly, LocalDate> birthDateColumn;
	@FXML
	private TableColumn<Elderly, String> curatorColumn;
	@FXML
	private TableColumn<Elderly, String> sympatheticColumn;
	@FXML
	private TableColumn<Elderly, Pane> actionsColumn;
	
	@FXML
	private Pane elderlyProgressIndicator;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}
	
	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));
	}
	
	private void initializeNodes() {
		load(elderlyService.getElderlyByRestHome(getRestHomeActived()));
	}
    
    public void addNewElderlyOnTable(Elderly elderly) {
	    if((elderly == null) || (elderly.getId() == null)) { return; }
	   
	    if(elderlyTable == null) { return; }

	    elderlyTable.getItems().add(elderly);
	    elderlyTable.refresh();
    }
    
    public void removeElderlyOnTable(Elderly elderly) {
    	if((elderly == null) || (elderly.getId() == null)) { return; }
    	
    	if(elderlyTable == null) { return; }
    	
    	elderlyTable.getItems().remove(elderly);
    	elderlyTable.refresh();
    }
    
    public void updateElderlyOnTable(Elderly elderly) {
    	if((elderly == null) || (elderly.getId() == null)) { return; }
    	
    	if(elderlyTable == null) { return; }
    	
    	elderlyTable.getItems().clear();
    	load(elderlyService.getElderlyByRestHome(getRestHomeActived()));
    }
	
	private void load(List<Elderly> elderlyList) {		
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		curatorColumn.setCellValueFactory(new PropertyValueFactory<>("curator"));
		sympatheticColumn.setCellValueFactory(new PropertyValueFactory<>("sympathetic"));
		
		elderlyList.forEach(type -> {
			elderlyTable.getItems().add(type);
		});

		ElderlyController currentElderlyController = this;
		
		actionsColumn.setCellFactory(param -> new TableCell<Elderly, Pane>() {
			@Override
	        protected void updateItem(Pane item, boolean empty) {
	            super.updateItem(item, empty);
	            if(empty == false) {
	            	Elderly elderly = elderlyTable.getItems().get(getIndex());
	            	
	            	Pane pane = new Pane();
	            	ImageView editImage = ViewUtils.createImageView("EDIT:"+Long.toString(elderly.getId()), EDIT_IMAGE, 25.0, 25.0, true, true, Cursor.HAND);
	    			ImageView deleteImage = ViewUtils.createImageView("DELETE:"+Long.toString(elderly.getId()), DELETE_IMAGE, 25.0, 25.0, true, true, Cursor.HAND);
	    			editImage.setLayoutX(10.0);
	            	deleteImage.setLayoutX(60.0);
	            	
	    			editImage.setOnMouseClicked(editElderlyButtonAction(elderly, currentElderlyController));
	    			deleteImage.setOnMouseClicked(deleteElderlyButtonAction(elderly));
	            	
	            	pane.getChildren().addAll(editImage, deleteImage);
	            	setGraphic(pane);
	            	this.setItem(item);
	            }  
	        }
		});
	}
	
	@FXML
	private void openElderlyFormButtonAction() throws IOException {
		Task<Object[]> task = new Task<Object[]>() {
			@Override
			protected Object[] call() throws Exception {
				initProgressIndicator();
				ElderlyFormController elderlyFormController = new ElderlyFormController();
				FXMLLoader loader = elderlyFormController.getFXMLLoader();
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
			ElderlyFormController elderlyFormControllerLoaded  = (ElderlyFormController) object[1];
			elderlyFormControllerLoaded.setCreatedForm(true);
			elderlyFormControllerLoaded.setElderly(null);
			elderlyFormControllerLoaded.setFather(this);
			elderlyFormControllerLoaded.performReload();
			
			Stage stage = new Stage();
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			stage.show();
			
			elderlyFormControllerLoaded.setMe(stage);
			stopProgressIndicator();
		});
	
		new Thread(task).start();
	}
	
	private EventHandler<Event> editElderlyButtonAction(Elderly elderly, ElderlyController father) {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				Task<Object[]> task = new Task<Object[]>() {
					@Override
					protected Object[] call() throws Exception {
						initProgressIndicator();
						ElderlyFormController elderlyFormController = new ElderlyFormController();
						FXMLLoader loader = elderlyFormController.getFXMLLoader();
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
					ElderlyFormController elderlyFormControllerLoaded  = (ElderlyFormController) object[1];
					elderlyFormControllerLoaded.setCreatedForm(false);
					elderlyFormControllerLoaded.setElderly(elderly);
					elderlyFormControllerLoaded.setFather(father);
					elderlyFormControllerLoaded.performReload();
					
					Stage stage = new Stage();
					Scene scene = new Scene(pane);
					stage.setScene(scene);
					stage.show();
					
					elderlyFormControllerLoaded.setMe(stage);
					stopProgressIndicator();
				});
			
				new Thread(task).start();
			}
		};
	}
	
	private EventHandler<Event> deleteElderlyButtonAction(Elderly elderly) {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				int optionChosen = JOptionPane.showConfirmDialog(null,"Deseja excluir o idoso "+ elderly.getName());
				
				if(optionChosen != JOptionPane.YES_OPTION) { return; }
				
				try {
					deleteElderly(elderly);
					JOptionPane.showMessageDialog(null, "Idoso "+elderly.getName()+" deletado com sucesso.");
					removeElderlyOnTable(elderly);
				}catch (UserException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
				
			}
		};
	}
	
	private void deleteElderly(Elderly elderly) throws UserException {
		String messageInfo = elderlyService.delete(elderly.getId());
		
		if(messageInfo == "ERROR") { throw new UserException("Erro ao excluir o idosp "+elderly.getName()); }
	}
	
	private void initProgressIndicator() {
		elderlyProgressIndicator.setVisible(true);
	}
	
	private void stopProgressIndicator() {
		elderlyProgressIndicator.setVisible(false);
	}

	


}