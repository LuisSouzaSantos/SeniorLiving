package br.com.ftt.ec6.seniorLiving.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ftt.ec6.seniorLiving.entities.support.Address;
import br.com.ftt.ec6.seniorLiving.entities.support.State;

public class ExternoApi {
	
	private final static String CEP_URL ="http://viacep.com.br/ws/";
	private final static String STATE_URL = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/";

	public static Address getAddressDescriptionByCEP(String cep) {
		try {
			String json = getJson(CEP_URL+cep+"/json");
			ObjectMapper mapper = new ObjectMapper();
			Address address = mapper.readValue(json, Address.class);
			return address;
		} catch (IOException e) {
			return null;
		}
	}
	
	public static State[] getStateList(){
		try {
			String json = getJson(STATE_URL);
			
			ObjectMapper mapper = new ObjectMapper();
			State[] stateList = mapper.readValue(json, State[].class);
			return stateList;
		} catch (IOException e) {
			return null;
		}
	}
	
	private static String getJson(String urlValue) throws IOException {
		URL url = new URL(urlValue);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		String line;
		BufferedReader br = new BufferedReader(new InputStreamReader((httpURLConnection.getInputStream())));
		String content="";
		
		while((line = br.readLine()) != null) {
			content+=line;
		}
		
		return content;
	}

	public static void main(String[] args) throws IOException {
		getStateList();
	}
	
}
