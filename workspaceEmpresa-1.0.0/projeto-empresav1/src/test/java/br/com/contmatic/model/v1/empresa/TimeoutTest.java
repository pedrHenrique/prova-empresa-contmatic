package br.com.contmatic.model.v1.empresa;

import org.junit.Test;

public class TimeoutTest {

	// Deve falhar
	@SuppressWarnings("java:S2699")
	@Test(timeout = 1000)
	public void teste_timeout_deve_falhar() {
		while(true);
	}
}
