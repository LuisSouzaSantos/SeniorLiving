package br.com.ftt.ec6.seniorLiving.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.ftt.ec6.seniorLiving.entities.support.State;

public class SupportProperties {

	private static SupportProperties instance;
	private static List<State> states;
	
	private SupportProperties() {}
	
	public static SupportProperties getInstance() {
		if(instance == null) {
			instance = new SupportProperties();
			loadProperties();
		}
		return instance;
	}
	
	public List<State> getStates(){
		if(states == null) {
			loadState();
		}
		
		return states;
	}
	
	private static void loadProperties() {
		loadState();
	}
	
	private static void loadState() {
		if(states == null) {
			states = new ArrayList<State>();
		}
		
		states.clear();
	
		State[] stateList = ExternoApi.getStateList();
		
		if(stateList == null) { return; }
		
		states.addAll(Arrays.asList(stateList));
	}
	
}
