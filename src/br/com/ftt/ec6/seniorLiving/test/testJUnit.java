package br.com.ftt.ec6.seniorLiving.test;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;

public class testJUnit {
	
	//padrao projeto strategy - eliminar excesso de c�digo repetido
	
	@SuppressWarnings("deprecation")
	@Test
	public void Teste() {
		
		int soma = 5 + 3;
		
		Assert.assertEquals(8, soma);
	}
}
