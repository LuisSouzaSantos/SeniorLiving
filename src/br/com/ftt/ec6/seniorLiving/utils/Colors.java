package br.com.ftt.ec6.seniorLiving.utils;

public enum Colors {
	BLACK("#000000"),
	WHITE("#FFFFFF"),
	DARK_BLUE("#1C2646"),
	LIGHT_BLUE("#2F528F"),
	PURPLE("#8290F0"),
	LIGHT_PURPLE("#C6CEFF"),
	LIGHT_GREY("#E2E2E2");
	
	private String hexColor;
	
	Colors(String hexColor){
		this.hexColor = hexColor;
	}
	
	public String getHexColor() {
		return hexColor;
	}
	
}
