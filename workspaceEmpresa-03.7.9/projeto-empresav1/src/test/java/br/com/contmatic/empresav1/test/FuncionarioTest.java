package br.com.contmatic.empresav1.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.contmatic.empresav1.model.Departamento;
import br.com.contmatic.empresav1.model.Funcionario;
import br.com.contmatic.empresav1.model.Pessoa;

public class FuncionarioTest {

	private static final String NULLSTR = null;
	private static final String EMPTYSTR = "";
	private static final Long NULLID = (Long) null;
	private static final Long EMPTYID = (long) 0;
	private static final Double NULLINT = null;
	private static final Double EMPTYINT = 0.0;
	
	private static final Object NULLINTEGER = null;
	private static final int EMPTYINTEGER = 0;

	private static Funcionario funcionario;
	private static Departamento dep;
	private Funcionario fun;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Inicio Testes");
		funcionario = new Funcionario();

		dep = new Departamento(1, " DepTestes", 256);
		funcionario.cadastrarPessoa(1, "Ana", "56495985096", "03575090", "11941063792", "testeMatic@cont.com", 1,
				3500.00);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("Fim Testes");
		Funcionario.getPessoaLista().clear();
		Departamento.getDepartamentoLista().clear();
		funcionario = null;
		dep = null;
	}

	@Before
	public void setUp() throws Exception {
		this.fun = new Funcionario();
	}

	@After
	public void tearDown() throws Exception {
		this.fun = null;
	}

	/*
	 * Está seção de testes tem o intuito de testar os métodos de principais da
	 * classe
	 */

	@Test
	public void teste_criando_objeto_construtor() {
		long id = 2;
		Funcionario funTest = new Funcionario(id, "Joana", "45495985096", "03575090", "11941063792",
				"testeMatic@cont.com", 1, 1500.00);
		assertEquals(funTest, fun.solicitarPessoa(id));
	}

	@Test
	public void teste_objeto_criado_por_metodo_com_parametros() {
	long id = 3;
	assertEquals("O Obj esperado era:",fun.cadastrarPessoa(id, "Cleber", "71477403000", "69915890",
			"1194106792", "testeMatic@cont.com", 1, 1500.79), funcionario.solicitarPessoa(id));
	assertNotNull("O objeto não deveria estar nulo", Pessoa.getPessoaLista().contains(funcionario.solicitarPessoa(id)));
	}
	
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_pessoa_criada_ja_existente() {
		long id = 1;
		fun.cadastrarPessoa(id, "Rogerinho", "45664899804", "69915890","11941012412792", "junior@Junior.com", 1, 50.79);
	}
	
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_pessoa_criada_com_cpf_ja_existente() {
		long id = 4;
		fun.cadastrarPessoa(id, "Rogerinho", "45664899804", "69915890","11941012412792", "junior@Junior.com", 1, 50.79);
	}
	
	@Test(expected = java.lang.NullPointerException.class)
	public void teste_pessoa_sendo_criada_nula() {
		fun.cadastrarPessoa(NULLID, NULLSTR, NULLSTR, NULLSTR,NULLSTR, NULLSTR, NULLID, NULLINT);
	}
	
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_pessoa_sendo_criada_vazia() {
		fun.cadastrarPessoa(EMPTYID, EMPTYSTR, EMPTYSTR, EMPTYSTR, EMPTYSTR, EMPTYSTR, EMPTYID, EMPTYINT);
	}
	
	@Test
	public void teste_remocao_pessoa_existente() {
		long id = 250;
		assertEquals("Os objetos deveriam ser iguais", new Funcionario(id, "Rogerinho", "45664899804", "69915890","11941012412792", "junior@Junior.com", 1, 50.79)
				,fun.removerPessoa(id));

	}
	
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_remocao_pessoa_nao_existente() {
		long id = 500;
		fun.removerPessoa(id);
	}
	
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_solicita_pessoa_nao_existente() {
		long id = 500;
		funcionario.solicitarPessoa(id);
	}
	
	/*
	 * Está seção de testes tem o intuito de testar métodos de listagem da classe
	 */

	@Test
	public void teste_listar_pessoas() {
		assertNotNull("Esperava receber uma lista: ", funcionario.listarPessoa());
	}
	
	@Test
	public void teste_toString() {
		assertNotNull("Os valores deveriam ser iguais", funcionario.toString());
	}
	
	/*
	 * Está seção de testes tem o intuito de testar os getters/setters da classe
	 */
	
	@Test
	public void teste_setId_e_getId_nome_correto() {
		long id = 45;
		funcionario.setIdPessoa(id);
		assertEquals("Os valores deveriam ser iguais", id, funcionario.getIdPessoa());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void teste_setId_valor_nulo() {
		funcionario.setIdPessoa(NULLID);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_setId_valor_vazio() {
		funcionario.setIdPessoa(EMPTYID);
	}
	
	@Test
	public void teste_setNome_e_getNome_nome_correto() {
		String name = new String("Carlos Alberto");
		funcionario.setNome(name);
		assertEquals("Os valores deveriam ser iguais", name, funcionario.getNome());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void teste_setNome_valor_nulo() {
		funcionario.setNome(NULLSTR);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_setNome_valor_vazio() {
		funcionario.setNome(EMPTYSTR);
	}
	
	@Test
	public void teste_setCpf_e_getCpf_nome_correto() {
		String cpf = new String("04517788040");
		funcionario.setCpf(cpf);
		assertEquals("Os valores deveriam ser iguais", cpf, funcionario.getCpf().replaceAll("\\D", "")); // "\\D" remove formatação
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void teste_setCpf_valor_nulo() {
		funcionario.setCpf(NULLSTR);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_setCpf_valor_vazio() {
		funcionario.setCpf(EMPTYSTR);
	}
	
	@Test
	public void teste_seCep_e_getCep_nome_correto() {
		String cep = new String("03575090");
		funcionario.setCep(cep);
		assertEquals("Os valores deveriam ser iguais", cep, funcionario.getCep().replaceAll("\\D", "")); // "\\D" remove formatação
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void teste_setCep_valor_nulo() {
		funcionario.setCep(NULLSTR);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_setCep_valor_vazio() {
		funcionario.setCep(EMPTYSTR);
	}
	
	@Test
	public void teste_setTelefone_e_getTelefone_nome_correto() {
		String tel = new String("1145649304");
		funcionario.setTelefone(tel);
		assertEquals("Os valores deveriam ser iguais", tel, funcionario.getTelefone().replaceAll("\\D", "")); // "\\D" remove formatação
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void teste_setTel_valor_nulo() {
		funcionario.setTelefone(NULLSTR);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_setTel_valor_vazio() {
		funcionario.setTelefone(EMPTYSTR);
	}
	
	@Test
	public void teste_setEmail_e_getEmail_nome_correto() {
		String email = new String("andre.crespo@contmatic.com");
		funcionario.setEmail(email);
		assertEquals("Os valores deveriam ser iguais", email, funcionario.getEmail());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void teste_setEmail_valor_nulo() {
		funcionario.setEmail(NULLSTR);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_setEmail_valor_vazio() {
		funcionario.setEmail(EMPTYSTR);
	}
	
	@Test
	public void teste_setSalario_e_getSalario_nome_correto() {
		Double valorActual = 5000.50;
		Double valorExpected;
		
		funcionario.setSalario(valorActual);
		valorExpected = funcionario.getSalario();
		assertEquals("Os valores deveriam ser iguais", valorActual, valorExpected);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void teste_setSalario_valor_nulo() {
		funcionario.setSalario(NULLINT);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_setSalario_valor_vazio() {
		funcionario.setSalario(EMPTYINT);
	}
	
	@Test
	public void teste_funcionario_busca_departamento_existente() {
		long id = 1;
		Departamento depart = new Departamento();
		assertEquals("Os departamentos deveriam ser iguais", funcionario.buscaDepartamento(dep.solicitarDep(id)), depart.solicitarDep(id));
	}
	
	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_funcionario_busca_departamento_inexistente() {
		long id = 193;
		funcionario.buscaDepartamento(dep.solicitarDep(id));
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void teste_buscaDepartamento_valor_nulo() {
		funcionario.buscaDepartamento(new Departamento(NULLID,NULLSTR,(int) NULLINTEGER));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_buscaDepartamento_valor_vazio() {
		funcionario.buscaDepartamento(new Departamento(EMPTYID,EMPTYSTR,EMPTYINTEGER));
	}
	
}
