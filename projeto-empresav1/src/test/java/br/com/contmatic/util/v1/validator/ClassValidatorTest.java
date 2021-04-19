package br.com.contmatic.util.v1.validator;

import static br.com.contmatic.util.v1.validator.StringValidator.validaNulo;
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

public class ClassValidatorTest {

	private Class<Empresa> classe;

	private String campo;

	private String valor;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println(" --- Início dos Testes da Classe Util ValidatorUtilsTest --- \n");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("\n --- Fim dos Testes da Classe Util ValidatorUtilsTest --- \n");
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
	public void nao_deve_aceitar_classe_nula_para_validar() {
		Exception nu = Assert.assertThrows(IllegalArgumentException.class, () -> validaNulo(null, campo, valor));
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
		assertThat(e.getMessage(), startsWith("O campo " + campo + " não foi encontrado na classe " + classe.getSimpleName() + "."));
	}
}
