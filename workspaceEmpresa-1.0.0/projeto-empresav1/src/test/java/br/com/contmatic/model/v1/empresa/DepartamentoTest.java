package br.com.contmatic.model.v1.empresa;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.startsWith;
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

import static br.com.contmatic.testes.util.TestesUtils.EMPTYSTR;
import static br.com.contmatic.testes.util.TestesUtils.NULLSTR;
import static br.com.contmatic.util.CamposTypes.DEPARTAMENTO_ID_TAMANHO_MAX;
import static br.com.contmatic.util.CamposTypes.DEPARTAMENTO_ID_TAMANHO_MIN;

import org.junit.Assert;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DepartamentoTest {

	private static final long ID_MINIMO = DEPARTAMENTO_ID_TAMANHO_MIN + 1;

	private static final long ID_MAXIMO = DEPARTAMENTO_ID_TAMANHO_MAX;

	private Long id;

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
		this.id = retornaIdAleatorio();
		this.nome = retornaNomeAleatorio();
		this.ramal = retornaRamalAleatorio();
		this.dep = new Departamento(id, nome, ramal);
	}

	public static long retornaIdAleatorio() {
		return ID_MINIMO + (long) (Math.random() * (ID_MAXIMO - ID_MINIMO));
	}

	private static String retornaRamalAleatorio() {
		return EX_RAMAL_DEPARTAMENTO_LISTA.get(new Random().nextInt(EX_NOMES_DEPARTAMENTO_LISTA.size()));
	}

	private static String retornaNomeAleatorio() {
		return EX_NOMES_DEPARTAMENTO_LISTA.get(new Random().nextInt(EX_NOMES_DEPARTAMENTO_LISTA.size()));
	}

	@After
	public void tearDown() throws Exception {
		this.id = null;
		this.nome = null;
		this.ramal = null;
		this.dep = null;
	}

	@Test(expected = Test.None.class)
	public void deve_aceitar_departamento_sem_ramal() {
		new Departamento(retornaIdAleatorio(), retornaNomeAleatorio());
	}

	@Test(expected = Test.None.class)
	public void deve_aceitar_id_valido() {
		dep.setId(500L);
	}

	@Test
	public void nao_deve_aceitar_id_nulo() {
		Exception nu = Assert.assertThrows("ID não devem ser passados como nulos", NullPointerException.class,
				() -> dep.setId(null));
		assertThat(nu.getMessage(), equalTo("O campo id da classe Departamento não pode ser nulo."));
	}

	@Test
	public void nao_deve_aceitar_id_com_tamanho_inapropriado() {
		Exception e = Assert.assertThrows("ID não devem ser passados como nulos", IllegalArgumentException.class,
				() -> dep.setId(99999L));
		assertThat(e.getMessage(), startsWith("O campo id da classe Departamento não pode ter esse tamanho."));
	}

	@Test(expected = Test.None.class)
	public void deve_aceitar_nome_departamento_valido() {
		dep.setNome(DepartamentoTest.retornaNomeAleatorio());
	}

	@Test
	public void nao_deve_aceitar_nome_departamento_nulo() {
		Exception nu = Assert.assertThrows("Nome não deve aceitar nulos", NullPointerException.class,
				() -> dep.setNome(NULLSTR));
		assertThat(nu.getMessage(), equalTo("O campo nome da classe Departamento não pode ser nulo."));
	}

	@Test
	public void nao_deve_aceitar_nome_departamento_com_tamanho_inapropriado() {
		Exception e = Assert.assertThrows("Nome não deve aceitar tamanhos inapropriados",
				IllegalArgumentException.class, () -> dep.setNome(EMPTYSTR));
		assertThat(e.getMessage(), startsWith("O campo nome da classe Departamento não pode ter esse tamanho."));
	}

	@Test
	public void nao_deve_aceitar_nomes_de_departamentos_com_valores_invalidos() {
		String[] nomesInvalidos = { "Recurso Humanos!", "Ti?", "________", "Financeiro & Comercial", "Estoque.",
				"_-Marketin!!-_" };
		for (String nome : nomesInvalidos) {
			Exception e = Assert.assertThrows("Nome não deve aceitar caracteres especiais",
					IllegalArgumentException.class, () -> dep.setNome(nome));
			assertThat(e.getMessage(),
					equalTo("O campo nome da classe Departamento não pode possuir caracteres especiais."));
		}
	}

	@Test(expected = Test.None.class)
	public void nao_deve_aceitar_ramal_com_numero_valido() {
		dep.setRamal(DepartamentoTest.retornaRamalAleatorio());
	}

	@Test
	public void nao_deve_aceitar_ramal_nulo() {
		Exception nu = Assert.assertThrows("Ramal não deve aceitar nulos", NullPointerException.class,
				() -> dep.setRamal(null));
		assertThat(nu.getMessage(), equalTo("O campo ramal da classe Departamento não pode ser nulo."));
	}

	@Test
	public void nao_deve_aceitar_ramal_com_tamanho_incorreto() {
		Exception e = Assert.assertThrows("Ramal não deve aceitar valore com tamanho fora do limite",
				IllegalArgumentException.class, () -> dep.setRamal("4448904578952745"));
		assertThat(e.getMessage(), startsWith("O campo ramal da classe Departamento não pode ter esse tamanho."));
	}

	@Test
	public void nao_deve_aceitar_ramal_com_valores_invalidos() {
		String[] ramalInvalidos = { "448A", "ABC", "________", "89ABC89", "77894O", "559@!$%" };
		for (String ramal : ramalInvalidos) {
			Exception e = Assert.assertThrows("Não devem ser aceitos nomes com caracteres especiais",
					IllegalArgumentException.class, () -> dep.setRamal(ramal));
			assertThat(e.getMessage(), equalTo("O campo ramal da classe Departamento só pode conter números."));
		}
	}

	@Test
	public void teste_equals_simetria() {
		Departamento a = new Departamento(89L, "Financeiro", "145");
		Departamento b = new Departamento(89L, "Financeiro", "145");
		assertTrue(a.equals(b) && b.equals(a));
	}

	@Test
	public void teste_equals_reflexividade() {
		Assert.assertEquals(dep, dep);
	}

	@Test
	public void teste_equals_transitividade() {
		Departamento a = new Departamento(125L, "Mecánica");
		Departamento b = new Departamento(125L, "Mecánica");
		Departamento c = new Departamento(125L, "Mecánica");
		assertTrue((a.equals(b) && b.equals(c)) && (a.equals(c)));
	}

	@Test
	public void teste_equals_objetos_nulos_devem_retornar_false() {
		Assert.assertNotEquals(dep, null);
	}

	@Test
	public void teste_equals_departamentos_com_ids_diferentes_nao_devem_ser_iguais() {
		Departamento departamento1 = new Departamento(5L, "Financeiro", "891");
		Departamento departamento2 = new Departamento(10L, "Legal", "5034");
		Assert.assertNotEquals(departamento1, departamento2);
	}

	@Test
	public void teste_equals_departamentos_com_ids_iguais_devem_ser_iguais() {
		Departamento departamento1 = new Departamento(51L, "TI");
		Departamento departamento2 = new Departamento(51L, "RH");
		Assert.assertEquals(departamento1, departamento2);
	}

	@Test
	public void teste_hashcode_consistencia() {
		Departamento a = new Departamento(892L, "Faxineiro", "4001");
		Departamento b = new Departamento(892L, "Faxineiro", "4001");
		Assert.assertEquals(a.hashCode(), b.hashCode());
	}

	@Test
	public void teste_toString() {
		Assert.assertNotNull("Os valores deveriam ser iguais", dep.toString());
	}
}
