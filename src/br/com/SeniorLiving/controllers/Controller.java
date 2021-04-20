package br.com.SeniorLiving.controllers;

import br.com.ftt.ec6.seniorLiving.entities.Role;
import br.com.ftt.ec6.seniorLiving.entities.User;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public abstract class Controller {

	protected static final String VERSION = "v1.0.0";
	private static User userLogged;
	private static Role roleActived;

	private static Stage lastStage;
	private static Stage currentStage;

	protected void setUserLogged(User user) {
		Controller.userLogged = user;
	}
	
	protected User getUserLogged() {
		return Controller.userLogged;
	}
	
	protected Role getRoleActived() {
		return Controller.roleActived;
	}

	protected void setRoleActived(Role roleActived) {
		Controller.roleActived = roleActived;
	}
	
	public static void goToNextScene(Stage currentStage, boolean closeCurrentStage, Stage nextStage, boolean isShowAndWait) throws RuntimeException{
		if(nextStage == null) { throw new RuntimeException("Next Stage parameter cannot be null"); }
		
		setLastStage(currentStage);
		setCurrentStage(nextStage);
		
		if(getLastStage() != null && closeCurrentStage) {
			getLastStage().close();
		}
		
		if(isShowAndWait) {
			getCurrentStage().showAndWait();;
		}else {
			getCurrentStage().show();
		}
	
	}
	
	public static Stage getLastStage() {
		return lastStage;
	}

	public static void setLastStage(Stage lastStage) {
		Controller.lastStage = lastStage;
	}

	public static void setCurrentStage(Stage currentStage) {
		Controller.currentStage = currentStage;
	}
	
	public static Stage getCurrentStage() {
		return currentStage;
	}
	
	public static void updateCurrentStage() {
		currentStage.centerOnScreen();
	}
	
	
	public abstract FXMLLoader getFXMLLoader();
	
}
