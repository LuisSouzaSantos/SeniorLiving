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
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuAdminController extends Controller implements Initializable {

	private final static String UI_PATH = "/br/com/SeniorLiving/gui/MenuAdmin.fxml";
	
	private final static String[] CONTROLLERS_ADMIN_GERAL_LIST = {"UserController", "RestHomeController", "AboutController"};
	private final static String[] CONTROLLERS_ADMIN_GERAL_ICONS_LIST = {"/br/com/SeniorLiving/images/user.png", "/br/com/SeniorLiving/images/restHome2.png", "/br/com/SeniorLiving/images/about.png"};
	private final static String[] CONTROLLERS_ADMIN_GERAL_NAMES = {"Usuários", "Casa de Repouso", "Sobre"};
	
	private final static String[] CONTROLLERS_ADMIN_LOCAL_LIST = {"AccommodationController", "TypeController", "ProductController", "PersonController", "ElderlyController", "BillingController","AboutController"};
	private final static String[] CONTROLLERS_ADMIN_LOCAL_ICONS_LIST = {"/br/com/SeniorLiving/images/accommodation.png", "/br/com/SeniorLiving/images/type.png", "/br/com/SeniorLiving/images/product.png", "/br/com/SeniorLiving/images/person.png", "/br/com/SeniorLiving/images/elderly.png", "/br/com/SeniorLiving/images/billing.png", "/br/com/SeniorLiving/images/about.png"};
	private final static String[] CONTROLLERS_ADMIN_LOCAL_NAMES = {"Acomodação", "Tipo", "Produto", "Pessoa", "Idoso", "Faturamento", "Sobre"};
	
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
	private Button gridOptionsButton1;
	@FXML
	private Button gridOptionsButton2;
	@FXML
	private Button gridOptionsButton3;
	@FXML
	private Button gridOptionsButton4;
	@FXML
	private Button gridOptionsButton5;
	@FXML
	private Button gridOptionsButton6;
	@FXML
	private Button gridOptionsButton7;
	
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
	
	@FXML
	private Pane menuAdminProgressIndicator;
	
	@FXML
	private ImageView logoutIconAction;
	
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
				return selectOptionButtonEvent(new UserController());
			case "RestHomeController":
				return selectOptionButtonEvent(new RestHomeController());
			case "AccommodationController":
				return selectOptionButtonEvent(new AccommodationController());
			case "TypeController":
				return selectOptionButtonEvent(new TypeController());
			case "ProductController":
				return selectOptionButtonEvent(new ProductController());
			case "PersonController":
				return selectOptionButtonEvent(new PersonController());
			case "ElderlyController":
				return selectOptionButtonEvent(new ElderlyController());
			case "BillingController":
				return selectOptionButtonEvent(new BillingController());
			default:
				return null;
		}
	}
	
	
	@FXML
	private EventHandler<Event> selectOptionButtonEvent(Controller controllerEntity){
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				cleanContentContainer();
				initProgressIndicator();
				
				Task<Pane> task = new Task<Pane>() {
					@Override
					protected Pane call() throws Exception {
						FXMLLoader loader = controllerEntity.getFXMLLoader();
						Pane pane = loader.load();
					
						return pane;
					}
				};
				
				task.setOnSucceeded(e -> {
					Pane pane = task.getValue();

					contentContainer.getChildren().add(pane);
					stopProgressIndicator();
				});
				
				task.setOnFailed(e -> {
					System.out.println(task.getException());
				});
				
				new Thread(task).start();
			}
		};
	}
	
	private EventHandler<Event> logoutOptionIconEvent() {
		return new EventHandler<Event>() {
			@Override
			public void handle(Event arg0) {
				try {
					performLogout();
					LoginController loginController = new LoginController();
					FXMLLoader loader = loginController.getFXMLLoader();
					AnchorPane anchorPane = loader.load();
					
					Stage stage = Controller.getCurrentStage();
					Scene scene = new Scene(anchorPane);
					stage.setScene(scene);
					
					Controller.goToNextScene(Controller.getCurrentStage(), true, stage, false);
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
				createGridOptionsIcons(CONTROLLERS_ADMIN_GERAL_LIST, CONTROLLERS_ADMIN_GERAL_ICONS_LIST, CONTROLLERS_ADMIN_GERAL_NAMES);
				break;
			case "ADMIN_LOCAL":
				createGridOptionsIcons(CONTROLLERS_ADMIN_LOCAL_LIST, CONTROLLERS_ADMIN_LOCAL_ICONS_LIST, CONTROLLERS_ADMIN_LOCAL_NAMES);
				loadRestHomeCombox();
				menuAdminSelectRestHomePane.setVisible(true);
				break;
			default:
				break;
		}
		
		logoutIconAction.setOnMouseClicked(logoutOptionIconEvent());
	}
	
	private void createGridOptionsIcons(String[] menuList, String[] menuIconsList, String[] menuNames) {
		List<ImageView> imageViewListToGridOptionsIcons = createImageViewListToGridOptionsIcons(menuList, menuIconsList);
		
		if(imageViewListToGridOptionsIcons.size() > MENU_GRID_ICONS_POSITIONS.length) {
			throw new RuntimeException("Number of image view"+imageViewListToGridOptionsIcons.size()+"list is more than menu grid icons");
		}
		
		for (int i = 0; i < imageViewListToGridOptionsIcons.size(); i++) {
			ImageView imageView = imageViewListToGridOptionsIcons.get(i);
			imageView.setOnMouseClicked(getEventButton(menuList[i]));
			menuGridIcons.add(imageView, 0, MENU_GRID_ICONS_POSITIONS[i]);
			buttonToBeUsed(i, menuNames[i]);
		}
	}
	
	private void buttonToBeUsed(int index, String buttonName) {
		switch (index) {
			case 0:
				gridOptionsButton1.setText(buttonName);
				gridOptionsButton1.setVisible(true);
				break;
			case 1:
				gridOptionsButton2.setText(buttonName);
				gridOptionsButton2.setVisible(true);
				break;
			case 2:
				gridOptionsButton3.setText(buttonName);
				gridOptionsButton3.setVisible(true);
				break;
			case 3:
				gridOptionsButton4.setText(buttonName);
				gridOptionsButton4.setVisible(true);
				break;
			case 4:
				gridOptionsButton5.setText(buttonName);
				gridOptionsButton5.setVisible(true);
				break;
			case 5:
				gridOptionsButton6.setText(buttonName);
				gridOptionsButton6.setVisible(true);
				break;
			case 6:
				gridOptionsButton7.setText(buttonName);
				gridOptionsButton7.setVisible(true);
				break;
		default:
			throw new RuntimeException("Button index not found");
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
	
	private void initProgressIndicator() {
		menuAdminProgressIndicator.setVisible(true);
	}
	
	private void stopProgressIndicator() {
		menuAdminProgressIndicator.setVisible(false);
	}
	
	private void loadRestHomeCombox() {
		User userLogged = getUserLogged();
		
		if(userLogged == null) { return; }
		
		List<RestHome> restHomeList = restHomeService.getRestHomeByAdmin(userLogged);
		
		if(restHomeList == null) { return; }
		
		ObservableList<RestHome> observableRestHomeList =  FXCollections.observableArrayList(restHomeList);
		
		menuAdminSelectRestHomeComboBox.setItems(observableRestHomeList);
	}
	
	private void performLogout() {
		setUserLogged(null);
		setRestHomeActived(null);
		setRoleActived(null);
	}

}
