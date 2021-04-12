package br.com.SeniorLiving.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import br.com.SeniorLiving.application.Main;
import br.com.ftt.ec6.seniorLiving.utils.Alerts;
import br.com.ftt.ec6.seniorLiving.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainViewController extends Controller implements Initializable{
	
	@FXML
	private MenuItem menuItemPerson;
	
	@FXML
	private MenuItem menuItemDepartment;
	
	@FXML
	private MenuItem menuItemUser;
	
	@FXML
	private MenuItem menuItemAgenda;
	
	@FXML
	private MenuItem loginItemAbout;
	
	@FXML
	private MenuItem menuItem;
	
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	private MenuItem menuItemRelatorio;
	
//	@FXML
//	private void onMenuItemPersonAction() {
//		loadView ("/br/com/SeniorLiving/gui/PersonList.fxml", (PersonListController controller) -> {
//			controller.setPersonService(new PersonService());
//			controller.updateTableView();
//		});
//	}
	
//	@FXML
//	private void onMenuItemDepartmentAction() {
//		loadView("/br/com/SeniorLiving/gui/DepartmentList.fxml", (DepartmentListController controller) -> {
//			controller.setDepartmentService(new DepartmentService());
//			controller.updateTableView();
//		});
//	}
	
//	@FXML
//	private void onMenuItemUserAction() {
//		loadView("/br/com/SeniorLiving/gui/UserList.fxml", (UserListController controller) -> {
//			controller.setUserService(new UserService());
//			controller.updateTableView();
//		});
//	}
	
	@FXML
	private void onMenuItemLoginAction() {
		loadView("/br/com/SeniorLiving/gui/Principal.fxml", x -> {});
		
	}
		
//	@FXML
//	private void onMenuItemAgendaAction() {
//		loadView("/br/com/SeniorLiving/gui/AgendaList.fxml", (AgendaListController controller) -> {
//			controller.setAgendaService(new AgendaService());
//			controller.updateTableView();
//		});
//	}
	
//	@FXML
//	private void onMenuItemFinancialAction() {
//		loadView("/br/com/SeniorLiving/gui/FinancialControlList.fxml", (FinancialListController controller) -> {
//			controller.setFinancialService(new FinancialService());
//			controller.updateTableView();
//		});
//	}
	
	@FXML
	private void onMenuItemAboutAction() {
		loadView("/br/com/SeniorLiving/gui/About.fxml", x -> {});
	}
	
	@FXML
	private void onMenuItemRelatorioAction() {
		loadView("/br/com/SeniorLiving/gui/Report.fxml", x -> {});
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) { }
	
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			T controller = loader.getController();
			initializingAction.accept(controller);
			
		}
		catch (IOException e) {
			Alerts.showAlert("IOException", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
	
	 @FXML
		private void onBtMenu (ActionEvent event) {
			Stage parentStage = Utils.currentStage(event);
			createDialogForm("/br/com/SeniorLiving/gui/Menu.fxml", parentStage);
		}
		
	    private void createDialogForm(String absoluteName, Stage parentStage) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
				Pane pane = loader.load();
				
				Stage dialogStage = new Stage();
				dialogStage.setTitle("Senior Living");
				dialogStage.setScene(new Scene(pane));
				dialogStage.setResizable(false);

				dialogStage.initOwner(parentStage);
				Image anotherIcon = new Image("/br/com/SeniorLiving/images/icon.png");
	            dialogStage.getIcons().add(anotherIcon);
				dialogStage.initModality(Modality.WINDOW_MODAL);
				dialogStage.showAndWait();
			} catch (IOException e) {
				e.printStackTrace();
				Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
			}
		}

		@Override
		public FXMLLoader getFXMLLoader() {
			return null;
		}

}


/*
 * public class Controller  implements Initializable{

    @FXML
    private AnchorPane pane1 ,pane2,pane3,pane4 , opacityPane,drawerPane;


    @FXML
    private ImageView drawerImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        opacityPane.setVisible(false);

        FadeTransition fadeTransition=new FadeTransition(Duration.seconds(0.5),opacityPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition=new TranslateTransition(Duration.seconds(0.5),drawerPane);
        translateTransition.setByX(-600);
        translateTransition.play();


        pane1.setStyle("-fx-background-image: url(\"/sample/1.jpg\")");
        pane2.setStyle("-fx-background-image: url(\"/sample/2.jpg\")");
        pane3.setStyle("-fx-background-image: url(\"/sample/3.jpg\")");
        pane4.setStyle("-fx-background-image: url(\"/sample/4.jpg\")");

        Animation();


        drawerImage.setOnMouseClicked(event -> {


            opacityPane.setVisible(true);

            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.5),opacityPane);
            fadeTransition1.setFromValue(0);
            fadeTransition1.setToValue(0.15);
            fadeTransition1.play();

            TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),drawerPane);
            translateTransition1.setByX(+600);
            translateTransition1.play();
        });

        opacityPane.setOnMouseClicked(event -> {



            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.5),opacityPane);
            fadeTransition1.setFromValue(0.15);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {
                opacityPane.setVisible(false);
            });


            TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),drawerPane);
            translateTransition1.setByX(-600);
            translateTransition1.play();
        });

    }

    public  void  Animation(){


        FadeTransition fadeTransition=new FadeTransition(Duration.seconds(3),pane4);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        fadeTransition.setOnFinished(event -> {
            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(3),pane3);
            fadeTransition1.setFromValue(1);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {

                FadeTransition fadeTransition2=new FadeTransition(Duration.seconds(3),pane2);
                fadeTransition2.setFromValue(1);
                fadeTransition2.setToValue(0);
                fadeTransition2.play();

                fadeTransition2.setOnFinished(event2 -> {

                    FadeTransition fadeTransition00=new FadeTransition(Duration.seconds(3),pane2);
                    fadeTransition00.setFromValue(0);
                    fadeTransition00.setToValue(1);
                    fadeTransition00.play();


                    fadeTransition00.setOnFinished(event3 -> {
                        FadeTransition fadeTransition11=new FadeTransition(Duration.seconds(3),pane3);
                        fadeTransition11.setFromValue(0);
                        fadeTransition11.setToValue(1);
                        fadeTransition11.play();

                        fadeTransition11.setOnFinished(event4 -> {
                            FadeTransition fadeTransition22=new FadeTransition(Duration.seconds(3),pane4);
                            fadeTransition22.setFromValue(0);
                            fadeTransition22.setToValue(1);
                            fadeTransition22.play();

                            fadeTransition22.setOnFinished(event5 -> {
                                Animation();
                            });
                        });


                    });
                });

            });




        });



    }

}
*/
