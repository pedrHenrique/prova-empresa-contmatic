package br.com.contmatic.model.util;

import static org.junit.Assert.*;

import static br.com.contmatic.util.documentos.CnpjValidator.geraCnpjAleatorio;
import static br.com.contmatic.util.AtributoValidator.validaNulo;
import static br.com.contmatic.util.CamposTypes.FUNCIONARIO_NOME_TAMANHO_MIN;
import static br.com.contmatic.util.AtributoValidator.validaEspacamento;

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
import br.com.contmatic.model.v1.empresa.Funcionario;

public class AtributoValidatorTest {

	private Class<Empresa> classe;

	private String campo;

	private String valor;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println(" --- Início dos Testes da Classe Util AtributoValidator --- \n");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("\n --- Fim dos Testes da Classe Util AtributoValidator --- \n");
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
	public void deve_aceitar_classe_campo_com_valores_validos() {
		validaNulo(classe, campo, valor);
	}

	@Test
	public void deve_validar_e_nao_aceitar_nomes_com_espacamento_invalidos() {
		Exception e = Assert.assertThrows(IllegalArgumentException.class,
				() -> validaEspacamento(Funcionario.class, "nome", "                 R", FUNCIONARIO_NOME_TAMANHO_MIN));
		assertThat(e.getMessage(), equalTo(
				"O campo nome da classe Funcionario foi informado em branco, ou com uma quantidade de espaços em brancos excessivos."));
	}

	@Test
	public void nao_deve_aceitar_classe_nula_para_validar() {
		Exception nu = Assert.assertThrows(NullPointerException.class, () -> validaNulo(null, campo, valor));
		assertThat(nu.getMessage(), equalTo("Você não pode validar uma classe passando a mesma nula."));
	}

	@Test
	public void nao_deve_aceitar_classe_object_para_validar() {
		Exception e = Assert.assertThrows(IllegalArgumentException.class, () -> validaNulo(Object.class, campo, valor));
		assertThat(e.getMessage(), startsWith("Classe Object não deve ser validada."));
	}

	@Test
	public void nao_deve_aceitar_campo_nao_existente_na_classe_informada() {
		campo = "ramal";

		Exception e = Assert.assertThrows(IllegalArgumentException.class, () -> validaNulo(classe, campo, valor));
		assertThat(e.getMessage(),
				startsWith("O campo " + campo + " não foi encontrado na classe " + classe.getSimpleName() + "."));
	}

	@Test
	public void nao_deve_aceitar_campo_nulo() {
		Exception nu = Assert.assertThrows(NullPointerException.class, () -> validaNulo(classe, null, valor));
		assertThat(nu.getMessage(), startsWith("Um campo da classe " + classe + " foi informado como nulo."));
	}

	@Test
	public void validaNulo_deve_lancar_exception_se_valor_passado_for_nulo() {
		Exception nu = Assert.assertThrows(NullPointerException.class, () -> validaNulo(classe, campo, null));
		assertThat(nu.getMessage(),
				startsWith("O campo " + campo + " da classe " + classe.getSimpleName() + " não pode ser nulo."));
	}
}
