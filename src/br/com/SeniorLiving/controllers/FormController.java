package br.com.SeniorLiving.controllers;

import javafx.stage.Stage;

public abstract class FormController<T> extends Controller {
	
	protected Stage me;
	protected boolean isCreatedForm;
	protected T father;

	public abstract void cleanErrorMessages();
	
	public abstract void clearForm();
	
	public void setStageMe(Stage me) {
		this.me = me;
	}
	
	public Stage getStageMe() {
		return this.me;
	}
	
	public void setCreatedForm(boolean isCreatedForm) {
		this.isCreatedForm = isCreatedForm;
	}

	public boolean isCreatedForm() {
		return isCreatedForm;
	}
	
	public void setFather(T father) {
		this.father = father;
	}

	public T getFather() {
		return father;
	}

}
