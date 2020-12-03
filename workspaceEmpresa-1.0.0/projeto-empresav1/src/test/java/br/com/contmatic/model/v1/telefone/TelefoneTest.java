package br.com.contmatic.model.v1.telefone;

import static org.junit.Assert.*;

import java.util.ArrayList;
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

import br.com.contmatic.testes.util.TestesUtils;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.startsWith;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TelefoneTest {

	private static final int TAMANHO_FIXO = TipoTelefone.RESIDENCIAL.getTamanho();

	private static final int TAMANHO_MOVEL = TipoTelefone.CELULAR.getTamanho();

	private String ddd;

	private String numero;

	private TipoTelefone tipo;

	private Telefone tel;

	private static final List<TipoDDD> LISTA_DDD = new ArrayList<TipoDDD>(Arrays.asList(TipoDDD.values()));

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println(" --- Início dos Testes da Classe Telefone --- \n");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("\n --- Fim dos Testes da Classe Telefone --- \n");
	}

	@Before
	public void setUp() throws Exception {
		this.ddd = this.retornDdd();
		this.numero = this.geraTelefone();
		this.tipo = this.retornaTipo(numero.length());
		this.tel = new Telefone((ddd + numero), tipo);
	}

	private String retornDdd() {
		return LISTA_DDD.get(new Random().nextInt(LISTA_DDD.size())).getDdd();
	}

	private TipoTelefone retornaTipo(int tamanho) {
		return (tamanho == TAMANHO_FIXO) ? TipoTelefone.RESIDENCIAL : TipoTelefone.CELULAR;
	}

	private String geraTelefone() {
		int aleatorio = new Random().nextInt(2);
		return (aleatorio == 1) ? geraNumero(TAMANHO_FIXO) : geraNumero(TAMANHO_MOVEL);
	}

	private String geraNumero(int tamanho) {
		Random random = new Random();
		StringBuilder fixo = new StringBuilder(tamanho);

		for (int i = 0; i < tamanho; i++) {
			int numero = random.nextInt(9);
			fixo.append(numero);
		}
		return fixo.toString();
	}

	@After
	public void tearDown() throws Exception {
		tel = null;
		ddd = null;
		numero = null;
	}

	@Test(expected = Test.None.class)
	public void deve_aceitar_telefones_recebendo_tipos_corretos() {
		new Telefone("1140028922", TipoTelefone.RESIDENCIAL);
		new Telefone("11940028922", TipoTelefone.CELULAR);
	}

	@Test
	public void tipo_do_telefone_deve_ser_setado_automaticamente_mesmo_nao_sendo_informado() {
		Telefone tel = new Telefone("27940028922");
		Telefone tel2 = new Telefone("2740028922");
		assertThat(tel.getTipoTelefone(), equalTo(TipoTelefone.CELULAR));
		assertThat(tel2.getTipoTelefone(), equalTo(TipoTelefone.RESIDENCIAL));
	}

	@Test(expected = Test.None.class)
	public void deve_aceitar_telefones_validos() {
		String[] valoresValidos = { "1145643904", "11941063792", "1140028922", "1105647890" };
		for (String value : valoresValidos) {
			tel.setNumeroTelefone(value);
		}
	}

	@Test(expected = Test.None.class)
	public void tipo_telefone_pode_receber_nulo_como_parametro() {
		new Telefone("2740028922", null);
	}

	@Test
	public void nao_deve_aceitar_numero_de_telefone_nulo() {
		Exception nu = Assert.assertThrows("Numero Telefone sendo passado nulo deve gerar exception",
				NullPointerException.class, () -> tel.setNumeroTelefone(TestesUtils.NULLSTR));
		assertThat(nu.getMessage(), equalTo("O campo numeroTelefone da classe Telefone não pode ser nulo."));
	}

	@Test
	public void nao_deve_aceitar_numero_de_telefone_vazio() {
		Exception e = Assert.assertThrows("Numero Telefone sendo passado vazio deve gerar exception",
				IllegalArgumentException.class, () -> tel.setNumeroTelefone(TestesUtils.EMPTYSTR));
		assertThat(e.getMessage(), startsWith("O tamanho do telefone inserido está incorreto."));
	}

	@Test
	public void nao_deve_aceitar_numero_de_telefone_com_tamanhos_invalidos() {
		String[] telInvalidos = { "123456789", "002123440028922K", "____________", "1194105      " };
		for (String value : telInvalidos) {
			Exception e = Assert.assertThrows("Tamanho do telefone incorreto deve gerar exception.",
					IllegalArgumentException.class, () -> tel.setNumeroTelefone(value));
			assertThat(e.getMessage(), startsWith("O tamanho do telefone inserido está incorreto."));
		}
	}

	@Test
	public void nao_deve_aceitar_DDDs_invalidos() {
		String[] dddInvalidos = { "0040028922", "0940028922", "1040028922", "9040028922" };
		for (String ddd : dddInvalidos) {
			Exception e = Assert.assertThrows("DDD's não existentes devem gerar exception",
					IllegalArgumentException.class, () -> tel.setNumeroTelefone(ddd));
			assertThat(e.getMessage(),
					startsWith("O DDD " + ddd.substring(0, 2) + " inserido para o telefone não existe"));
		}
	}

	@Test
	public void nao_deve_aceitar_formatos_invalidos_para_telefone() {
		String[] telInvalidos = { "11xxxxxxxx", "11abcdefgh", "11telefone" };
		for (String value : telInvalidos) {
			Exception e = Assert.assertThrows("Tamanho do telefone incorreto deve gerar exception sobre o tamanho",
					IllegalArgumentException.class, () -> tel.setNumeroTelefone(value));
			assertThat(e.getMessage(), equalTo("O campo numeroTelefone da classe Telefone só pode conter números."));
		}
	}

	@Test
	public void nao_deve_permitir_instanciar_um_telefone_com_seu_tipo_incorreto() {
		List<Exception> listaExceptions = new ArrayList<>();
		listaExceptions.add(Assert.assertThrows(IllegalArgumentException.class,
				() -> new Telefone("11940028922", TipoTelefone.RESIDENCIAL)));
		listaExceptions.add(Assert.assertThrows(IllegalArgumentException.class,
				() -> new Telefone("1140028922", TipoTelefone.CELULAR)));
		assertThat(listaExceptions.size(), equalTo(2));
		assertThat(listaExceptions.get(0).getMessage(),
				equalTo("O Tipo de telefone inserido não condiz com o telefone informado."));
		assertThat(listaExceptions.get(1).getMessage(),
				equalTo("O Tipo de telefone inserido não condiz com o telefone informado."));
	}

	@Test
	public void tipoTelefone_deve_ser_alterado_automaticamente_se_numeroTelefone_for_alterado() {
		Telefone telefoneExemplo = new Telefone("1140028922", TipoTelefone.RESIDENCIAL);
		assertThat(telefoneExemplo.getTipoTelefone(), equalTo(TipoTelefone.RESIDENCIAL));
		telefoneExemplo.setNumeroTelefone("11940028922");
		assertThat(telefoneExemplo.getTipoTelefone(), equalTo(TipoTelefone.CELULAR));
	}

	@Test
	public void teste_equals_simetria() {
		Telefone a = new Telefone("1145643904", TipoTelefone.RESIDENCIAL);
		Telefone b = new Telefone("1145643904", TipoTelefone.RESIDENCIAL);
		assertTrue(a.equals(b) && b.equals(a));
	}

	@Test
	public void teste_equals_reflexividade() {
		Assert.assertEquals(tel, tel);
	}

	@Test
	public void teste_equals_transitividade() {
		Telefone a = new Telefone("11945643904", TipoTelefone.CELULAR);
		Telefone b = new Telefone("11945643904", TipoTelefone.CELULAR);
		Telefone c = new Telefone("11945643904", TipoTelefone.CELULAR);
		assertTrue((a.equals(b) && b.equals(c)) && (a.equals(c)));
	}

	@Test
	public void teste_equals_objetos_nulos_devem_retornar_false() {
		Assert.assertNotEquals(tel, null);
	}

	@Test
	public void teste_equals_telefones_com_numero_diferente_nao_devem_ser_iguais() {
		Telefone celular1 = new Telefone("11945643904", TipoTelefone.CELULAR);
		Telefone celular2 = new Telefone("11998420563", TipoTelefone.CELULAR);
		Assert.assertNotEquals(celular1, celular2);
	}

	@Test
	public void teste_hashcode_consistencia() {
		Telefone a = new Telefone("11945643904", TipoTelefone.CELULAR);
		Telefone b = new Telefone("11945643904", TipoTelefone.CELULAR);
		Assert.assertEquals(a.hashCode(), b.hashCode());
	}

	@Test
	public void teste_toString() {
		Assert.assertNotNull("Não deveria estar nulo", new Telefone("1140028922").toString());
	}
}
