package br.com.contmatic.model.v1.telefone;

import java.util.ArrayList;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static br.com.contmatic.model.v1.telefone.DDIType.DDI1_ESTADOS_UNIDOS;
import static br.com.contmatic.model.v1.telefone.DDIType.DDI1_ILHAS_MARIANAS_DO_NORTE;
import static br.com.contmatic.model.v1.telefone.DDIType.DDI354;
import static br.com.contmatic.model.v1.telefone.DDIType.DDI55;
import static br.com.contmatic.model.v1.telefone.DDIType.DDI852;
import static br.com.contmatic.model.v1.telefone.TelefoneType.CELULAR_NACIONAL;
import static br.com.contmatic.model.v1.telefone.TelefoneType.INTERNACIONAL;
import static br.com.contmatic.model.v1.telefone.TelefoneType.RESIDENCIAL_NACIONAL;
import static br.com.contmatic.testes.util.TestesUtils.NULLSTR;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TelefoneTest {

	private static final int TAMANHO_FIXO = RESIDENCIAL_NACIONAL.getTamanho();

	private static final int TAMANHO_MOVEL = CELULAR_NACIONAL.getTamanho();
	
	private DDIType ddi;

	private String ddd;

	private String numero;

	private TelefoneType tipo;

	private Telefone tel;

	private static final List<DDDType> LISTA_DDD = new ArrayList<DDDType>(Arrays.asList(DDDType.values()));
	
	private static final List<DDIType> LISTA_DDI = new ArrayList<DDIType>(Arrays.asList(DDIType.values()));

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
		this.ddi = this.retornDdi();
		this.ddd = this.retornDdd();
		this.numero = this.geraTelefone(ddi);
		this.tipo = this.retornaTipo(numero.length(), ddi);
		this.tel = new Telefone(ddi, ddd,  numero, tipo);
	}

	private String retornDdd() {
		return LISTA_DDD.get(new Random().nextInt(LISTA_DDD.size())).getDdd();
	}
	
	private DDIType retornDdi() {
		return LISTA_DDI.get(new Random().nextInt(LISTA_DDI.size()));
	}

	private TelefoneType retornaTipo(int tamanho, DDIType ddi) {
		if (ddi.equals(DDI55)) {
			return (tamanho == TAMANHO_FIXO) ? RESIDENCIAL_NACIONAL : CELULAR_NACIONAL;
		} else {
			return INTERNACIONAL;
		}
	}

	private String geraTelefone(DDIType ddi) {
		if (ddi.equals(DDI55)) {
			return geraNumeroNacional();
		} else {
			return geraNumeroInternacional();
		}	
	}

	private String geraNumeroNacional() {
		return (new Random().nextInt(2) == 1) ? geraNumero(TAMANHO_FIXO) : geraNumero(TAMANHO_MOVEL);
	}

	private String geraNumeroInternacional() {
		return geraNumero(new Random().nextInt(5) + 4);
	}

	private String geraNumero(int tamanho) {
		Random random = new Random();
		StringBuilder strNumero = new StringBuilder(tamanho);
		for (int i = 0; i < tamanho; i++) {
			int numero = random.nextInt(9);
			strNumero.append(numero);
		}
		return strNumero.toString();
	}

	@After
	public void tearDown() throws Exception {
		ddi = null;
		ddd = null;
		tel = null;
		tipo = null;
		numero = null;
	}

	@Test(expected = Test.None.class)
	public void deve_aceitar_telefones_recebendo_tipos_corretos() {
		new Telefone(DDI55, retornDdd(), "40028922", RESIDENCIAL_NACIONAL);
		new Telefone(DDI55, retornDdd(), "940028922", CELULAR_NACIONAL);
		new Telefone(DDI852, null, "041047", INTERNACIONAL);
	}

	@Test(expected = Test.None.class)
	public void deve_aceitar_telefones_validos() {
		tel = new Telefone(DDI55, "11", "25678905", null);
		String[] valoresValidos = { "45643904", "941063792", "40028922", "05647890" };
		for (String value : valoresValidos) {
			tel.setNumero(value);
		}
	}

	@Test(expected = Test.None.class)
	public void telefones_de_tipo_nacional_podem_receber_seu_tipo_como_nulo() {
		new Telefone(DDI55, "27", "40028922", null);
	}
	
	@Test
	public void tipo_do_telefone_nacional_deve_ser_setado_automaticamente_mesmo_nao_sendo_informado() {
		Telefone tel = new Telefone(DDI55, retornDdd(), "940028922", null);
		Telefone tel2 = new Telefone(DDI55, retornDdd(), "40028922", null);
		assertThat(tel.getTipo(), equalTo(CELULAR_NACIONAL));
		assertThat(tel2.getTipo(), equalTo(RESIDENCIAL_NACIONAL));
	}
	
	@Test
	public void telefones_de_tipo_internacional_nao_podem_receber_seu_tipo_como_nulo() {
		Exception e = assertThrows("Telefones internacionais devem obrigatoriamente receber o seu tipo como internacional", IllegalArgumentException.class, () -> new Telefone(DDI1_ESTADOS_UNIDOS, "028922", null));
		assertThat(e.getMessage(), equalTo("O Tipo de telefone inserido não condiz com o telefone informado.\n" + 
				"Se você estiver inserindo um telefone internacional, você precisa informar o seu tipo como internacional."));
	}
	
	@Test
	public void telefones_internacionais_nao_devem_conseguir_trocar_seu_tipo_para_nacional() {
		tel = new Telefone(DDI852, "40028922", INTERNACIONAL);
		Exception e = assertThrows("Telefone internacional não pode ter seu tipo trocado para nacional.",IllegalArgumentException.class, () -> tel.setTipo(RESIDENCIAL_NACIONAL));
		assertThat(e.getMessage(), startsWith("O Tipo de telefone inserido não condiz com o telefone informado."));
	}
	
	@Test
	public void telefones_internacionais_devem_ter_seu_tipo_informado() {
		Exception e = assertThrows("Telefones internacionais precisam ter seu tipo informado", IllegalArgumentException.class, () -> new Telefone(DDI1_ESTADOS_UNIDOS, "4048979", null));
		assertThat(e.getMessage(), equalTo("O Tipo de telefone inserido não condiz com o telefone informado.\n" + 
				"Se você estiver inserindo um telefone internacional, você precisa informar o seu tipo como internacional."));
	}
	
	@Test(expected = Test.None.class)
	public void telefones_internacionais_nao_devem_possuir_validacoes_como_telefones_nacionais() {
		String telefoneIceLand = "4313";
		new Telefone(DDI354, telefoneIceLand, INTERNACIONAL);
	}

	@Test
	public void nao_deve_aceitar_numero_de_telefone_nulo() {
		Exception nu = assertThrows("Numero Telefone sendo passado nulo deve gerar exception", IllegalArgumentException.class, () -> tel.setNumero(NULLSTR));
		assertThat(nu.getMessage(), equalTo("O campo numero da classe Telefone não pode ser nulo."));
	}

	@Test
	public void nao_deve_aceitar_numero_de_telefone_vazio() {
		Exception e = assertThrows("Numero Telefone sendo passado vazio deve gerar exception", IllegalArgumentException.class, () -> tel.setNumero("           "));
		assertThat(e.getMessage(), startsWith("O campo numero da classe Telefone foi informado em branco"));
	}

	@Test
	public void nao_deve_aceitar_numero_de_telefone_nacional_com_tamanhos_invalidos() {
		tel = new Telefone(DDI55, retornDdd(), "40028922", RESIDENCIAL_NACIONAL);
		String[] telInvalidos = { "1234567895", "002123440028922K", "____________", "1194105      " };
		for (String value : telInvalidos) {
			Exception e = assertThrows("Tamanho do telefone incorreto deve gerar exception.", IllegalArgumentException.class, () -> tel.setNumero(value));
			assertThat(e.getMessage(), startsWith("O tamanho do telefone inserido está incorreto."));
		}
	}
	
	@Test
	public void nao_deve_aceitar_numero_de_telefone_internacional_com_tamanho_invalido() {		
		Exception e = assertThrows("Tamanho do telefone incorreto deve gerar exception.", IllegalArgumentException.class, () -> new Telefone(DDI1_ILHAS_MARIANAS_DO_NORTE, "040", INTERNACIONAL));
		assertThat(e.getMessage(), startsWith("O tamanho do telefone inserido está incorreto."));		
	}
	
	@Test(expected = Test.None.class)
	public void deve_aceitar_DDD_para_telefone_nacional() {		
		new Telefone(DDI55, "11", geraNumero(TAMANHO_FIXO), RESIDENCIAL_NACIONAL);
	}
	
	@Test
	public void nao_deve_aceitar_DDD_nulo_para_telefone_nacional() {
		tel = new Telefone(DDI55, "11", geraNumero(TAMANHO_FIXO), RESIDENCIAL_NACIONAL);
		Exception nu = assertThrows("DDD sendo passado nulo deve gerar exception", IllegalArgumentException.class, () -> tel.setDdd(null));
		assertThat(nu.getMessage(), equalTo("O campo ddd da classe Telefone não pode ser nulo."));
	}
	
	@Test
	public void nao_deve_aceitar_DDD_sendo_passado_com_tamanho_invalido_para_telefone_nacional() {
		tel = new Telefone(DDI55, "11", geraNumero(TAMANHO_FIXO), RESIDENCIAL_NACIONAL);
		Exception nu = assertThrows("DDD sendo passado com tamanho invalido deve gerar exception", IllegalArgumentException.class, () -> tel.setDdd("44564894"));
		assertThat(nu.getMessage(), startsWith("O campo ddd da classe Telefone não pode ter esse tamanho."));
	}
	
	@Test
	public void nao_deve_aceitar_DDD_sendo_passado_em_branco_para_telefone_nacional() {
		tel = new Telefone(DDI55, "11", geraNumero(TAMANHO_FIXO), RESIDENCIAL_NACIONAL);
		Exception nu = assertThrows("DDD sendo passado em branco deve gerar exception", IllegalArgumentException.class, () -> tel.setDdd("  "));
		assertThat(nu.getMessage(), equalTo("O campo ddd da classe Telefone foi informado em branco, ou com uma quantidade de espaços em brancos excessivos."));
	}
	
	@Test
	public void nao_deve_aceitar_telefone_nacional_com_DDDs_invalidos() {
		tel = new Telefone(DDI55, retornDdd(), "998420563", null);
		String[] dddInvalidos = { "00", "09", "10", "90" };
		for (String ddd : dddInvalidos) {
			Exception e = assertThrows("DDD's não existentes devem gerar exception", IllegalArgumentException.class, () -> tel.setDdd(ddd));
			assertThat(e.getMessage(), startsWith("O DDD " + ddd + " inserido para o telefone nacional não existe"));
		}
	}
	
	@Test
	public void telefones_internacionais_nao_devem_ter_DDD() {
		tel = new Telefone(DDI1_ESTADOS_UNIDOS, "11", "556789003", INTERNACIONAL);
		assertThat(tel.getDdd(), equalTo(null));
	}

	@Test
	public void nao_deve_aceitar_formatos_invalidos_para_telefone() {
		String[] telInvalidos = { "xxxxxxxx", "abcdefgh", "telefone" };
		for (String value : telInvalidos) {
			Exception e = assertThrows("Telefone só deve poder conter dígitos", IllegalArgumentException.class, () -> tel.setNumero(value));
			assertThat(e.getMessage(), equalTo("O campo numero da classe Telefone só pode conter dígitos."));
		}
	}

	@Test
	public void nao_deve_permitir_instanciar_um_telefone_com_seu_tipo_incorreto() {
		List<Exception> listaExceptions = new ArrayList<>();
		String ddd = DDDType.DDD24.getDdd();
		listaExceptions.add(assertThrows(IllegalArgumentException.class, () -> new Telefone(DDI55, ddd, "940028922", RESIDENCIAL_NACIONAL)));
		listaExceptions.add(assertThrows(IllegalArgumentException.class, () -> new Telefone(DDI55, ddd, "40028922", CELULAR_NACIONAL)));
		listaExceptions.add(assertThrows(IllegalArgumentException.class, () -> new Telefone(DDI852, ddd, "24567", CELULAR_NACIONAL)));
		listaExceptions.add(assertThrows(IllegalArgumentException.class, () -> new Telefone(DDI55,  ddd, "40028922", INTERNACIONAL)));
		assertThat(listaExceptions.size(), equalTo(4));
		assertThat(listaExceptions.get(0).getMessage(), startsWith("O Tipo de telefone inserido não condiz com o telefone informado."));
		assertThat(listaExceptions.get(1).getMessage(), startsWith("O Tipo de telefone inserido não condiz com o telefone informado."));
		assertThat(listaExceptions.get(2).getMessage(), startsWith("O Tipo de telefone inserido não condiz com o telefone informado."));
		assertThat(listaExceptions.get(3).getMessage(), startsWith("O Tipo de telefone inserido não condiz com o telefone informado."));
	}

	@Test
	public void tipoTelefone_deve_ser_alterado_automaticamente_se_numeroTelefone_nacional_for_alterado() {
		Telefone telefoneExemplo = new Telefone(DDI55, "11", "40028922", null);
		assertThat(telefoneExemplo.getTipo(), equalTo(RESIDENCIAL_NACIONAL));
		telefoneExemplo.setNumero("940028922");
		assertThat(telefoneExemplo.getTipo(), equalTo(CELULAR_NACIONAL));
	}
	
	@Test(expected = Test.None.class)
	public void deve_aceitar_DDI_para_telefone() {
		String numEstadosUnidos = "4048979";
		new Telefone(DDI1_ESTADOS_UNIDOS, null, numEstadosUnidos, INTERNACIONAL);
	}
	
	@Test
	public void nao_deve_aceitar_DDI_nulo_para_telefone() {
		Exception nu = assertThrows("DDI sendo passado nulo deve gerar exception", IllegalArgumentException.class, () -> tel.setDdi(null));
		assertThat(nu.getMessage(), equalTo("O campo ddi da classe Telefone não pode ser nulo."));
	}

	@Test
	public void teste_equals_simetria() {
		Telefone a = new Telefone(DDI55, "11", "45643904", RESIDENCIAL_NACIONAL);
		Telefone b = new Telefone(DDI55, "11", "45643904", RESIDENCIAL_NACIONAL);
		assertTrue(a.equals(b) && b.equals(a));
	}

	@Test
	public void teste_equals_reflexividade() {
		assertEquals(tel, tel);
	}

	@Test
	public void teste_equals_transitividade() {
		Telefone a = new Telefone(DDI55, "11", "945643904", CELULAR_NACIONAL);
		Telefone b = new Telefone(DDI55, "11", "945643904", CELULAR_NACIONAL);
		Telefone c = new Telefone(DDI55, "11", "945643904", CELULAR_NACIONAL);
		assertTrue((a.equals(b) && b.equals(c)) && (a.equals(c)));
	}

	@Test
	public void teste_equals_objetos_nulos_devem_retornar_false() {
		assertNotEquals(tel, null);
	}

	@Test
	public void teste_equals_telefones_com_numero_diferente_nao_devem_ser_iguais() {
		Telefone celular1 = new Telefone(DDI55, "11", "945643904", CELULAR_NACIONAL);
		Telefone celular2 = new Telefone(DDI55, "11", "998420563", CELULAR_NACIONAL);
		assertNotEquals(celular1, celular2);
	}

	@Test
	public void teste_hashcode_consistencia() {
		Telefone a = new Telefone(DDI55, "11", "945643904", CELULAR_NACIONAL);
		Telefone b = new Telefone(DDI55, "11", "945643904", CELULAR_NACIONAL);
		assertEquals(a.hashCode(), b.hashCode());
	}

	@Test
	public void teste_toString() {
		assertNotNull("Não deveria estar nulo", new Telefone(DDI55, "11","40028922", RESIDENCIAL_NACIONAL).toString());
	}
}
