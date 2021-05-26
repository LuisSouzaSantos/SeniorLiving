package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.com.ftt.ec6.seniorLiving.entities.Type;
import br.com.ftt.ec6.seniorLiving.exception.TypeException;
import br.com.ftt.ec6.seniorLiving.service.TypeService;
import br.com.ftt.ec6.seniorLiving.service.impl.TypeServiceImpl;
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

public class TypeController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/TypeList.fxml";
	private final static TypeService typeService = TypeServiceImpl.getInstance();

	
	@FXML
	private TableView<Type> typeTable;
	
	@FXML
	private TableColumn<Type, String> nameColumn;
	@FXML
	private TableColumn<Type, Pane> actionsColumn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		load(typeService.getTypeByRestHome(getRestHomeActived()));
	}

	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));
	}
    
    public void addNewTypeOnTable(Type type) {
	    if((type == null) || (type.getId() == null)) { return; }
	   
	    if(typeTable == null) { return; }

	    typeTable.getItems().add(type);
	    typeTable.refresh();
    }
    
    public void removeTypeOnTable(Type type) {
    	if((type == null) || (type.getId() == null)) { return; }
    	
    	if(typeTable == null) { return; }
    	
    	typeTable.getItems().remove(type);
    	typeTable.refresh();
    }
    
    public void updateTypeOnTable(Type type) {
    	if((type == null) || (type.getId() == null)) { return; }
    	
    	if(typeTable == null) { return; }
    	
    	typeTable.getItems().clear();
    	load(typeService.getTypeByRestHome(getRestHomeActived()));
    }
	
	private void load(List<Type> types) {
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		types.forEach(type -> {
			typeTable.getItems().add(type);
		});

		TypeController currentTypeController = this;
		
		actionsColumn.setCellFactory(param -> new TableCell<Type, Pane>() {
			@Override
	        protected void updateItem(Pane item, boolean empty) {
	            super.updateItem(item, empty);
	            if(empty == false) {
	            	Type type = typeTable.getItems().get(getIndex());
	            	
	            	Pane pane = new Pane();
	            	ImageView editImage = ViewUtils.createImageView("EDIT:"+Long.toString(type.getId()), EDIT_IMAGE, 25.0, 25.0, true, true, Cursor.HAND);
	    			ImageView deleteImage = ViewUtils.createImageView("DELETE:"+Long.toString(type.getId()), DELETE_IMAGE, 25.0, 25.0, true, true, Cursor.HAND);
	    			editImage.setLayoutX(10.0);
	            	deleteImage.setLayoutX(60.0);
	            	
	    			editImage.setOnMouseClicked(editTypeButtonAction(type, currentTypeController));
	    			deleteImage.setOnMouseClicked(deleteTypeButtonAction(type));
	            	
	            	pane.getChildren().addAll(editImage, deleteImage);
	            	setGraphic(pane);
	            	this.setItem(item);
	            }  
	        }
		});
	}
	
	@FXML
	private void openTypeFormButtonAction() throws IOException {
		TypeFormController typeFormController = new TypeFormController();
		FXMLLoader loader = typeFormController.getFXMLLoader();
		Pane pane = loader.load();
		
		
		TypeFormController typeFormControllerLoaded = loader.getController();
		typeFormControllerLoaded.setCreatedForm(true);
		typeFormControllerLoaded.setType(null);
		typeFormControllerLoaded.setFather(this);
		typeFormControllerLoaded.performReload();
		
		Stage stage = new Stage();
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
		
		typeFormControllerLoaded.setMe(stage);
	}
	
	private EventHandler<Event> editTypeButtonAction(Type type, TypeController father) {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				try {
					TypeFormController typeFormController = new TypeFormController();
					FXMLLoader loader = typeFormController.getFXMLLoader();
					Pane pane = loader.load();
					
					TypeFormController typeFormControllerLoaded = loader.getController();
					typeFormControllerLoaded.setCreatedForm(false);
					typeFormControllerLoaded.setType(type);
					typeFormControllerLoaded.setFather(father);
					typeFormControllerLoaded.performReload();
					
					Stage stage = new Stage();
					Scene scene = new Scene(pane);
					stage.setScene(scene);
					stage.show();
					
					typeFormControllerLoaded.setMe(stage);
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	private EventHandler<Event> deleteTypeButtonAction(Type type) {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				int optionChosen = JOptionPane.showConfirmDialog(null,"Deseja excluir o tipo "+ type.getName());
				
				if(optionChosen != JOptionPane.YES_OPTION) { return; }
				
				try {
					deleteType(type);
					JOptionPane.showMessageDialog(null, "Tipo "+type.getName()+" deletado com sucesso.");
					removeTypeOnTable(type);
				}catch (TypeException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
				
			}
		};
	}
	
	private void deleteType(Type type) throws TypeException {
		String messageInfo = typeService.delete(type.getId());
		
		if(messageInfo == "ERROR") { throw new TypeException("Erro ao excluir o tipo "+type.getName()); }
		
		if(messageInfo != "SUCCESS") { throw new TypeException(messageInfo); }
	}

}