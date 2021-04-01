package br.com.ftt.ec6.seniorLiving.utils;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Constraints {
	
	public static void setTextFieldInteger(TextField txt) {
		if(txt == null) { throw new RuntimeException("ERROR: TEXT_FIELD_INTEGER"); }
		
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if(newValue != null && !newValue.matches("\\d*")) {
				txt.setText(oldValue);
			}
		});
	}
	
	public static void setTextFieldMaxLength(TextField txt, int max) {
		if(txt == null) { throw new RuntimeException("ERROR: TEXT_FIELD_MAX"); }
		
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if(newValue != null && newValue.length() > max) {
				txt.setText(oldValue);
			}
		});
	}
	
	public static void setTextFieldDouble(TextField txt) {
		if(txt == null) { throw new RuntimeException("ERROR: TEXT_FIELD_DOUBLE"); }
		
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if(newValue != null && !newValue.matches("\\d*([\\.]\\d*)?")) {
				txt.setText(oldValue);
			}
		});	}
	
	public static void setTextAreaMaxLength(TextArea txt, int max) {
		if(txt == null) { throw new RuntimeException("ERROR: TEXT_FIELD_AREA_MAX_LENGTH"); }
		
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if(newValue != null && newValue.length() > max) {
				txt.setText(oldValue);
			}
		});
	}
	
}
