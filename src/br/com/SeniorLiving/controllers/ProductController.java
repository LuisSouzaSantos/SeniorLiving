package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.com.ftt.ec6.seniorLiving.entities.Product;
import br.com.ftt.ec6.seniorLiving.entities.Type;
import br.com.ftt.ec6.seniorLiving.exception.ProductException;
import br.com.ftt.ec6.seniorLiving.exception.UserException;
import br.com.ftt.ec6.seniorLiving.service.ProductService;
import br.com.ftt.ec6.seniorLiving.service.impl.ProductServiceImpl;
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

public class ProductController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/ProductList.fxml";
	private final static ProductService productService = (ProductService) ServiceProxy.newInstance(ProductServiceImpl.getInstance());

	@FXML
	private TableView<Product> productTable;
	
	@FXML
	private TableColumn<Product, String> nameColumn;
	@FXML
	private TableColumn<Product, Pane> actionsColumn;
	
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
		productTable.getItems().clear();
    	load();
    }
    
    public void addNewProductOnTable(Product product) {
	    if((product == null) || (product.getId() == null)) { return; }
	   
	    if(productTable == null) { return; }

	    productTable.getItems().add(product);
    }
    
    public void removeProductOnTable(Product product) {
    	if((product == null) || (product.getId() == null)) { return; }
    	
    	if(productTable == null) { return; }
    	
    	productTable.getItems().remove(product);
    }
    
	private void load() {
		List<Product> productList = productService.getProductByRestHome(getRestHomeActived());
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		productList.forEach(product -> {
			productTable.getItems().add(product);
		});

		ProductController currentProductController = this;
		
		actionsColumn.setCellFactory(param -> new TableCell<Product, Pane>() {
			@Override
	        protected void updateItem(Pane item, boolean empty) {
	            super.updateItem(item, empty);
	            if(empty == false) {
	            	Product product = productTable.getItems().get(getIndex());
	            	
	            	Pane pane = new Pane();
	            	ImageView editImage = ViewUtils.createImageView("EDIT:"+Long.toString(product.getId()), EDIT_IMAGE, 25.0, 25.0, true, true, Cursor.HAND);
	    			ImageView deleteImage = ViewUtils.createImageView("DELETE:"+Long.toString(product.getId()), DELETE_IMAGE, 25.0, 25.0, true, true, Cursor.HAND);
	    			editImage.setLayoutX(10.0);
	            	deleteImage.setLayoutX(60.0);
	            	
	    			editImage.setOnMouseClicked(editProductButtonAction(product, currentProductController));
	    			deleteImage.setOnMouseClicked(deleteProductButtonAction(product));
	            	
	            	pane.getChildren().addAll(editImage, deleteImage);
	            	setGraphic(pane);
	            	this.setItem(item);
	            }  
	        }
		});
	}
	
	@FXML
	private void openProductFormButtonAction() throws IOException {
		ProductFormController productFormController = new ProductFormController();
		FXMLLoader loader = productFormController.getFXMLLoader();
		Pane pane = loader.load();
		
		ProductFormController productFormControllerLoaded = loader.getController();
		productFormControllerLoaded.setCreatedForm(true);
		productFormControllerLoaded.setProduct(null);
		productFormControllerLoaded.setFather(this);
		productFormControllerLoaded.performReload();
		
		Stage stage = new Stage();
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
		
		productFormControllerLoaded.setMe(stage);
	}
	
	private EventHandler<Event> editProductButtonAction(Product product, ProductController father) {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				try {
					ProductFormController productFormController = new ProductFormController();
					FXMLLoader loader = productFormController.getFXMLLoader();
					Pane pane = loader.load();
					
					ProductFormController productFormControllerLoaded = loader.getController();
					productFormControllerLoaded.setCreatedForm(false);
					productFormControllerLoaded.setProduct(product);
					productFormControllerLoaded.setFather(father);
					productFormControllerLoaded.performReload();
					
					Stage stage = new Stage();
					Scene scene = new Scene(pane);
					stage.setScene(scene);
					stage.show();
					
					productFormControllerLoaded.setMe(stage);
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	private EventHandler<Event> deleteProductButtonAction(Product product) {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				int optionChosen = JOptionPane.showConfirmDialog(null,"Deseja excluir o produto "+ product.getName());
				
				if(optionChosen != JOptionPane.YES_OPTION) { return; }
				
				try {
					deleteProduct(product);
					JOptionPane.showMessageDialog(null, "Produto "+product.getName()+" deletado com sucesso.");
					addNewProductOnTable(product);
				}catch (UserException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
				
			}
		};
	}
	
	private void deleteProduct(Product product) throws UserException {
		String messageInfo = productService.delete(product.getId());
		
		if(messageInfo == "ERROR") { throw new UserException("Erro ao excluir o produto "+product.getName()); }
	}
	
}
