package br.com.contmatic.empresa.v1.test;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.startsWith;

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

import br.com.contmatic.empresa.v1.model.Endereco;
import util.TestesUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EnderecoTest {

	private static final int NUMERO_TAMANHO_MAX = Endereco.getNumeroTamanhoMax();

	private static final int NUMERO_TAMANHO_MIN = Endereco.getNumeroTamanhoMin();

	private static final int TAMANHO_COMPLEMENTO = Endereco.getTamanhoComplemento();

	private String cep;

	private String numero;

	private String complemento;

	private Endereco end;

	private static final List<String> CEPS_VALIDOS_LISTA = Arrays.asList("77017004", "29170713", "59607084", "69318708",
			"68030190");

	private static final List<String> NUMERACAO_LISTA = Arrays.asList("41A", "0143", "589", "5", "8001", "707", "8091A",
			"801B", "8001A");

	private static final List<String> COMPLEMENTOS_LISTA = Arrays.asList("apt.801", "Casa A", "Casa B", "apt.21", "");

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println(" --- Início dos Testes da Classe Endereco --- \n");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("\n --- Fim dos Testes da Classe Endereco --- \n");
	}

	@Before
	public void setUp() throws Exception {
		this.cep = retornaCepValido();
		this.numero = retornaNumero();
		this.complemento = retornaComplemento();
		this.end = Endereco.cadastraEndereco(cep, numero, complemento);
	}

	private static String retornaComplemento() {
		return COMPLEMENTOS_LISTA.get(new Random().nextInt(COMPLEMENTOS_LISTA.size()));
	}

	private static String retornaNumero() {
		return NUMERACAO_LISTA.get(new Random().nextInt(NUMERACAO_LISTA.size()));
	}

	private static String retornaCepValido() {
		return CEPS_VALIDOS_LISTA.get(new Random().nextInt(CEPS_VALIDOS_LISTA.size()));
	}

	@After
	public void tearDown() throws Exception {
		this.cep = null;
		this.numero = null;
		this.complemento = null;
		this.end = null;
	}

	@Test(expected = Test.None.class)
	public void deve_permitir_cadastrar_um_endereco_passando_cep_numero_complemento_validos() {
		Endereco.cadastraEndereco(EnderecoTest.retornaCepValido(), EnderecoTest.retornaNumero(),
				EnderecoTest.retornaComplemento());
	}

	@Test(expected = Test.None.class)
	public void deve_permitir_cadastrar_um_endereco_mesmo_nao_passando_complemento() {
		Endereco.cadastraEndereco(EnderecoTest.retornaCepValido(), EnderecoTest.retornaNumero());
	}

	@Test
	public void nao_deve_permitir_tentar_cadastrar_um_endereco_passando_cep_nulo() {
		Exception nu = Assert.assertThrows("Nao deve permitir cep nulo", NullPointerException.class,
				() -> Endereco.cadastraEndereco(null, "4546"));

		assertThat(nu.getMessage(), equalTo("Valor não pode ser nulo"));
	}

	@Test
	public void nao_deve_permitir_tentar_cadastrar_um_endereco_passando_cep_com_tamanho_errado() {
		Exception e = Assert.assertThrows("Nao deve permitir cep com tamanho errado", IllegalArgumentException.class,
				() -> Endereco.cadastraEndereco("03575-090", "4546"));

		assertThat(e.getMessage(), equalTo(
				"O tamanho do CEP inserido não é válido. Por favor, insira o CEP sem formatação ou espaços em branco"));
	}

	@Test
	public void nao_deve_permitir_tentar_cadastrar_um_endereco_passando_cep_com_formato_errado() {
		Exception e = Assert.assertThrows("Nao deve permitir cep com formato errado", IllegalArgumentException.class,
				() -> Endereco.cadastraEndereco("xxxxxxxx", "4546"));

		assertThat(e.getMessage(), equalTo("O CEP só pode sor composto de números"));
	}

	@Test
	public void nao_deve_permitir_cadastrar_endereco_se_o_cep_informado_nao_for_valido() {
		Exception e = Assert.assertThrows("Nao deve permitir cep não existente", IllegalArgumentException.class,
				() -> Endereco.cadastraEndereco("00000000", "4546"));

		assertThat(e.getMessage(), startsWith("Ops.. Parece que não foi possível obter as informações do seu CEP."));
	}

	@Test
	public void nao_deve_permitir_tentar_cadastrar_um_endereco_passando_numero_nulo() {
		Exception nu = Assert.assertThrows("Nao deve permitir numero nulo", NullPointerException.class,
				() -> Endereco.cadastraEndereco("03575090", null));

		assertThat(nu.getMessage(), equalTo("Valor não pode ser nulo"));
	}

	@Test
	public void nao_deve_permitir_tentar_cadastrar_um_endereco_passando_numero_vazio() {
		Exception e = Assert.assertThrows("Nao deve permitir numero estando vazio", IllegalArgumentException.class,
				() -> Endereco.cadastraEndereco("03575090", TestesUtils.EMPTYSTR));

		assertThat(e.getMessage(), equalTo("Você não pode inserir uma numeração em branca"));
	}

	@Test
	public void nao_deve_permitir_tentar_cadastrar_um_endereco_passando_numero_com_tamanho_errado() {
		Exception e = Assert.assertThrows("Nao deve permitir numero com tamanho errado", IllegalArgumentException.class,
				() -> Endereco.cadastraEndereco("03575090", "123456789"));

		assertThat(e.getMessage(), equalTo("Tamanho do numero passado não pode ser aceito. Insira uma numeração de "
				+ NUMERO_TAMANHO_MIN + " até " + NUMERO_TAMANHO_MAX));
	}

	@Test
	public void nao_deve_permitir_tentar_cadastrar_um_endereco_passando_numero_com_formatos_errados() {
		String[] numerosMalFormados = {"A41", "85JK", "____", "abcd", "@45"};

		for (String numero : numerosMalFormados) {
			Exception e = Assert.assertThrows("Nao deve permitir numero com formato errado", IllegalArgumentException.class,
				() -> Endereco.cadastraEndereco("03575090", numero));

			assertThat(e.getMessage(), equalTo("Este tipo de numeracao \"" + numero + "\" não pode ser aceita"));
		}
	}

	@Test(expected = Test.None.class)
	public void setNumero_deve_aceitar_numero_com_cinco_casas_se_seu_ultimo_digito_for_uma_letra() {
		end.setNumero("4545A");
	}

	@Test
	public void setNumero_nao_deve_aceitar_numero_com_cinco_casas_se_seu_ultimo_digito_nao_for_uma_letra() {
		String num = "45456";
		Exception e = Assert.assertThrows("Número precisa ser passado com uma formatação correta", IllegalArgumentException.class,
				() -> end.setNumero(num));

		assertThat(e.getMessage(), equalTo("Último dígito desse formato de número precisa ser uma letra. Não " + num.charAt(num.length() - 1)));
	}

	@Test(expected = Test.None.class)
	public void setNumero_deve_aceitar_numero_finalizado_com_letra() {
		end.setNumero("55B");
	}

	@Test(expected = Test.None.class)
	public void setNumero_deve_aceitar_numero_finalizado_sem_letra() {
		end.setNumero("55");
	}

	@Test
	public void setNumero_nao_deve_aceitar_numero_finalizado_com_algo_diferente_de_letra_ou_numero() {
		String num = "45*";
		Exception e = Assert.assertThrows("Número precisa ser passado com uma formatação correta", IllegalArgumentException.class,
				() -> end.setNumero(num));

		assertThat(e.getMessage(), equalTo("Último dígito desse formato de número pode ser uma letra ou número. Não " + num.charAt(num.length() - 1)));
	}

	@Test(expected = Test.None.class)
	public void setComplemento_deve_permitir_complemento_valido() {
		end.setComplemento("Apt 126");
	}

	@Test
	public void setComplemento_nao_deve_permitir_complemento_nulo() {
		Exception nu = Assert.assertThrows("Nao deve permitir complemento nulo", NullPointerException.class,
				() -> end.setComplemento(null));

		assertThat(nu.getMessage(), equalTo("Valor não pode ser nulo"));
	}

	@Test
	public void setComplemento_nao_deve_permitir_complemento_com_tamanho_errado() {
		Exception e = Assert.assertThrows("Nao deve permitir complemento com tamanho errado", IllegalArgumentException.class,
				() -> end.setComplemento("Exemplo de um Complemento Muito Grande"));

		assertThat(e.getMessage(), equalTo("Tamanho de complemento inserido precisa ser menor que " + TAMANHO_COMPLEMENTO));
	}

	@Test
	public void setComplemento_nao_deve_permitir_complemento_com_formato_invalido() {
		Exception e = Assert.assertThrows("Nao deve permitir complemento estando vazio", IllegalArgumentException.class,
				() -> end.setComplemento("Apt.@#$@"));

		assertThat(e.getMessage(), equalTo("Complemento não pode possuir caracteres especiais."));
	}
	
	@Test
	public void nao_deve_permitir_cadastro_caso_algum_valor_retornado_do_viaCep_estiver_vazio() {
		Exception e = Assert.assertThrows("Não deve permitir retorno vazios do viaCep. Caso algo aconteça", IllegalArgumentException.class,
				() -> end.setRua(""));

		assertThat(e.getMessage(), equalTo("Ops, um valor vazio foi rebecido pelo ViaCep. Isto não deveria ter acontecido"));
	}
	
	@Test
	public void teste_equals_simetria() {
		Endereco a = Endereco.cadastraEndereco("58067105", "105");
		Endereco b = Endereco.cadastraEndereco("58067105", "105");
		assertTrue(a.equals(b) && b.equals(a));
	}
	
	@Test
	public void teste_equals_reflexividade() {
		Assert.assertEquals(end, end);
	}
	
	@Test
	public void teste_equals_transitividade() {
		Endereco a = Endereco.cadastraEndereco("04130040", "281");
		Endereco b = Endereco.cadastraEndereco("04130040", "281");
		Endereco c = Endereco.cadastraEndereco("04130040", "281");
		assertThat(a.equals(b) && b.equals(c), equalTo(a.equals(c)));
	}
	
	@Test
	public void teste_equals_objetos_nulos_devem_retornar_false() {
		Assert.assertNotEquals(end, null);
	}
	
	@Test
	public void teste_equals_enderecos_com_ceps_diferente_nao_devem_ser_iguais() {
		Endereco endereco1 = Endereco.cadastraEndereco("87013935", "301");
		Endereco endereco2 = Endereco.cadastraEndereco("59142800", "301");
		Assert.assertNotEquals(endereco1, endereco2);
	}
	
	@Test
	public void teste_equals_enderecos_com_numeros_diferente_nao_devem_ser_iguais() {
		Endereco endereco1 = Endereco.cadastraEndereco("68928145", "301");
		Endereco endereco2 = Endereco.cadastraEndereco("68928145", "302");
		Assert.assertNotEquals(endereco1, endereco2);
	}
	
	@Test
	public void teste_hashcode_consistencia() {
		Endereco a = Endereco.cadastraEndereco("04130040", "281");
		Endereco b = Endereco.cadastraEndereco("04130040", "281");
		Assert.assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void teste_toString() {
		Assert.assertNotNull(end.toString());
	}
}
