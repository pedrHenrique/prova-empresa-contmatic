package br.com.contmatic.util.v1.validator;

import static br.com.contmatic.util.v1.CamposTypes.EMPRESA_RAZAO_SOCIAL_TAMANHO_MAX;
import static br.com.contmatic.util.v1.CamposTypes.EMPRESA_RAZAO_SOCIAL_TAMANHO_MIN;
import static br.com.contmatic.util.v1.validator.NumericValidator.validaTamanho;
import static br.com.contmatic.util.v1.validator.documentos.CnpjValidator.TAMANHO_CNPJ;
import static br.com.contmatic.util.v1.validator.documentos.CnpjValidator.geraCnpjAleatorio;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.contmatic.model.v1.empresa.Empresa;

public class NumericValidatorTest {

	private Class<Empresa> classe;

	private String campo;

	private String valor;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println(" --- Início dos Testes da Classe Util NumericValidatorTest --- \n");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("\n --- Fim dos Testes da Classe Util NumericValidatorTest --- \n");
	}

	@Before
	public void setUp() throws Exception {
		classe = Empresa.class;
		campo = "cnpj";
		valor = geraCnpjAleatorio();
	}

	@After
	public void tearDown() throws Exception {
		classe = null;
		campo = null;
		valor = null;
	}
	
	@Test(expected = Test.None.class)
	public void tamanhos_que_estiverem_dentro_da_margem_permitida_devem_ser_aceitos() {
		Integer cnpj = valor.length();
		validaTamanho(classe, campo, cnpj, TAMANHO_CNPJ);
	}
	
	@Test
	public void deve_validar_o_tamanho_e_nao_permitir_violacoes_no_tamanho_esperado() {
		Integer cnpjComTamanhoErrado = valor.length() + 5;
		Exception e = Assert.assertThrows(IllegalArgumentException.class, () -> validaTamanho(classe, campo, cnpjComTamanhoErrado, TAMANHO_CNPJ));
		assertThat(e.getMessage(), startsWith("O campo " + campo + " da classe " + classe.getSimpleName() + " não pode ter esse tamanho."));
	}

	@Test
	public void deve_validar_o_tamanho_e_nao_permitir_violacoes_no_tamanho_minimo_ou_maximo() {
		campo = "razaoSocial";
		Integer exemploMin = "coca".length();
		Integer exemploMax = "Osvaldo Pereira Nascimento Henrique Junior Indústria de Automóveis Elétricos e Revendedor de Peças LTDA".length();
		Exception eMin = Assert.assertThrows(IllegalArgumentException.class, () -> validaTamanho(classe, campo,  exemploMin, EMPRESA_RAZAO_SOCIAL_TAMANHO_MIN, EMPRESA_RAZAO_SOCIAL_TAMANHO_MAX));
		Exception eMax = Assert.assertThrows(IllegalArgumentException.class, () -> validaTamanho(classe, campo,  exemploMax, EMPRESA_RAZAO_SOCIAL_TAMANHO_MIN, EMPRESA_RAZAO_SOCIAL_TAMANHO_MAX));
		assertThat(eMin.getMessage(), startsWith("O campo " + campo + " da classe " + classe.getSimpleName() + " não pode ter esse tamanho."));
		assertThat(eMax.getMessage(), startsWith("O campo " + campo + " da classe " + classe.getSimpleName() + " não pode ter esse tamanho."));
	}
}
