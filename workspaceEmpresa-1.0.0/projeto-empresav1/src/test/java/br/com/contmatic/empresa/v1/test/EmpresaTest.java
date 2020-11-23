package br.com.contmatic.empresa.v1.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.contmatic.empresa.v1.model.Empresa;
import br.com.contmatic.empresa.v1.model.Contato;
import br.com.contmatic.empresa.v1.model.Endereco;
import br.com.contmatic.empresa.v1.model.contato.Telefone;

import static br.com.contmatic.empresa.v1.util.Documentos.cnpjAleatorio;
import util.TestesUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmpresaTest {

	private static final int RAZAO_SOCIAL_TAMANHO_MAX = Empresa.getRazaoSocialTamanhoMax();

	private static final int RAZAO_SOCIAL_TAMANHO_MIN = Empresa.getRazaoSocialTamanhoMin();

	private static final int NOME_FANTASIA_TAMANHO_MIN = Empresa.getNomeFantasiaTamanhoMin();

	private static final int NOME_FANTASIA_TAMANHO_MAX = Empresa.getNomeFantasiaTamanhoMax();

	private String razaoSocial;

	private String nomeFantasia;

	private String cnpj;

	private Endereco endereco;

	private Contato contato;

	private Empresa emp;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println(" --- Início dos Testes da Classe Empresa --- \n");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("\n --- Fim dos Testes da Classe Empresa --- \n");
	}

	@Before
	public void setUp() throws Exception {
		this.razaoSocial = "TesteMatic Testes e Aprendizagens Ltda";
		this.nomeFantasia = "TesteMatic";
		this.cnpj = cnpjAleatorio();
		this.contato = retornaContato();
		this.endereco = retornaEndereco();
		this.emp = new Empresa(razaoSocial, nomeFantasia, cnpj, endereco, contato);
	}

	private static Endereco retornaEndereco() {
		return Endereco.cadastraEndereco("03575090", String.valueOf(new Random().nextInt(100)));
	}

	private static Contato retornaContato() {
		return new Contato("testematic@contmatic.com", new Telefone("1125068922"));
	}

	@After
	public void tearDown() throws Exception {
		this.razaoSocial = null;
		this.nomeFantasia = null;
		this.cnpj = null;
		this.contato = null;
		this.endereco = null;
		this.emp = null;
	}

	@Test(expected = Test.None.class)
	public void setRazaoSocial_nao_deve_gerar_exception_recebendo_nomes_validos() {
		String[] razoesSociais = { "Delta Comércio de Alimentos Eireli", "Banco Santander Brasil S.A.",
				"Nextel Telecominicações Ltda", "Julio da Silva Ferreira MEI" };

		for (String razao : razoesSociais) {
			emp.setRazaoSocial(razao);
		}
	}

	@Test
	public void setRazaoSocial_nao_deve_permitir_razaoSocial_nula() {
		Exception nu = Assert.assertThrows("Nao deve permitir razao social nula", NullPointerException.class,
				() -> emp.setRazaoSocial(null));

		assertThat(nu.getMessage(), equalTo("Valor não pode ser nulo"));
	}

	@Test
	public void setRazaoSocial_nao_deve_permitir_razaoSocial_com_tamanho_errado() {
		Exception e = Assert.assertThrows("Nao deve permitir razao social com tamanho errado",
				IllegalArgumentException.class,
				() -> emp.setRazaoSocial("Arthur Cristo Meirelis da Silva Junior Sauro"
						+ " Mercado de Produções e Cosméticos Com Foco Urbano e Simples LTDA"));

		assertThat(e.getMessage(), equalTo("Razão Social não pôde conter tamanho inferior a " + RAZAO_SOCIAL_TAMANHO_MIN
				+ " ou maior que " + RAZAO_SOCIAL_TAMANHO_MAX));
	}

	@Test
	public void setRazaoSocial_nao_deve_permitir_razaoSocial_com_formato_invalido() {
		Exception e = Assert.assertThrows("Nao deve permitir razao social contendo caracteres especiais",
				IllegalArgumentException.class,
				() -> emp.setRazaoSocial("Antonio Ribeiro, Produtor de Carnes, Ltda! "));

		assertThat(e.getMessage(), equalTo("O nome não pode possuir caracteres especiais"));
	}

	@Test(expected = Test.None.class)
	public void setNomeFantasia_nao_deve_gerar_exception_recebendo_nomes_validos() {
		String[] nomesFantasia = { "Softmatic", "Itaú", "Coca Cola", "Vivo", "ContaAzul", "Magnetis", "Padaria Estrela",
				"Restaurante Forno e Fogão" };

		for (String nome : nomesFantasia) {
			emp.setNomeFantasia(nome);
		}
	}

	@Test
	public void setNomeFantasia_deve_gerar_exception_recebendo_valor_nulo() {
		Exception nu = Assert.assertThrows("Nome fantasia nulo deve gerar exception", NullPointerException.class,
				() -> emp.setNomeFantasia(TestesUtils.NULLSTR));

		assertThat(nu.getMessage(), equalTo("Valor não pode ser nulo"));
	}

	@Test
	public void setNomeFantasia_nao_deve_permitir_nomeFantasia_com_tamanho_errado() {
		Exception e = Assert.assertThrows("Nao deve permitir nome fantasia com tamanho errado",
				IllegalArgumentException.class,
				() -> emp.setNomeFantasia("Exemplo de um nome fantasia que seria muito grande para ser aceito"));

		assertThat(e.getMessage(), equalTo("Nome Fantasia não pôde conter tamanho inferior a "
				+ NOME_FANTASIA_TAMANHO_MIN + " ou maior que " + NOME_FANTASIA_TAMANHO_MAX));
	}

	@Test
	public void setNomeFantasia_deve_gerar_exception_recebendo_valores_incorretos() {
		String[] nomesInvalidos = { "Vi$o", "_____", "Coca-Cola", "!@#$%&*().;_ ", };

		for (String nome : nomesInvalidos) {
			Exception e = Assert.assertThrows("Nome fantasia não deveria conter caracteres especiais",
					IllegalArgumentException.class, () -> emp.setNomeFantasia(nome));

			assertThat(e.getMessage(), equalTo("O nome não pode possuir caracteres especiais"));
		}
	}

	@Ignore("Regex utilizado para este teste não está mais presente no projeto")
	@Test
	public void setNomeFantasia_deve_gerar_exception_espacamentos_em_branco() {
		String exemploInvalido = "Roda Roda Jequi  ti";

		Assert.assertThrows("Espaçamento incorreto em nome deve provocar uma exception", IllegalArgumentException.class,
				() -> emp.setNomeFantasia(exemploInvalido));
	}

	@Test(expected = Test.None.class)
	public void setCNPJ_deve_permitir_cnpj_recebendo_valor_valido() {
		emp.setCnpj("89664262000164");
	}

	@Test
	public void setCNPJ_nao_deve_permitir_cnpj_nulo() {
		Exception nu = Assert.assertThrows("CNPJ que forem informados nulos, devem gerar exception",
				NullPointerException.class, () -> emp.setCnpj(TestesUtils.NULLSTR));

		assertThat(nu.getMessage(), equalTo("Valor não pode ser nulo"));
	}

	@Test
	public void setCNPJ_nao_deve_permitir_cnpj_recebendo_valores_incorretos() {
		Exception e = Assert.assertThrows("CNPJ's incorretos devem gerar exception", IllegalArgumentException.class,
				() -> emp.setCnpj("23124362123455"));

		assertThat(e.getMessage(),
				equalTo("O CNPJ que você inseriu não é válido. Por favor, insira o CNPJ sem nenhuma formatacao"));
	}

	@Test(expected = Test.None.class)
	public void setContato_nao_deve_gerar_exception_recebendo_contato_valido() {
		emp.setContato(EmpresaTest.retornaContato());
	}

	@Test
	public void setContato_deve_gerar_exception_recebendo_contato_nulo() {
		Exception nu = Assert.assertThrows("Contatos passados nulos devem gerar exception", NullPointerException.class,
				() -> emp.setContato(null));

		assertThat(nu.getMessage(), equalTo("Contato não pode ser passado como nulo"));
	}

	@Test(expected = Test.None.class)
	public void setEndereco_nao_deve_gerar_exception_recebendo_endereco_valido() {
		emp.setEndereco(EmpresaTest.retornaEndereco());
	}
	
	@Test
	public void setEndereco_deve_gerar_exception_recebendo_endereco_nulo() {
		Exception nu = Assert.assertThrows("enderecos passados nulos devem gerar exception", NullPointerException.class,
				() -> emp.setEndereco(null));

		assertThat(nu.getMessage(), equalTo("Endereco não pode ser passado como nulo"));
	}

	@Test
	public void teste_equals_simetria() {
		Empresa a = new Empresa("Alibaba Comercios ltda", "Alibaba", "38456215000194", EmpresaTest.retornaEndereco(), EmpresaTest.retornaContato());
		Empresa b = new Empresa("Alibaba Comercios ltda", "Alibaba", "38456215000194", EmpresaTest.retornaEndereco(), EmpresaTest.retornaContato());
		assertTrue(a.equals(b) && b.equals(a));
	}
	
	@Test
	public void teste_equals_reflexividade() {
		Assert.assertEquals(emp, emp);
	}
	
	@Test
	public void teste_equals_transitividade() {
		Empresa a = new Empresa("Mercado Livre S.A", "Mercado Livre", "62437664000170", EmpresaTest.retornaEndereco(), EmpresaTest.retornaContato());
		Empresa b = new Empresa("Mercado Livre S.A", "Mercado Livre", "62437664000170", EmpresaTest.retornaEndereco(), EmpresaTest.retornaContato());
		Empresa c = new Empresa("Mercado Livre S.A", "Mercado Livre", "62437664000170", EmpresaTest.retornaEndereco(), EmpresaTest.retornaContato());
		assertThat(a.equals(b) && b.equals(c), equalTo(a.equals(c)));
	}
	
	@Test
	public void teste_equals_objetos_nulos_devem_retornar_false() {
		Assert.assertNotEquals(emp, null);
	}
	
	@Test
	public void teste_equals_empresas_com_razaoSocial_diferente_nao_devem_ser_iguais() {
		Empresa empresa1 = new Empresa("Alibaba Comercios ltda", "Alibaba", "38456215000194", EmpresaTest.retornaEndereco(), EmpresaTest.retornaContato());
		Empresa empresa2 = new Empresa("Mercado Livre S.A", "Mercado Livre", "38456215000194", EmpresaTest.retornaEndereco(), EmpresaTest.retornaContato());
		Assert.assertNotEquals(empresa1, empresa2);
	}

	@Test
	public void teste_equals_empresas_com_cnpj_diferente_nao_devem_ser_iguais() {
		Empresa empresa1 = new Empresa("Torresmos do Ricardao Mei", "Alibaba", "38456215000194", EmpresaTest.retornaEndereco(), EmpresaTest.retornaContato());
		Empresa empresa2 = new Empresa("Torresmos do Ricardao Mei", "Mercado Livre", "32395778000133", EmpresaTest.retornaEndereco(), EmpresaTest.retornaContato());
		Assert.assertNotEquals(empresa1, empresa2);
	}
	
	@Test
	public void teste_hashcode_consistencia() {
		Empresa a = new Empresa("Alibaba Comercios ltda", "Alibaba", "38456215000194", EmpresaTest.retornaEndereco(), EmpresaTest.retornaContato());
		Empresa b = new Empresa("Alibaba Comercios ltda", "Alibaba", "38456215000194", EmpresaTest.retornaEndereco(), EmpresaTest.retornaContato());
		Assert.assertEquals(a.hashCode(), b.hashCode());
	}
	
	@Test
	public void teste_toString() {
		assertNotNull("Esperava receber uma lista", emp.toString());
	}
}
