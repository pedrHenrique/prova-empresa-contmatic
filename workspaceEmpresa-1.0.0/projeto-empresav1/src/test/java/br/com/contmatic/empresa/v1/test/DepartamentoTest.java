package br.com.contmatic.empresa.v1.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.contmatic.empresa.v1.model.Departamento;

import org.junit.Assert;
import util.TestesUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DepartamentoTest {

	private static final int NOME_TAMANHO_MAX = Departamento.getNomeTamanhoMax();

	private static final int NOME_TAMANHO_MIN = Departamento.getNomeTamanhoMin();

	private static final int RAMAL_TAMANHO_MAX = Departamento.getRamalTamanhoMax();

	private static final int RAMAL_TAMANHO_MIN = Departamento.getRamalTamanhoMin();

	private String nome;

	private String ramal;

	private Departamento dep;

	private static final List<String> EX_NOMES_DEPARTAMENTO_LISTA = Arrays.asList("Financeiro", "Expedição", "Estoque",
			"RH", "TI", "Contábil");

	private static final List<String> EX_RAMAL_DEPARTAMENTO_LISTA = Arrays.asList("126", "458", "228", "4456", "55894",
			"662489", "7778264", "886459126");

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println(" --- Início dos Testes da Classe Departamento --- \n");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("\n --- Fim dos Testes da Classe Departamento --- \n");
	}

	@Before
	public void setUp() throws Exception {
		this.nome = retornaNomeAleatorio();
		this.ramal = retornaRamalAleatorio();
		this.dep = new Departamento(nome, ramal);

	}

	private static String retornaRamalAleatorio() {
		return EX_RAMAL_DEPARTAMENTO_LISTA.get(new Random().nextInt(EX_NOMES_DEPARTAMENTO_LISTA.size()));
	}

	private static String retornaNomeAleatorio() {
		return EX_NOMES_DEPARTAMENTO_LISTA.get(new Random().nextInt(EX_NOMES_DEPARTAMENTO_LISTA.size()));
	}

	@After
	public void tearDown() throws Exception {
		this.nome = null;
		this.ramal = null;
		this.dep = null;
	}

	@Test(expected = Test.None.class)
	public void setNome_nao_deve_gerar_exception_recebendo_nome_valido() {
		dep.setNome(DepartamentoTest.retornaNomeAleatorio());
	}

	@Test
	public void setNome_deve_gerar_exception_recebendo_valor_nulo() {
		Exception nu = Assert.assertThrows("Nome não deve aceitar nulos", NullPointerException.class,
				() -> dep.setNome(TestesUtils.NULLSTR));

		assertThat(nu.getMessage(), equalTo("Valor não pode ser nulo"));
	}

	@Test
	public void setNome_deve_gerar_exception_recebendo_tamanho_inapropriado() {
		Exception e = Assert.assertThrows("Nome não deve aceitar tamanhos inapropriados",
				IllegalArgumentException.class, () -> dep.setNome(TestesUtils.EMPTYSTR));

		assertThat(e.getMessage(), equalTo("O tamanho para o nome do departamento não pode ser menor que "
				+ NOME_TAMANHO_MIN + " ou maior que " + NOME_TAMANHO_MAX));
	}

	@Test
	public void setNome_deve_gerar_exception_recebendo_valores_invalidos() {
		String[] nomesInvalidos = { "Recurso Humanos!", "Ti?", "________", "Financeiro & Comercial", "Estoque.",
				"_-Marketin!!-_" };

		for (String nome : nomesInvalidos) {
			Exception e = Assert.assertThrows("Nome não deve aceitar caracteres especiais",
					IllegalArgumentException.class, () -> dep.setNome(nome));

			assertThat(e.getMessage(),
					equalTo("O nome do departamento não pode possuir caracteres"));
		}
	}

	@Test(expected = Test.None.class)
	public void setRamal_nao_deve_gerar_exception_recebendo_numero_valido() {
		dep.setRamal(DepartamentoTest.retornaRamalAleatorio());
	}

	@Test
	public void setRamal_deve_gerar_exception_recebendo_valor_nulo() {
		Exception nu = Assert.assertThrows("Ramal não deve aceitar nulos", NullPointerException.class,
				() -> dep.setRamal(null));

		assertThat(nu.getMessage(), equalTo("Valor não pode ser nulo"));
	}

	@Test
	public void setRamal_deve_gerar_exception_recebendo_tamanho_incorreto() {
		Exception e = Assert.assertThrows("Ramal não deve aceitar valore com tamanho fora do limite",
				IllegalArgumentException.class, () -> dep.setRamal("4448904578952745"));

		assertThat(e.getMessage(), equalTo("O tamanho para o ramal do departamento não pode ser menor que "
				+ RAMAL_TAMANHO_MIN + " ou maior que " + RAMAL_TAMANHO_MAX));
	}

	@Test
	public void setRamal_deve_gerar_exception_recebendo_valores_invalidos() {
		String[] ramalInvalidos = { "448A", "ABC", "________", "89ABC89", "77894O", "559@!$%" };

		for (String ramal : ramalInvalidos) {			
			Exception e = Assert.assertThrows("Não devem ser aceitos nomes com caracteres especiais",
					IllegalArgumentException.class, () -> dep.setRamal(ramal));

			assertThat(e.getMessage(), equalTo("Ramal só pode conter números"));
		}
	}
	
	@Test
	public void teste_equals_simetria() {
		Departamento a = new Departamento("Financeiro", "145");
		Departamento b = new Departamento("Financeiro", "145");
		assertTrue(a.equals(b) && b.equals(a));
	}
	
	@Test
	public void teste_equals_reflexividade() {
		Assert.assertEquals(dep, dep);
	}
	
	@Test
	public void teste_equals_transitividade() {
		Departamento a = new Departamento("Financeiro", "145");
		Departamento b = new Departamento("Financeiro", "145");
		Departamento c = new Departamento("Financeiro", "145");
		assertThat(a.equals(b) && b.equals(c), equalTo(a.equals(c)));
	}
	
	@Test
	public void teste_equals_objetos_nulos_devem_retornar_false() {
		Assert.assertNotEquals(dep, null);
	}
	
	@Test
	public void teste_equals_departamentos_com_nomes_diferentes_nao_devem_ser_iguais() {
		Departamento departamento1 = new Departamento("Financeiro", "145");
		Departamento departamento2 = new Departamento("TI", "145");
		Assert.assertNotEquals(departamento1, departamento2);
	}
	
	@Test
	public void teste_equals_departamentos_com_ramais_diferentes_nao_devem_ser_iguais() {
		Departamento departamento1 = new Departamento("TI", "145");
		Departamento departamento2 = new Departamento("TI", "245");
		Assert.assertNotEquals(departamento1, departamento2);
	}
	
	@Test
	public void teste_hashcode_consistencia() {
		Departamento a = new Departamento("Financeiro", "145");
		Departamento b = new Departamento("Financeiro", "145");
		Assert.assertEquals(a.hashCode(), b.hashCode());
	}


	@Test
	public void teste_toString() {
		Assert.assertNotNull("Os valores deveriam ser iguais", dep.toString());
	}
}
