package br.com.contmatic.empresav1.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.contmatic.empresav1.model.Departamento;

public class DepartamentoScannerTest {

	/*
	 * Está serie de testes tem o intuito de comprovar os métodos de solicitação de
	 * objetos da classe
	 */

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		new Departamento(10, "Junior", 155);
		new Departamento(11, "Roberto", 285);
		new Departamento(12, "Ana", 405);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Test // Testa criando o obj pela função sem parâmetros
	public void teste_registrar_objeto_Scanner() {
		Departamento test3 = new Departamento();
		test3.registrarDep();
		assertSame(test3, test3.solicitarDep());
		assertNotNull(test3);
	}

	@Test (timeout=100000)
	public void teste_busca_departamento_existente_com_Scanner() {
		Departamento dep = new Departamento();
		System.out.println("\n--Você pode Buscar Departamentos de 10 a 12." + 
		"Ou O próprio departamento que criou anteriormente -- \n");

		assertNotNull("Esperava receber uma lista de objetos", dep.solicitarDep());
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_remover2_objeto() {

	}
}
