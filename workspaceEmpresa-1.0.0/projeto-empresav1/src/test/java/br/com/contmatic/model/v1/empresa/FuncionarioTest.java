package br.com.contmatic.model.v1.empresa;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.startsWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.contmatic.model.v1.empresa.endereco.Endereco;

import static br.com.contmatic.testes.util.TestesUtils.EMPTYDOUBLE;
import static br.com.contmatic.testes.util.TestesUtils.EMPTYSTR;
import static br.com.contmatic.testes.util.TestesUtils.NULLSTR;
import static br.com.contmatic.testes.util.TestesUtils.retornaContato;
import static br.com.contmatic.testes.util.TestesUtils.retornaDepartamento;
import static br.com.contmatic.testes.util.TestesUtils.retornaEndereco;
import static br.com.contmatic.util.DataFormatter.PADRAO_DATA;
import static br.com.contmatic.util.validator.documentos.CpfValidator.geraCpfAleatorio;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FuncionarioTest {

	private String nome;

	private String cpf;

	private Endereco endereco;

	private Contato contato;

	private Date dtAdmissao;

	private double salario;

	private Funcionario fun;

	private Departamento departamento;

	private static final List<Double> SALARIOS_LISTA = Arrays.asList(800.00, 1500.00, 2000.00, 4500.00, 6000.00, 7.500);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println(" --- Início dos Testes da Classe Funcionario --- \n");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("\n --- Fim dos Testes da Classe Funcionario --- \n");
	}

	@Before
	public void setUp() throws Exception {
		this.nome = "Nome Teste Funcionario";
		this.cpf = geraCpfAleatorio();
		this.endereco = retornaEndereco();
		this.contato = retornaContato();
		this.salario = retornaSalario();
		this.dtAdmissao = new Date();
		this.departamento = retornaDepartamento();
		fun = new Funcionario(nome, cpf, endereco, contato, dtAdmissao, salario, departamento);
	}

	@After
	public void tearDown() throws Exception {
		this.nome = null;
		this.cpf = null;
		this.endereco = null;
		this.contato = null;
		this.dtAdmissao = null;
		this.salario = 0.0;
		this.departamento = null;
		this.fun = null;
	}

	private static String retornaDataTexto() {
		return "01/01/1970";
	}

	private static Double retornaSalario() {
		return SALARIOS_LISTA.get(new Random().nextInt(SALARIOS_LISTA.size()));
	}

	@Test(expected = Test.None.class)
	public void deve_permitir_instanciar_funcionario_valido() {
		new Funcionario("Roberto Carlos", "84278571283", retornaEndereco(), retornaContato(), "11-05-2015",
				retornaSalario(), retornaDepartamento());
	}

	@Test(expected = Test.None.class)
	public void deve_aceitar_nome_valido() {
		fun.setNome("Carlos Alberto");
	}

	@Test
	public void nao_deve_aceitar_nome_nulo() {
		Exception nu = assertThrows("Nome não deve aceitar nulos", IllegalArgumentException.class,
				() -> fun.setNome(NULLSTR));
		assertThat(nu.getMessage(), equalTo("O campo nome da classe Funcionario não pode ser nulo."));
	}

	@Test
	public void nao_deve_aceitar_nome_com_tamanho_inapropriado() {
		Exception e = assertThrows("Nome não deve aceitar tamanhos inapropriados",
				IllegalArgumentException.class, () -> fun.setNome(EMPTYSTR));
		assertThat(e.getMessage(), startsWith("O campo nome da classe Funcionario não pode ter esse tamanho."));
	}

	@Test
	public void nao_deve_aceitar_nome_com_numeros() {
		Exception e = assertThrows("Nome não deve aceitar caracteres diferentes de letras",
				IllegalArgumentException.class, () -> fun.setNome("JoaoCleber123"));
		assertThat(e.getMessage(), equalTo("O campo nome da classe Funcionario não pode possuir dígitos."));
	}

	@Test
	public void nao_deve_aceitar_nome_com_simbolos() {
		Exception e = assertThrows("Nome não deve aceitar caracteres diferentes de letras",
				IllegalArgumentException.class, () -> fun.setNome("xX_-Joao_Cleber-_Xx"));
		assertThat(e.getMessage(),
				equalTo("O campo nome da classe Funcionario não pode possuir caracteres especiais."));
	}

	@Test(expected = Test.None.class)
	public void deve_aceitar_cpf_valido() {
		fun.setCpf("04517788040");
	}

	@Test
	public void nao_deve_aceitar_cpf_nulo() {
		Exception nu = assertThrows("CPF não deve aceitar nulos", IllegalArgumentException.class,
				() -> fun.setCpf(NULLSTR));
		assertThat(nu.getMessage(), equalTo("O campo cpf da classe Funcionario não pode ser nulo."));

	}

	@Test
	public void nao_deve_aceitar_cpf_com_tamanho_invalido() {
		Exception e = assertThrows("CPF com tamanho incorreto deve gerar exception",
				IllegalArgumentException.class, () -> fun.setCpf("12345"));
		assertThat(e.getMessage(), startsWith("O campo CPF da classe Funcionario foi informado com o tamanho errado."));
	}

	@Test
	public void nao_deve_aceitar_cpf_formato_invalido() {
		Exception e = assertThrows("CPF com formato incorreto deve gerar exception",
				IllegalArgumentException.class, () -> fun.setCpf("xxxxxxxxxxx"));
		assertThat(e.getMessage(), startsWith("O campo CPF da classe Funcionario foi informado com o formato errado."));
	}

	@Test
	public void nao_deve_aceitar_um_cpf_invalido() {
		Exception e = assertThrows("CPF invalido deve gerar exception", IllegalArgumentException.class,
				() -> fun.setCpf("00011122233"));
		assertThat(e.getMessage(),
				equalTo("O CPF que você inseriu não é válido. Por favor, insira o CPF sem nenhuma formatacao"));
	}

	@Test(expected = Test.None.class)
	public void deve_aceitar_contato_valido() {
		fun.setContato(retornaContato());
	}

	@Test
	public void nao_deve_aceitar_contato_nulo() {
		Exception nu = assertThrows("Contatos passados nulos devem gerar exception", IllegalArgumentException.class,
				() -> fun.setContato(null));
		assertThat(nu.getMessage(), equalTo("O campo contato da classe Funcionario não pode ser nulo."));
	}

	@Test(expected = Test.None.class)
	public void deve_aceitar_dataAdmissao_valida() {
		Date exemploFuncionarioAdmitidoHoje = new Date();
		fun.setDtAdimissao(exemploFuncionarioAdmitidoHoje);
	}

	@Test
	public void nao_deve_aceitar_data_no_nula() throws ParseException {
		Exception e = assertThrows("Datas nulas não devem ser aceitas", IllegalArgumentException.class,
				() -> fun.setDtAdimissao(null));
		assertThat(e.getMessage(), equalTo("O campo dtAdimissao da classe Funcionario não pode ser nulo."));
	}

	@Test
	public void nao_deve_aceitar_data_no_futuro() throws ParseException {
		Date exemplo = new SimpleDateFormat(PADRAO_DATA).parse("15/02/2095");
		Exception e = assertThrows("Datas no futuro não devem ser aceitas", IllegalArgumentException.class,
				() -> fun.setDtAdimissao(exemplo));
		assertThat(e.getMessage(), equalTo("Datas no futuro não podem ser aceitas."));
	}

	@Test
	public void nao_deve_aceitar_data_muito_antigas() throws ParseException {
		Date exemplo = new SimpleDateFormat(PADRAO_DATA).parse("25/12/1899");
		Exception e = assertThrows("Datas com ano inferior a 1900 devem degar exception", IllegalArgumentException.class,
				() -> fun.setDtAdimissao(exemplo));
		assertThat(e.getMessage(), startsWith("A data informada é muito antiga para ser aceita."));
	}

	@Test(expected = Test.None.class)
	public void deve_aceitar_endereco_valido() {
		fun.setEndereco(retornaEndereco());
	}

	@Test
	public void nao_deve_aceitar_endereco_nulo() {
		Exception nu = assertThrows("Enderecos passados nulos devem gerar exception", IllegalArgumentException.class,
				() -> fun.setEndereco(null));
		assertThat(nu.getMessage(), equalTo("O campo endereco da classe Funcionario não pode ser nulo."));
	}

	@Test(expected = Test.None.class)
	public void deve_aceitar_salario_valido() {
		fun.setSalario(retornaSalario());
	}

	@Test
	public void nao_deve_aceitar_salario_invalido() {
		Exception e = assertThrows("Salario invalidos devem gerar exception", IllegalArgumentException.class,
				() -> fun.setSalario(EMPTYDOUBLE));
		assertThat(e.getMessage(), equalTo("Este salário não pode ser aceito..."));
	}

	@Test(expected = Test.None.class)
	public void deve_aceitar_departamento_valido() {
		fun.setDepartamento(retornaDepartamento());
	}

	@Test
	public void nao_deve_aceitar_departamento_nulo() {
		Exception nu = assertThrows("Departamentos passados nulos devem gerar exception",
				IllegalArgumentException.class, () -> fun.setDepartamento(null));
		assertThat(nu.getMessage(), equalTo("O campo departamento da classe Funcionario não pode ser nulo."));
	}

	@Test
	public void teste_toString() {
		assertNotNull(fun.toString());
	}

	@Test
	public void teste_equals_simetria() {
		Funcionario a = new Funcionario("Ana Maria Clara", "49490297038", retornaEndereco(), retornaContato(), retornaDataTexto(), 500.00,
				retornaDepartamento());
		Funcionario b = new Funcionario("Ana Maria Clara", "49490297038", retornaEndereco(), retornaContato(), retornaDataTexto(), 750.00,
				retornaDepartamento());
		assertTrue(a.equals(b) && b.equals(a));
	}

	@Test
	public void teste_equals_reflexividade() {
		assertEquals(fun, fun);
	}

	@Test
	public void teste_equals_transitividade() {
		Funcionario a = new Funcionario("Ana Carolline", "49490297038", retornaEndereco(), retornaContato(), retornaDataTexto(), 500.00,
				retornaDepartamento());
		Funcionario b = new Funcionario("Ana Carolline", "49490297038", retornaEndereco(), retornaContato(), retornaDataTexto(), 750.00,
				retornaDepartamento());
		Funcionario c = new Funcionario("Ana Carolline", "49490297038", retornaEndereco(), retornaContato(), retornaDataTexto(), 1000.00,
				retornaDepartamento());
		assertTrue((a.equals(b) && b.equals(c)) && (a.equals(c)));
	}

	@Test
	public void teste_equals_objetos_nulos_devem_retornar_false() {
		assertNotEquals(fun, null);
	}

	@Test
	public void teste_equals_funcionarios_com_cpfs_diferentes_nao_devem_ser_iguais() {
		Funcionario funcionario1 = new Funcionario("Ana Vitória Leonardi", "49490297038", retornaEndereco(),
				retornaContato(), retornaDataTexto(), 500.00, retornaDepartamento());
		Funcionario funcionario2 = new Funcionario("Ana Vitória Leonardi", "66348746038", retornaEndereco(),
				retornaContato(), retornaDataTexto(), 500.00, retornaDepartamento());
		assertNotEquals(funcionario1, funcionario2);
	}

	@Test
	public void teste_hashcode_consistencia() {
		Funcionario a = new Funcionario("Ana Maria Clara", "49490297038", retornaEndereco(), retornaContato(), retornaDataTexto(), 500.00,
				retornaDepartamento());
		Funcionario b = new Funcionario("Ana Maria Clara", "49490297038", retornaEndereco(), retornaContato(), retornaDataTexto(), 750.00,
				retornaDepartamento());
		assertEquals(a.hashCode(), b.hashCode());
	}
}
