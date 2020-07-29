package br.com.contmatic.empresav1.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.contmatic.empresav1.model.Departamento;

public class DepartamentoTest {
	
	private static final String NULLSTR = null;
	private static final String EMPTYSTR = "";
	private static final Long NULLID = (Long) null;
	private static final Long EMPTYID = (long) 0;
	private static Departamento departamento; 
	private Departamento dep;
	
	private static final Object NULLINT = null;
	private static final int EMPTYINT = 0;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		departamento = new Departamento();
		
		departamento.registrarDep(1, "Contábil", 155);
		departamento.registrarDep(2, "Recursos Humanos", 285);
		departamento.registrarDep(3, "Tecnologias", 405);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {	
		Departamento.getDepartamentoLista().clear();
		departamento = null;
	}
	
	@Before
	public void setUp() throws Exception{
		this.dep = new Departamento();
	}
	
	@After
	public void tearDown() throws Exception{
		this.dep = null;
	}

	/*
	 * Está seção de testes tem o intuito de testar os métodos de criação dos
	 * objetos da classe
	 */

	@Test // Testa criando o obj pelo construtor
	public void teste_objeto_criado_por_construtor() {
		long id = 10;

		dep = new Departamento(id, "Financeiro", 226);
		assertEquals("O Obj esperado era: ", dep, dep.solicitarDep(id));
		assertNotNull(dep.solicitarDep(id));
	}

	@Test
	public void teste_objeto_criado_por_metodo_com_parametros() {
		long id = 11;

		dep = new Departamento();
		dep.registrarDep(id, "Expedição", 189);
		assertEquals("O Obj esperado era:", dep, dep.solicitarDep(id));
		assertNotNull(dep.solicitarDep(id));

	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_objeto_criado_ja_existente_() {
		long id = 1;
		dep.registrarDep(id, "Financeiro", 226);
	}
	
	@Test(expected = java.lang.NullPointerException.class)
	public void teste_objeto_sendo_criado_nulo_() {
		dep = new Departamento(NULLID, "Qualidade", 250);

	}

	/*
	 * Está seção de testes tem o intuito de testar os métodos de remoção dos
	 * objetos da classe
	 */

	@Test
	public void teste_remocao_objeto_existente() {
		long id = 250;
		assertEquals(new Departamento(id, "Rogerio", 145), dep.removerDep(id));

	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_remocao_objeto_nao_existente() {
		long id = 179;
		dep.removerDep(id);
	}

	/*
	 * Está seção de testes tem o intuito de testar métodos de busca de objetos da
	 * classe
	 */

	@Test // Testando a busca por objetos num HashSet
	public void teste_busca_departamento_existente() {
		assertNotNull("Esperava receber um objeto", dep.solicitarDep(1));
		assertNotNull("Esperava receber um objeto", dep.solicitarDep(2));
		assertNotNull("Esperava receber um objeto", dep.solicitarDep(3));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_busca_departamento_nao_existente() {
		dep.solicitarDep(50); // deve falhar

	}

	/*
	 * Está seção de testes tem o intuito de testar os getters/setters da classe
	 */

	@Test
	public void teste_setNome_e_getNome_nome_correto() {
		String name = new String("Gerencia");
		dep.setNome(name);
		assertEquals(name, dep.getNome());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void teste_setNome_valor_nulo() {
		dep.setNome(NULLSTR);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_setNome_valor_vazio() {
		dep.setNome(EMPTYSTR);
	}

	@Test
	public void teste_setId_e_getId_correto() {
		long id = 25;
		dep.setIdDepartamento(id);
		assertEquals(id, dep.getIdDepartamento());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void teste_setId_valor_nulo() {
		dep.setIdDepartamento(NULLID);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_setId_valor_vazio() {
		dep.setIdDepartamento(EMPTYID);
	}

	@Test
	public void teste_setRamal_e_getId_correto() {
		int num = 456;
		dep.setRamal(num);
		assertEquals(num, dep.getRamal());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void teste_setRamal_valor_nulo() {
		dep.setRamal((int) NULLINT);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_setRamal_valor_vazio() {
		dep.setRamal(EMPTYINT);
	}

	/*
	 * Está seção de testes tem o intuito de testar os métodos de listagem
	 */

	@Test
	public void teste_listar_departamentos() {
		assertNotNull(dep.listarDepartamentos());
	}

	@Test
	public void teste_toString() {
		assertNotNull(dep.toString());
	}
}
