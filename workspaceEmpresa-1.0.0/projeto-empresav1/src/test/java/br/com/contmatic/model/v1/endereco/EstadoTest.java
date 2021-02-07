package br.com.contmatic.model.v1.endereco;

import static br.com.contmatic.model.v1.empresa.endereco.EstadoType.MG;
import static br.com.contmatic.model.v1.empresa.endereco.EstadoType.PI;
import static br.com.contmatic.model.v1.empresa.endereco.EstadoType.PR;
import static br.com.contmatic.model.v1.empresa.endereco.EstadoType.SC;
import static br.com.contmatic.model.v1.empresa.endereco.PaisType.BRASIL;
import static br.com.contmatic.model.v1.empresa.endereco.PaisType.ESTADOS_UNIDOS;
import static br.com.contmatic.model.v1.empresa.endereco.PaisType.ITALIA;
import static br.com.contmatic.testes.util.TestesUtils.retornaEstadoAleatorio;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.contmatic.model.v1.empresa.endereco.Estado;
import br.com.contmatic.model.v1.empresa.endereco.EstadoType;
import br.com.contmatic.model.v1.empresa.endereco.PaisType;

public class EstadoTest {

	private String nome;

	private String uf;

	private PaisType pais;

	private EstadoType tipo;

	private Estado est;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println(" --- Início dos Testes da Classe Estado --- \n");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("\n --- Fim dos Testes da Classe Estado --- \n");
	}

	@Before
	public void setUp() throws Exception {
		this.tipo = retornaEstadoAleatorio();
		this.nome = tipo.getNome();
		this.uf = tipo.getUf();
		this.pais = tipo.getPais();
		est = new Estado(nome, uf, pais);
	}

	@After
	public void tearDown() throws Exception {
		this.tipo = null;
		this.nome = null;
		this.uf = null;
		this.pais = null;
	}

	@Test(expected = Test.None.class)
	public void deve_aceitar_tipo_estado_sendo_passado_valido() {
		est = new Estado(retornaEstadoAleatorio());
	}

	@Test(expected = Test.None.class)
	public void deve_aceitar_estado_sendo_informado_corretamente() {
		est = new Estado("Nebraska", "NE", ESTADOS_UNIDOS);
	}

	@Test
	public void nao_deve_aceitar_estado_brasileiro_informado_invalidamente() {
		Exception e = assertThrows("Estados brasileiros devem ser validados", IllegalArgumentException.class,
				() -> est = new Estado("São Paulo", "RJ", BRASIL));
		assertThat(e.getMessage(),
				startsWith("Os valores informados para o estado não condizem com um estado Brasileiro válido."));
	}

	@Test
	public void nao_deve_aceitar_tipo_estado_sendo_passado_nulo() {
		Exception e = assertThrows("Estados nulos não devem ser aceitos", IllegalArgumentException.class,
				() -> est = new Estado(null));
		assertThat(e.getMessage(), equalTo("O campo uf da classe EstadoType não pode ser nulo."));
	}
	
	@Test
	public void nao_deve_aceitar_nomeEstado_passado_nulo() {
		Exception e = assertThrows("Nomes de estado nulos não devem ser aceitos", IllegalArgumentException.class,
				() -> est = new Estado(null, "SP", BRASIL));
		assertThat(e.getMessage(), equalTo("O campo nome da classe Estado não pode ser nulo."));
	}
	
	@Test
	public void nao_deve_aceitar_nomeEstado_passado_vazio() {
		Exception e = assertThrows("Nomes de estado vazios não devem ser aceitos", IllegalArgumentException.class,
				() -> est = new Estado("                                          ", "SP", BRASIL));
		assertThat(e.getMessage(), startsWith("O campo nome da classe Estado foi informado em branco"));
	}
	
	@Test
	public void nao_deve_aceitar_nomeEstado_passado_com_tamanho_inapropriado() {
		Exception e = assertThrows("Nomes de estado enormes não devem ser aceitos", IllegalArgumentException.class,
				() -> est = new Estado("ExemploDeUmNomeParaEstadoQueNaoDeveriaSerAceitoDevidoOSeuTamanho", "SP", BRASIL));
		assertThat(e.getMessage(), startsWith("O campo nome da classe Estado não pode ter esse tamanho."));
	}

	@Test
	public void nao_deve_aceitar_nomeEstado_passado_com_numeros() {
		Exception e = assertThrows("Nomes de estado com números não devem ser aceitos", IllegalArgumentException.class,
				() -> est = new Estado("P4R4", "SP", BRASIL));
		assertThat(e.getMessage(), startsWith("O campo nome da classe Estado não pode possuir dígitos."));
	}

	@Test
	public void nao_deve_aceitar_uf_passado_nulo() {
		Exception e = assertThrows("UF nulos não devem ser aceitos", IllegalArgumentException.class,
				() -> est = new Estado("Rio de Janeiro", null, BRASIL));
		assertThat(e.getMessage(), equalTo("O campo uf da classe Estado não pode ser nulo."));
	}
	
	@Test
	public void nao_deve_aceitar_uf_passado_vazio() {
		Exception e = assertThrows("UF vazias não devem ser aceitas", IllegalArgumentException.class,
				() -> est = new Estado("Ohio", "ExemploDeUmaUFQueNaoDeveriaSerAceitoDevidoOSeuTamanho", ESTADOS_UNIDOS));
		assertThat(e.getMessage(), startsWith("O campo uf da classe Estado não pode ter esse tamanho."));
	}

	@Test
	public void nao_deve_aceitar_uf_passado_com_numeros() {
		Exception e = assertThrows("UF com números não devem ser aceitas", IllegalArgumentException.class,
				() -> est = new Estado("ROMA", "22", ITALIA));
		assertThat(e.getMessage(), startsWith("O campo uf da classe Estado não pode possuir dígitos."));
	}

	@Test
	public void nao_deve_aceitar_pais_passado_nulo() {
		Exception e = assertThrows("Pais informado nulo deve gerar exception", IllegalArgumentException.class,
				() -> est = new Estado("ROMA", "IT", null));
		assertThat(e.getMessage(), startsWith("O campo pais da classe Estado não pode ser nulo."));
	}

	@Test
	public void teste_toString() {
		assertNotNull(est.toString());
	}

	@Test
	public void teste_equals_simetria() {
		Estado a = new Estado("São Paulo", "SP", BRASIL);
		Estado b = new Estado("são paulo", "Sp", BRASIL);
		assertTrue(a.equals(b) && b.equals(a));
	}

	@Test
	public void teste_equals_reflexividade() {
		assertEquals(est, est);
	}

	@Test
	public void teste_equals_transitividade() {
		Estado a = new Estado(MG);
		Estado b = new Estado(MG);
		Estado c = new Estado(MG);
		assertTrue((a.equals(b) && b.equals(c)) && (a.equals(c)));
	}

	@Test
	public void teste_equals_objetos_nulos_devem_retornar_false() {
		assertNotEquals(est, null);
	}

	@Test
	public void teste_equals_Estados_diferentes_devem_ser_diferentes_um_do_outro() {
		Estado estadoA = new Estado(PI);
		Estado estadoB = new Estado(PR);
		assertNotEquals(estadoA, estadoB);
	}

	@Test
	public void teste_hashcode_consistencia() {
		Estado a = new Estado(SC);
		Estado b = new Estado(SC);
		assertEquals(a.hashCode(), b.hashCode());
	}
}
