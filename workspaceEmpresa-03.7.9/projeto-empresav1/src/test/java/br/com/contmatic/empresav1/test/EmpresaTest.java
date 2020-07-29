package br.com.contmatic.empresav1.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.contmatic.empresav1.model.Empresa;

public class EmpresaTest {
	
	private static final String NULLSTR = null;
	private static final String EMPTYSTR = "";
	private static final Long NULLID = (Long) null;
	private static final Long EMPTYID = (long) 0;
	private static Empresa exemplo1;
	private Empresa empresa; 
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		exemplo1 = new Empresa();
		
		exemplo1.registrarEmpresa(1, "TestMatic", "57695925000111", "03575090", "1145649304");
		exemplo1.registrarEmpresa(2,"MarcaoTimatic", "89138206000196", "72150704", "11941063792");
		exemplo1.registrarEmpresa(3, "Softmatiqui", "60449385000109", "57071401", "1104028922");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {	
		Empresa.getEmpresaLista().clear();
		exemplo1 = null;
	}
	
	@Before
	public void setUp() throws Exception{
		this.empresa = new Empresa();
	}
	
	@After
	public void tearDown() throws Exception{
		this.empresa = null;
	}
		
	
	// Testar a implementação de testes de criação de objetos NULOS
	
	/*
	 * Está seção de testes tem o intuito de testar os métodos principais
	 * da classe
	 */

	
	@Test // Testa criando o obj pelo construtor
	public void teste_objeto_criado_por_construtor() {
		long id = 5;
		empresa = new Empresa(id, "HoHoHo", "89270828000173", "04789050", "1125064896");
		assertEquals("O Obj esperado era: ", empresa, empresa.solicitarEmpresa(id));
		assertNotNull(empresa.solicitarEmpresa(id));
	}
	
	@Test
	public void teste_objeto_criado_por_metodo_com_parametros() {
		long id = 6;

		empresa.registrarEmpresa(id, "HoHoHo", "89270828000173", "04789050", "1125064896");
		assertEquals("O Obj esperado era:", empresa, empresa.solicitarEmpresa(id));
		assertNotNull(empresa.solicitarEmpresa(id));
	}
	
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_objeto_criado_ja_existente_() {
		long id = 1;
		empresa = new Empresa(id, "HoHoHo", "89270828000173", "04789050", "1125064896");

	}
	
	@Test(expected = java.lang.NullPointerException.class)
	public void teste_objeto_sendo_criado_nulo_() {
		empresa = new Empresa(NULLID, "HoHoHo", "89270828000173", "04789050", "1125064896");

	}
	
	@Test
	public void teste_remocao_objeto_existente() {
		long id = 250;
		assertEquals(new Empresa(id, "HoHoHo", "89270828000173", "04789050", "1125064896"), empresa.removerEmpresa(id));

	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_remocao_objeto_nao_existente() {
		long id = 179;
		empresa.removerEmpresa(id);
	}
	
	public void teste_busca_departamento_existente() {
		assertNotNull("Esperava receber um objeto", empresa.solicitarEmpresa(1));
		assertNotNull("Esperava receber um objeto", empresa.solicitarEmpresa(2));
		assertNotNull("Esperava receber um objeto", empresa.solicitarEmpresa(3));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_busca_departamento_nao_existente() {
		long id = 899;
		empresa.solicitarEmpresa(id); // deve falhar

	}
	
	
	/*
	 * Está seção de testes tem o intuito de testar os getters/setters da classe
	 */
	
	@Test
	public void teste_setId_e_getId_correto() {
		long id = 25;
		empresa.setIdEmpresa(id);
		assertEquals("Error: O valor de ID deveria ser igual", id , empresa.getIdEmpresa());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void teste_setId_valor_nulo() {
		empresa.setIdEmpresa(NULLID);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_setId_valor_vazio() {
		empresa.setIdEmpresa(EMPTYID);
		System.out.println();
	}
	
	@Test 
	public void teste_setNome_e_getNome_correto() {
		String nome = "Softmatic";
		empresa.setNome(nome);
		assertEquals(nome, empresa.getNome());
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
		String cnpj = "15456145000169";
		empresa.setCnpj(cnpj);
		assertEquals(cnpj, empresa.getCnpj().replaceAll("\\D", ""));
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
		String cep = "03575090";
		empresa.setCep(cep);
		assertEquals(cep, empresa.getCep().replaceAll("\\D", ""));
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
	public void teste_setTelefone_e_getTelefone_correto() {
		String telefone = "11998420563";
		empresa.setTelefone(telefone);
		assertEquals(telefone, empresa.getTelefone().replaceAll("\\D", ""));
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
