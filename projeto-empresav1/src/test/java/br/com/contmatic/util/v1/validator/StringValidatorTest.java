package br.com.contmatic.util.v1.validator;

import static br.com.contmatic.util.v1.CamposTypes.EMPRESA_NOME_FANTASIA_TAMANHO_MIN;
import static br.com.contmatic.util.v1.validator.StringValidator.validaEspacamento;
import static br.com.contmatic.util.v1.validator.StringValidator.validaNulo;
import static br.com.contmatic.util.v1.validator.StringValidator.verificaSeCampoPossuiDigitos;
import static br.com.contmatic.util.v1.validator.StringValidator.verificaSeCampoPossuiSimbolos;
import static br.com.contmatic.util.v1.validator.StringValidator.verificaSeCampoSoPossuiDigitos;
import static br.com.contmatic.util.v1.validator.documentos.CnpjValidator.geraCnpjAleatorio;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.contmatic.model.v1.empresa.Empresa;

public class StringValidatorTest {
	
	private Class<Empresa> classe;

	private String campo;

	private String valor;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println(" --- Início dos Testes da Classe Util StringValidatorTest --- \n");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("\n --- Fim dos Testes da Classe Util StringValidatorTest --- \n");
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

	@Test
	public void nao_deve_aceitar_campo_nulo() {
		Exception nu = Assert.assertThrows(IllegalArgumentException.class, () -> validaNulo(classe, null, valor));
		assertThat(nu.getMessage(), startsWith("Um campo da classe " + classe + " foi informado como nulo."));
	}

	@Test
	public void deve_lancar_exception_se_valor_passado_for_nulo() {
		Exception nu = Assert.assertThrows(IllegalArgumentException.class, () -> validaNulo(classe, campo, null));
		assertThat(nu.getMessage(), startsWith("O campo " + campo + " da classe " + classe.getSimpleName() + " não pode ser nulo."));
	}
	
	@Test
	public void deve_validar_e_nao_aceitar_nomes_com_espacamento_invalido() {
		campo = "nomeFantasia";
		Exception e = Assert.assertThrows(IllegalArgumentException.class, () -> validaEspacamento(classe, campo, "                    R", EMPRESA_NOME_FANTASIA_TAMANHO_MIN));
		assertThat(e.getMessage(), equalTo("O campo " + campo + " da classe " + classe.getSimpleName() + " foi informado em branco, ou com uma quantidade de espaços em brancos excessivos."));
	}
	
	@Test
	public void deve_validar_e_nao_permitir_a_presenca_de_simbolos() {
		Exception e = Assert.assertThrows(IllegalArgumentException.class, () -> verificaSeCampoPossuiSimbolos(classe, campo,  "484&*¨79836"));
		assertThat(e.getMessage(), startsWith("O campo " + campo + " da classe " + classe.getSimpleName() + " não pode possuir caracteres especiais."));
	}
	
	@Test
	public void deve_validar_e_nao_permitir_a_presenca_de_digitos() {
		campo = "razaoSocial";
		Exception e = Assert.assertThrows(IllegalArgumentException.class, () -> verificaSeCampoPossuiDigitos(classe, campo,  "Fed4r5ut0"));
		assertThat(e.getMessage(), startsWith("O campo " + campo + " da classe " + classe.getSimpleName() + " não pode possuir dígitos."));
	}
	
	@Test
	public void deve_validar_e_apenas_permitir_a_presenca_de_digitos() {
		Exception e = Assert.assertThrows(IllegalArgumentException.class, () -> verificaSeCampoSoPossuiDigitos(classe, campo,  "484abc798de"));
		assertThat(e.getMessage(), startsWith("O campo " + campo + " da classe " + classe.getSimpleName() + " só pode possuir dígitos."));
	}

}
