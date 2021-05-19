package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.com.ftt.ec6.seniorLiving.entities.RestHome;
import br.com.ftt.ec6.seniorLiving.entities.Role;
import br.com.ftt.ec6.seniorLiving.entities.User;
import br.com.ftt.ec6.seniorLiving.service.RestHomeService;
import br.com.ftt.ec6.seniorLiving.service.impl.RestHomeServiceImpl;
import br.com.ftt.ec6.seniorLiving.utils.ViewUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class MenuAdminController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/MenuAdmin.fxml";
	
	private final static String[] CONTROLLERS_ADMIN_GERAL_LIST = {"UserController", "RestHomeController", "AboutController"};
	private final static String[] CONTROLLERS_ADMIN_GERAL_ICONS_LIST = {"/br/com/SeniorLiving/images/user.png", "/br/com/SeniorLiving/images/restHome.png", "/br/com/SeniorLiving/images/about.png"};
	
	private final static String[] CONTROLLERS_ADMIN_LOCAL_LIST = {"AccommodationController", "TypeController", "ProductController", "PersonController", "ElderlyController", "BillingController","AboutController"};
	private final static String[] CONTROLLERS_ADMIN_LOCAL_ICONS_LIST = {"/br/com/SeniorLiving/images/accommodation.png", "/br/com/SeniorLiving/images/type.png", "/br/com/SeniorLiving/images/product.png", "/br/com/SeniorLiving/images/person.png", "/br/com/SeniorLiving/images/elderly.png", "/br/com/SeniorLiving/images/billing.png", "/br/com/SeniorLiving/images/about.png"};
	
	private final static Integer[] MENU_GRID_ICONS_POSITIONS = {0, 1, 2, 3, 4, 5, 6, 7};
	
	private final static RestHomeService restHomeService = RestHomeServiceImpl.getInstance();
	
	@FXML
	private Text menuUserNameText;
	@FXML
	private Text menuVersionText;
	
	@FXML
	private GridPane menuGridOptions;
	@FXML
	private GridPane menuGridIcons;
	
	@FXML
	private Pane menuAdminPaneLeftContainer;
	@FXML
	private Pane menuAdminPaneTopContainer;
	
	@FXML
	private Pane contentContainer;
	
	@FXML
	private Pane menuAdminSelectRestHomePane;
	@FXML
	private ComboBox<RestHome> menuAdminSelectRestHomeComboBox;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initText();
		initMenuAdmin();
	}

	@Override
	public FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource(UI_PATH));	
	}
	
	private EventHandler<Event> getEventButton(String controller){
		switch (controller) {
			case "UserController":
				return selectUserOptionButtonEvent();
			case "RestHomeController":
				return selectRestHomeOptionButtonEvent();
			case "AccommodationController":
				return selectAccommodationOptionButtonEvent();
			case "TypeController":
				return selectTypeOptionButtonEvent();
			case "ProductController":
				return selectProductOptionButtonEvent();
			case "PersonController":
				return selectPersonOptionButtonEvent();
			case "ElderlyController":
				return selectElderlyOptionButtonEvent();
			case "BillingController":
				return selectBillingOptionButtonEvent();
			default:
				return null;
		}
	}

	@FXML
	private EventHandler<Event> selectUserOptionButtonEvent() {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				try {
					cleanContentContainer();
					UserController userController = new UserController();
					FXMLLoader loader = userController.getFXMLLoader();
					Pane pane = loader.load();
					contentContainer.getChildren().add(pane);
				}catch(IOException e) { e.printStackTrace(); }	
			}
		};
	}
	
	@FXML
	private EventHandler<Event> selectRestHomeOptionButtonEvent() {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				try {
					cleanContentContainer();
					RestHomeController restHomeController = new RestHomeController();
					FXMLLoader loader = restHomeController.getFXMLLoader();
					Pane pane = loader.load();
					contentContainer.getChildren().add(pane);
				}catch(IOException e) { e.printStackTrace(); }	
			}
		};
	}
	
	private EventHandler<Event> selectAccommodationOptionButtonEvent() {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				try {
					cleanContentContainer();
					AccommodationController accommodationController = new AccommodationController();
					FXMLLoader loader = accommodationController.getFXMLLoader();
					Pane pane = loader.load();
					contentContainer.getChildren().add(pane);
				}catch(IOException e) { e.printStackTrace(); }	
			}
		};
	}
	
	private EventHandler<Event> selectTypeOptionButtonEvent() {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				try {
					cleanContentContainer();
					TypeController typeController = new TypeController();
					FXMLLoader loader = typeController.getFXMLLoader();
					Pane pane = loader.load();
					contentContainer.getChildren().add(pane);
				}catch(IOException e) { e.printStackTrace(); }	
			}
		};
	}
	
	private EventHandler<Event> selectProductOptionButtonEvent() {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				try {
					cleanContentContainer();
					ProductController productController = new ProductController();
					FXMLLoader loader = productController.getFXMLLoader();
					Pane pane = loader.load();
					contentContainer.getChildren().add(pane);
				}catch(IOException e) { e.printStackTrace(); }	
			}
		};
	}
	
	private EventHandler<Event> selectPersonOptionButtonEvent() {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				try {
					cleanContentContainer();
					PersonController personController = new PersonController();
					FXMLLoader loader = personController.getFXMLLoader();
					Pane pane = loader.load();
					contentContainer.getChildren().add(pane);
				}catch(IOException e) { e.printStackTrace(); }	
			}
		};
	}
	
	private EventHandler<Event> selectElderlyOptionButtonEvent() {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				try {
					cleanContentContainer();
					ElderlyController elderlyController = new ElderlyController();
					FXMLLoader loader = elderlyController.getFXMLLoader();
					Pane pane = loader.load();
					contentContainer.getChildren().add(pane);
				}catch(IOException e) { e.printStackTrace(); }	
			}
		};
	}
	
	private EventHandler<Event> selectBillingOptionButtonEvent() {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				try {
					cleanContentContainer();
					BillingController billingController = new BillingController();
					FXMLLoader loader = billingController.getFXMLLoader();
					Pane pane = loader.load();
					contentContainer.getChildren().add(pane);
				}catch(IOException e) { e.printStackTrace(); }	
			}
		};
	}
	
	@FXML
	private void menuAdminSelectRestHomeButtonAction() {
		if(menuAdminSelectRestHomeComboBox.getItems() == null) { return; }
		
		User userLogged = getUserLogged();
		RestHome restHomeSelected = menuAdminSelectRestHomeComboBox.getValue();
		
		if(restHomeSelected.getAdmin().getId().equals(userLogged.getId()) == false) { return; }
		
		setRestHomeActived(restHomeSelected);
		menuAdminSelectRestHomePane.setVisible(false);
	}
	
	private void cleanContentContainer() {
		if(contentContainer == null) { return; }
		
		if(contentContainer.getChildren() == null) { return; }
		
		contentContainer.getChildren().clear();
	}
	
	private void initMenuAdmin() {
		Role role = getRoleActived();
		
		switch (role.getName()) {
			case "ADMIN_GERAL":
				createGridOptionsIcons(CONTROLLERS_ADMIN_GERAL_LIST, CONTROLLERS_ADMIN_GERAL_ICONS_LIST);
				break;
			case "ADMIN_LOCAL":
				createGridOptionsIcons(CONTROLLERS_ADMIN_LOCAL_LIST, CONTROLLERS_ADMIN_LOCAL_ICONS_LIST);
				loadRestHomeCombox();
				menuAdminSelectRestHomePane.setVisible(true);
				break;
			default:
				break;
		}
	}
	
	private void createGridOptionsIcons(String[] menuList, String[] menuIconsList) {
		List<ImageView> imageViewListToGridOptionsIcons = createImageViewListToGridOptionsIcons(menuList, menuIconsList);
		
		if(imageViewListToGridOptionsIcons.size() > MENU_GRID_ICONS_POSITIONS.length) {
			throw new RuntimeException("Number of image view"+imageViewListToGridOptionsIcons.size()+"list is more than menu grid icons");
		}
		
		for (int i = 0; i < imageViewListToGridOptionsIcons.size(); i++) {
			ImageView imageView = imageViewListToGridOptionsIcons.get(i);
			imageView.setOnMouseClicked(getEventButton(menuList[i]));
			menuGridIcons.add(imageView, 0, MENU_GRID_ICONS_POSITIONS[i]);
		}
	}
	
	private List<ImageView> createImageViewListToGridOptionsIcons(String[] menuList, String[] menuIconsList) {
		List<ImageView> imageViewList = new ArrayList<ImageView>();
		
		for (int i = 0; i < menuIconsList.length; i++) {
			ImageView imageView = ViewUtils.createImageView(menuList[i], menuIconsList[i], 46.0, 34.0, true, true, Cursor.HAND);
			imageViewList.add(imageView);
		}
		
		return imageViewList;
	}
	
	private void initText() {
		menuVersionText.setText(Controller.VERSION);
		menuUserNameText.setText(getUserLogged().getNickname());
	}
	
	private void loadRestHomeCombox() {
		User userLogged = getUserLogged();
		
		if(userLogged == null) { return; }
		
		List<RestHome> restHomeList = restHomeService.getRestHomeByAdmin(userLogged);
		
		if(restHomeList == null) { return; }
		
		ObservableList<RestHome> observableRestHomeList =  FXCollections.observableArrayList(restHomeList);
		
		menuAdminSelectRestHomeComboBox.setItems(observableRestHomeList);
	}

}
