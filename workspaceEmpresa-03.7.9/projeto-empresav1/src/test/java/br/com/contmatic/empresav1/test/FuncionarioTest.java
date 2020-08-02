package br.com.contmatic.empresav1.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import br.com.contmatic.empresav1.model.Departamento;
import br.com.contmatic.empresav1.model.Funcionario;
import br.com.contmatic.empresav1.model.Pessoa;

public class FuncionarioTest {

	private static final Object NULLOBJ = null;
	private static final String NULLSTR = null;
	private static final String EMPTYSTR = "";
	private static final Long NULLID = (Long) null;
	private static final Long EMPTYID = (long) 0;
	private static final Double NULLINT = null;
	private static final Double EMPTYINT = 0.0;
	private static final int EMPTYINTEGER = 0;

	private static Funcionario funcionario;
	private Funcionario fun; // criado para testar os getters/setters.
	private static Departamento dep;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		funcionario = new Funcionario();

		dep = new Departamento(1, " DepTestes", 256);
		funcionario.cadastrarPessoa(1, "Ana", "56495985096", "03575090", "11941063792",
				"testeMatic@cont.com", 1,3500.00);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
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
		fun = new Funcionario(id, "Joana", "45495985096", "03575090", "11941063792",
				"testeMatic@cont.com", 1, 1500.00);
		assertThat("O Obj esperado era:", fun, equalTo(funcionario.solicitarPessoa(id)));
	}

	@Test
	public void teste_objeto_criado_por_metodo_com_parametros() {
	long id = 3;
	assertThat("O Obj esperado era:",fun.cadastrarPessoa(id, "Cleber", "71477403000", "69915890",
			"1194106792", "testeMatic@cont.com", 1, 1500.79), equalTo(funcionario.solicitarPessoa(id)));
	assertNotNull("O objeto não deveria estar nulo", Pessoa.getPessoaLista().contains(funcionario.solicitarPessoa(id)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void teste_pessoa_criada_ja_existente() {
		long id = 1;
		fun.cadastrarPessoa(id, "Rogerinho", "45664899804", "69915890","11941012412792", "junior@Junior.com", 1, 50.79);
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Ignore("Teste ignorado pois funcionalidade ainda não está presente") 
	public void teste_pessoa_criada_com_cpf_ja_existente() {
		long id = 4;
		fun.cadastrarPessoa(id, "Rogerinho", "45664899804", "69915890","11941012412792", "junior@Junior.com", 1, 50.79);
	}
	
	@Test(expected = NullPointerException.class)
	public void teste_pessoa_sendo_criada_nula() {
		fun.cadastrarPessoa(NULLID, NULLSTR, NULLSTR, NULLSTR,NULLSTR, NULLSTR, NULLID, NULLINT);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void teste_pessoa_sendo_criada_vazia() {
		fun.cadastrarPessoa(EMPTYID, EMPTYSTR, EMPTYSTR, EMPTYSTR, EMPTYSTR, EMPTYSTR, EMPTYID, EMPTYINT);
	}
	
	@Test
	public void teste_remocao_pessoa_existente() {
		long id = 250;
		assertThat("Os objetos deveriam ser iguais", new Funcionario(id, "Rogerinho", "45664899804", "69915890","11941012412792", "junior@Junior.com", 1, 50.79), equalTo(fun.removerPessoa(id)));

	}
	
	@Test(expected = IllegalArgumentException.class)
	public void teste_remocao_pessoa_nao_existente() {
		long id = 500;
		fun.removerPessoa(id);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void teste_solicita_pessoa_nao_existente() {
		long id = 500;
		fun.solicitarPessoa(id);
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
		fun.setIdPessoa(id);
		assertThat("Os valores deveriam ser iguais", id, equalTo(fun.getIdPessoa()));
	}

	@Test(expected = NullPointerException.class)
	public void teste_setId_valor_nulo() {
		fun.setIdPessoa(NULLID);
	}

	@Test(expected = IllegalArgumentException.class)
	public void teste_setId_valor_vazio() {
		fun.setIdPessoa(EMPTYID);
	}
	
	@Test
	public void teste_setNome_e_getNome_nome_correto() {
		String name = new String("Carlos Alberto");
		fun.setNome(name);
		assertThat("Os valores deveriam ser iguais", name, equalTo(fun.getNome()));
	}

	@Test(expected = NullPointerException.class)
	public void teste_setNome_valor_nulo() {
		fun.setNome(NULLSTR);
	}

	@Test(expected = IllegalArgumentException.class)
	public void teste_setNome_valor_vazio() {
		fun.setNome(EMPTYSTR);
	}
	
	@Test
	public void teste_setCpf_e_getCpf_nome_correto() {
		String cpf = new String("04517788040");
		fun.setCpf(cpf);
		assertThat("Os valores deveriam ser iguais", cpf, equalTo(fun.getCpf().replaceAll("\\D", "")));// "\\D" remove formatação
	}

	@Test(expected = NullPointerException.class)
	public void teste_setCpf_valor_nulo() {
		fun.setCpf(NULLSTR);
	}

	@Test(expected = IllegalArgumentException.class)
	public void teste_setCpf_valor_vazio() {
		fun.setCpf(EMPTYSTR);
	}
	
	@Test
	public void teste_seCep_e_getCep_nome_correto() {
		String cep = new String("03575090");
		fun.setCep(cep);
		assertThat("Os valores deveriam ser iguais", cep, equalTo(fun.getCep().replaceAll("\\D", ""))); // "\\D" remove formatação
	}

	@Test(expected = NullPointerException.class)
	public void teste_setCep_valor_nulo() {
		fun.setCep(NULLSTR);
	}

	@Test(expected = IllegalArgumentException.class)
	public void teste_setCep_valor_vazio() {
		fun.setCep(EMPTYSTR);
	}
	
	@Test
	public void teste_setTelefone_e_getTelefone_nome_correto() {
		String tel = new String("1145649304");
		fun.setTelefone(tel);
		assertThat("Os valores deveriam ser iguais", tel, equalTo(fun.getTelefone().replaceAll("\\D", ""))); // "\\D" remove formatação
	}

	@Test(expected = NullPointerException.class)
	public void teste_setTel_valor_nulo() {
		fun.setTelefone(NULLSTR);
	}

	@Test(expected = IllegalArgumentException.class)
	public void teste_setTel_valor_vazio() {
		fun.setTelefone(EMPTYSTR);
	}
	
	@Test
	public void teste_setEmail_e_getEmail_nome_correto() {
		String email = new String("andre.crespo@contmatic.com");
		fun.setEmail(email);
		assertThat("Os valores deveriam ser iguais", email, equalTo(fun.getEmail())); // "\\D" remove formatação
		
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void teste_setEmail_valor_nulo() {
		fun.setEmail(NULLSTR);
	}

	@Test(expected = IllegalArgumentException.class)
	public void teste_setEmail_valor_vazio() {
		fun.setEmail(EMPTYSTR);
	}
	
	@Test
	public void teste_setSalario_e_getSalario_nome_correto() {
		//Forma encontrada de não gerar markers pelo Sonar
		Double valorActual = 5000.50; 
		Double valorExpected;
		
		fun.setSalario(valorActual);
		valorExpected = fun.getSalario();
		assertThat("Os valores deveriam ser iguais", valorActual, equalTo(valorExpected));
	}

	@Test(expected = NullPointerException.class)
	public void teste_setSalario_valor_nulo() {
		fun.setSalario(NULLINT);
	}

	@Test(expected = IllegalArgumentException.class)
	public void teste_setSalario_valor_vazio() {
		fun.setSalario(EMPTYINT);
	}
	
	@Test
	public void teste_funcionario_busca_departamento_existente() {
		long id = 1;
		Departamento depart = new Departamento();
		assertThat("Os departamentos deveriam ser iguais", fun.buscaDepartamento(dep.solicitarDep(id)), equalTo(depart.solicitarDep(id)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void teste_funcionario_busca_departamento_inexistente() {
		long id = 193;
		fun.buscaDepartamento(dep.solicitarDep(id));
	}

	@Test(expected = NullPointerException.class)
	public void teste_buscaDepartamento_valor_nulo() {
		fun.buscaDepartamento(new Departamento(NULLID,NULLSTR,(int) NULLOBJ));
	}

	@Test(expected = IllegalArgumentException.class)
	public void teste_buscaDepartamento_valor_vazio() {
		fun.buscaDepartamento(new Departamento(EMPTYID,EMPTYSTR,EMPTYINTEGER));
	}
	
}
