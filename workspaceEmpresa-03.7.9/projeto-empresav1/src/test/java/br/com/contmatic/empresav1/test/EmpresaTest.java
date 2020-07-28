package br.com.contmatic.empresav1.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.contmatic.empresav1.model.Departamento;
import br.com.contmatic.empresav1.model.Empresa;

public class EmpresaTest {
	
	private static final String NULLSTR = null;
	private static final String EMPTYSTR = "";
	private static final Long NULLID = (Long) null;
	private static final Long EMPTYID = (long) 0;
	Empresa empresa = new Empresa();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		new Empresa(1, "TestMatic", "57695925000111", "03575090", "1145649304");
		new Empresa(2,"MarcaoTimatic", "89138206000196", "72150704", "11941063792");
		new Empresa(3, "Softmatiqui", "60449385000109", "57071401", "1104028922");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {	}
	
	/*
	 * Está seção de testes tem o intuito de testar os getters/setters da classe
	 */
	
	@Test
	public void teste_setId_e_getId_correto() {
		empresa.setIdEmpresa(25);
		assertEquals(25, empresa.getIdEmpresa());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void teste_setId_valor_nulo() {
		empresa.setIdEmpresa(NULLID);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_setId_valor_vazio() {
		empresa.setIdEmpresa(EMPTYID);
	}
	
	@Test 
	public void teste_setNome_e_getNome_correto() {
		empresa.setNome("Softmatic");
		assertEquals("Softmatic", empresa.getNome());
	}
	
	@Test(expected = java.lang.NullPointerException.class)
	public void teste_setNome_valor_nulo() {
		empresa.setNome(NULLSTR);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_setNome_valor_vazio() {
		empresa.setNome(EMPTYSTR);
	}
	
	@Test
	public void teste_setCNPJ_e_getCNPJ_correto() {
		empresa.setCnpj("15456145000169");
		assertEquals("15456145000169", empresa.getCnpj());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void teste_setCNPJ_valor_nulo() {
		empresa.setCnpj(NULLSTR);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_setCNPJ_valor_vazio() {
		empresa.setCnpj(EMPTYSTR);
	}
	
	@Test
	public void teste_setCep_e_getCep_correto() {
		empresa.setCep("03575090");
		assertEquals("03575090", empresa.getCep());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void teste_setCep_valor_nulo() {
		empresa.setCep(NULLSTR);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_setCep_valor_vazio() {
		empresa.setCep(EMPTYSTR);
	}
	
	@Test
	public void teste_setTelefone_e_getCep_correto() {
		empresa.setTelefone("11998420563");
		assertEquals("11998420563", empresa.getTelefone());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void teste_setTelefone_valor_nulo() {
		empresa.setTelefone(NULLSTR);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_setTelefone_valor_vazio() {
		empresa.setTelefone(EMPTYSTR);
	}

	/*
	 * Está seção de testes tem o intuito de testar os métodos de listagem
	 */
	
	@Test
	public void teste_toString() {
		assertNotNull(empresa.listarEmpresas());
	}
	
	@Test 
	public void teste_listarEmpresas() {
		assertNotNull(empresa.listarEmpresas());
	}

}
