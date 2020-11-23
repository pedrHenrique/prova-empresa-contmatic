package br.com.contmatic.empresa.v1.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.startsWith;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.contmatic.empresa.v1.model.Contato;
import br.com.contmatic.empresa.v1.model.Departamento;
import br.com.contmatic.empresa.v1.model.Endereco;
import br.com.contmatic.empresa.v1.model.Funcionario;
import br.com.contmatic.empresa.v1.model.contato.Telefone;
import util.TestesUtils;

import static br.com.contmatic.empresa.v1.util.Documentos.cpfAleatorio;;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FuncionarioTest {

	private static final int NOME_TAMANHO_MAX = Funcionario.getNomeTamanhoMax();

	private static final int NOME_TAMANHO_MIN = Funcionario.getNomeTamanhoMin();
	
	private String nome;

	private String cpf;

	private Endereco endereco;

	private Contato contato;

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
		this.cpf = cpfAleatorio();
		this.endereco = retornaEndereco();
		this.contato = retornaContato();
		this.salario = retornaSalario();
		this.departamento = retornaDepartamento();
		fun = new Funcionario(nome, cpf, endereco, contato, salario, departamento);
	}

	@After
	public void tearDown() throws Exception {
		this.nome = null;
		this.cpf = null;
		this.endereco = null;
		this.contato = null;
		this.salario = 0.0;
		this.departamento = null;
		this.fun = null;
	}

	private static Departamento retornaDepartamento() {
		return new Departamento("DepartamentoTeste", "14564");
	}

	private static Double retornaSalario() {
		return SALARIOS_LISTA.get(new Random().nextInt(SALARIOS_LISTA.size()));
	}
	
	private static Endereco retornaEndereco() {
		return Endereco.cadastraEndereco("03575090", String.valueOf(new Random().nextInt(100)));
	}

	private static Contato retornaContato() {
		return new Contato("testematic@contmatic.com", new Telefone("1125068922"));
	}
	
	@Test(expected = Test.None.class)
	public void setNome_nao_deve_gera_exception_recebendo_valor_valido() {
		fun.setNome("Carlos Alberto");
	}

	@Test
	public void setNome_deve_gerar_exception_recebendo_valor_nulo() {
		Exception nu = Assert.assertThrows("Nome não deve aceitar nulos", NullPointerException.class,
				() -> fun.setNome(TestesUtils.NULLSTR));

		assertThat(nu.getMessage(), equalTo("Valor não pode ser nulo"));
	}

	@Test
	public void setNome_deve_gerar_exception_recebendo_tamanho_inapropriado() {
		Exception e = Assert.assertThrows("Nome não deve aceitar tamanhos inapropriados",
				IllegalArgumentException.class, () -> fun.setNome(TestesUtils.EMPTYSTR));

		assertThat(e.getMessage(), equalTo("Nome do funcionario deve ter tamanho entre " + NOME_TAMANHO_MIN + " a " + NOME_TAMANHO_MAX));
	}

	@Test
	public void setNome_deve_gerar_exception_recebendo_nome_com_numeros() {
		Exception e = Assert.assertThrows("Nome não deve aceitar caracteres diferentes de letras",
				IllegalArgumentException.class, () -> fun.setNome("JoaoCleber123"));

		assertThat(e.getMessage(), equalTo("O nome do funcionario não pode possuir caracteres especiais ou números"));
	}

	@Test
	public void setNome_deve_gerar_exception_recebendo_nome_com_simbolos() {
		Exception e = Assert.assertThrows("Nome não deve aceitar caracteres diferentes de letras",
				IllegalArgumentException.class, () -> fun.setNome("xX_-Joao_Cleber-_Xx"));

		assertThat(e.getMessage(), equalTo("O nome do funcionario não pode possuir caracteres especiais ou números"));
	}
	
	@Test(expected = Test.None.class)
	public void setCpf_nao_deve_gerar_exception_recebendo_cpf_valido() {
		fun.setCpf("04517788040");		
	}

	@Test
	public void setCpf_deve_gerar_exception_recebendo_valor_nulo() {
		Exception nu = Assert.assertThrows("CPF não deve aceitar nulos", NullPointerException.class,
				() -> fun.setCpf(TestesUtils.NULLSTR));

		assertThat(nu.getMessage(), equalTo("Valor não pode ser nulo"));
		
	}

	@Test
	public void setCpf_deve_gerar_exception_recebendo_um_cpf_invalido() {
		Exception e = Assert.assertThrows("CPF invalidos devem gerar exception", IllegalArgumentException.class,
				() -> fun.setCpf("00011122233"));

		assertThat(e.getMessage(), startsWith("O CPF informado não pôde ser aceito."));
	}
	
	@Test(expected = Test.None.class)
	public void setContato_nao_deve_gerar_exception_recebendo_contato_valido() {		
		fun.setContato(FuncionarioTest.retornaContato());		
	}

	@Test
	public void setContato_deve_gerar_exception_recebendo_contato_nulo() {
		Exception nu = Assert.assertThrows("Contatos passados nulos devem gerar exception", NullPointerException.class,
				() -> fun.setContato(null));

		assertThat(nu.getMessage(), equalTo("Contato não pode ser nulo"));
	}
	
	@Test(expected = Test.None.class)
	public void setEndereco_nao_deve_gerar_exception_recebendo_endereco_valido() {
		fun.setEndereco(FuncionarioTest.retornaEndereco());
	}

	@Test
	public void setEndereco_deve_gerar_exception_recebendo_endereco_nulo() {
		Exception nu = Assert.assertThrows("Enderecos passados nulos devem gerar exception", NullPointerException.class,
				() -> fun.setEndereco(null));

		assertThat(nu.getMessage(), equalTo("Endereco não pode ser nulo"));
	}

	
	@Test(expected = Test.None.class)
	public void setSalario_nao_deve_gerar_exception_recebendo_valor_valido() {
		fun.setSalario(FuncionarioTest.retornaSalario());
	}

	@Test
	public void setSalario_deve_gerar_exception_recebendo_valor_invalido() {
		Exception e = Assert.assertThrows("Salario invalidos devem gerar exception", IllegalArgumentException.class,
				() -> fun.setSalario(TestesUtils.EMPTYDOUBLE));

		assertThat(e.getMessage(), equalTo("Este salário não pode ser aceito..."));
	}
	
	@Test(expected = Test.None.class)
	public void setDepartamento_nao_deve_gerar_exception_recebendo_endereco_valido() {
		fun.setDepartamento(FuncionarioTest.retornaDepartamento());
	}

	@Test
	public void setDepartamento_deve_gerar_exception_recebendo_departamento_nulo() {
		Exception nu = Assert.assertThrows("Departamentos passados nulos devem gerar exception", NullPointerException.class,
				() -> fun.setDepartamento(null));

		assertThat(nu.getMessage(), equalTo("Departamento não pode ser nulo"));
	}

	@Test
	public void teste_toString() {
		assertNotNull(fun.toString());
	}

	@Test
	public void teste_equals_simetria() {
		Funcionario a = new Funcionario("Ana Maria Clara", "49490297038", FuncionarioTest.retornaEndereco(), FuncionarioTest.retornaContato(), 500.00, FuncionarioTest.retornaDepartamento());
		Funcionario b = new Funcionario("Ana Maria Clara", "49490297038", FuncionarioTest.retornaEndereco(), FuncionarioTest.retornaContato(), 750.00, FuncionarioTest.retornaDepartamento());
		assertTrue(a.equals(b) && b.equals(a));
	}
	
	@Test
	public void teste_equals_reflexividade() {
		Assert.assertEquals(fun, fun);
	}
	
	@Test
	public void teste_equals_transitividade() {
		Funcionario a = new Funcionario("Ana Carolline", "49490297038", FuncionarioTest.retornaEndereco(), FuncionarioTest.retornaContato(), 500.00, FuncionarioTest.retornaDepartamento());
		Funcionario b = new Funcionario("Ana Carolline", "49490297038", FuncionarioTest.retornaEndereco(), FuncionarioTest.retornaContato(), 750.00, FuncionarioTest.retornaDepartamento());
		Funcionario c = new Funcionario("Ana Carolline", "49490297038", FuncionarioTest.retornaEndereco(), FuncionarioTest.retornaContato(), 1000.00, FuncionarioTest.retornaDepartamento());
		assertThat(a.equals(b) && b.equals(c), equalTo(a.equals(c)));
	}
	
	@Test
	public void teste_equals_objetos_nulos_devem_retornar_false() {
		Assert.assertNotEquals(fun, null);
	}
	
	@Test
	public void teste_equals_funcionarios_com_cpfs_diferentes_nao_devem_ser_iguais() {
		Funcionario funcionario1 = new Funcionario("Ana Vitória Leonardi", "49490297038", FuncionarioTest.retornaEndereco(), FuncionarioTest.retornaContato(), 500.00, FuncionarioTest.retornaDepartamento());
		Funcionario funcionario2 = new Funcionario("Ana Vitória Leonardi", "66348746038", FuncionarioTest.retornaEndereco(), FuncionarioTest.retornaContato(), 500.00, FuncionarioTest.retornaDepartamento());
		Assert.assertNotEquals(funcionario1, funcionario2);
	}
	
	@Test
	public void teste_hashcode_consistencia() {
		Funcionario a = new Funcionario("Ana Maria Clara", "49490297038", FuncionarioTest.retornaEndereco(), FuncionarioTest.retornaContato(), 500.00, FuncionarioTest.retornaDepartamento());
		Funcionario b = new Funcionario("Ana Maria Clara", "49490297038", FuncionarioTest.retornaEndereco(), FuncionarioTest.retornaContato(), 750.00, FuncionarioTest.retornaDepartamento());
		Assert.assertEquals(a.hashCode(), b.hashCode());
	}
}
