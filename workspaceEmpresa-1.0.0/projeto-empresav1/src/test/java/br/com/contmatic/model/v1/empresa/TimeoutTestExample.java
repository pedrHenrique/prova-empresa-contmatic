package br.com.contmatic.model.v1.empresa;

import static org.junit.Assert.*;

import org.junit.Test;

public class TimeoutTestExample {

	private Boolean test = true;

	@Test(timeout = 3000)
	public void teste_timeout_deve_falhar() {
		assertTrue(test);
		while(true);
	} 
}
