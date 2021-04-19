package br.com.contmatic.model.v1.empresa;

import static br.com.contmatic.testes.v1.util.TestesUtils.retornaContato;
import static br.com.contmatic.testes.v1.util.TestesUtils.retornaEndereco;
import static br.com.contmatic.util.v1.validator.documentos.CnpjValidator.geraCnpjAleatorio;
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
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.contmatic.model.v1.endereco.Endereco;
import br.com.contmatic.testes.v1.util.TestesUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmpresaTest {

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
		this.cnpj = geraCnpjAleatorio();
		this.contato = retornaContato();
		this.endereco = retornaEndereco();
		this.emp = new Empresa(razaoSocial, nomeFantasia, cnpj, endereco, contato);
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
	public void deve_aceitar_razoesSociais_validas() {
		String[] razoesSociais = { "Delta Comércio de Alimentos Eireli", "Banco Santander Brasil SA",
				"Nextel Telecominicações Ltda", "Julio da Silva Ferreira MEI" };
		for (String razao : razoesSociais) {
			emp.setRazaoSocial(razao);
		}
	}

	@Test
	public void nao_deve_aceitar_razaoSocial_nula() {
		Exception nu = assertThrows("Nao deve permitir razao social nula", IllegalArgumentException.class,
				() -> emp.setRazaoSocial(null));
		assertThat(nu.getMessage(), equalTo("O campo razaoSocial da classe Empresa não pode ser nulo."));
	}

	@Test
	public void nao_deve_aceitar_razaoSocial_com_tamanho_errado() {
		String exemploRazaoSocialGrande = "Arthur Cristo Meirelis da Silva Junior Sauro Mercado de Produções e"
				+ " Cosméticos Com Foco Urbano e Simples LTDA";
		Exception e = assertThrows("Nao deve permitir razao social com tamanho errado",
				IllegalArgumentException.class, () -> emp.setRazaoSocial(exemploRazaoSocialGrande));
		assertThat(e.getMessage(), startsWith("O campo razaoSocial da classe Empresa não pode ter esse tamanho."));
	}

	@Test
	public void nao_deve_aceitar_razaoSocial_com_formato_invalido() {
		Exception e = assertThrows("Nao deve permitir razao social contendo caracteres especiais",
				IllegalArgumentException.class, () -> emp.setRazaoSocial("Antonio Ribeiro, Produtor de Carnes, Ltda! "));
		assertThat(e.getMessage(),
				equalTo("O campo razaoSocial da classe Empresa não pode possuir caracteres especiais."));
	}

	@Test(expected = Test.None.class)
	public void deve_aceitar_nomesFantasias_validos() {
		String[] nomesFantasia = { "Softmatic", "Itaú", "Coca Cola", "Vivo", "ContaAzul", "Magnetis", "Padaria Estrela",
				"Restaurante Forno e Fogão" };
		for (String nome : nomesFantasia) {
			emp.setNomeFantasia(nome);
		}
	}

	@Test
	public void nao_deve_aceitar_nomeFantasia_com_valor_nulo() {
		Exception nu = assertThrows("Não deve permitir nome fantasia nulo", IllegalArgumentException.class,
				() -> emp.setNomeFantasia(TestesUtils.NULLSTR));
		assertThat(nu.getMessage(), equalTo("O campo nomeFantasia da classe Empresa não pode ser nulo."));
	}

	@Test
	public void nao_deve_aceitar_nomeFantasia_com_tamanho_errado() {
		String exemplo = "Exemplo de um nome fantasia que seria muito grande para ser aceito";
		Exception e = assertThrows(IllegalArgumentException.class, () -> emp.setNomeFantasia(exemplo));
		assertThat(e.getMessage(), startsWith("O campo nomeFantasia da classe Empresa não pode ter esse tamanho."));
	}

	@Test
	public void nao_deve_aceitar_nomesFantasias_incorretos() {
		String[] nomesInvalidos = { "Vi$o", "_____", "Coca-Cola", "!@#$%&*().;_ ", };
		for (String nome : nomesInvalidos) {
			Exception e = assertThrows("Nome fantasia não deveria conter caracteres especiais",
					IllegalArgumentException.class, () -> emp.setNomeFantasia(nome));
			assertThat(e.getMessage(),
					equalTo("O campo nomeFantasia da classe Empresa não pode possuir caracteres especiais."));
		}
	}

	@Ignore("Regex utilizado para este teste não está mais presente no projeto")
	@Test
	public void nao_deve_aceitar_espacamentos_em_branco_invalidos_no_nomeFantasia() {
		String exemploInvalido = "Roda Roda Jequi  ti";
		assertThrows("Espaçamento incorreto em nome deve provocar uma exception", IllegalArgumentException.class,
				() -> emp.setNomeFantasia(exemploInvalido));
	}

	@Test(expected = Test.None.class)
	public void deve_aceitar_cnpj_valido() {
		emp.setCnpj("89664262000164");
	}

	@Test
	public void nao_deve_aceitar_cnpj_nulo() {
		Exception nu = assertThrows("CNPJ que forem informados nulos, devem gerar exception",
				IllegalArgumentException.class, () -> emp.setCnpj(TestesUtils.NULLSTR));
		assertThat(nu.getMessage(), equalTo("O campo CNPJ da classe Empresa não pode ser nulo."));
	}

	@Test
	public void nao_deve_aceitar_tamanho_incorreto_no_cnpj() {
		Exception e = assertThrows("CNPJ com tamanho incorreto deve gerar exception",
				IllegalArgumentException.class, () -> emp.setCnpj("123005"));
		assertThat(e.getMessage(), startsWith("O campo CNPJ da classe Empresa foi informado com o tamanho errado."));
	}

	@Test
	public void nao_deve_aceitar_cnpj_incorreto() {
		Exception e = assertThrows("CNPJ com formato incorreto deve gerar exception",
				IllegalArgumentException.class, () -> emp.setCnpj("xxxxxxxxxxxxxx"));
		assertThat(e.getMessage(), startsWith("O campo CNPJ da classe Empresa foi informado com o formato errado."));
	}

	@Test
	public void nao_deve_aceitar_cnpj_invalido() {
		Exception e = assertThrows("CNPJ's invalidos devem gerar exception", IllegalArgumentException.class,
				() -> emp.setCnpj("23124362123455"));
		assertThat(e.getMessage(),
				equalTo("O CNPJ que você inseriu não é válido. Por favor, insira o CNPJ sem nenhuma formatacao"));
	}

	@Test(expected = Test.None.class)
	public void deve_aceitar_contato_valido() {
		emp.setContato(retornaContato());
	}

	@Test
	public void nao_deve_aceitar_contato_nulo() {
		Exception nu = assertThrows("Contatos passados nulos devem gerar exception", IllegalArgumentException.class,
				() -> emp.setContato(null));
		assertThat(nu.getMessage(), equalTo("O campo contato da classe Empresa não pode ser nulo."));
	}

	@Test(expected = Test.None.class)
	public void deve_aceitar_endereco_valido() {
		emp.setEndereco(retornaEndereco());
	}

	@Test
	public void nao_deve_aceitar_endereco_nulo() {
		Exception nu = assertThrows("enderecos passados nulos devem gerar exception", IllegalArgumentException.class,
				() -> emp.setEndereco(null));
		assertThat(nu.getMessage(), equalTo("O campo endereco da classe Empresa não pode ser nulo."));
	}

	@Test
	public void teste_equals_simetria() {
		Empresa a = new Empresa("Alibaba Comercios ltda", "Alibaba", "38456215000194", retornaEndereco(),
				retornaContato());
		Empresa b = new Empresa("Alibaba Comercios ltda", "Alibaba", "38456215000194", retornaEndereco(),
				retornaContato());
		assertTrue(a.equals(b) && b.equals(a));
	}

	@Test
	public void teste_equals_reflexividade() {
		assertEquals(emp, emp);
	}

	@Test
	public void teste_equals_transitividade() {
		Empresa a = new Empresa("Mercado Livre SA", "Mercado Livre", "62437664000170", retornaEndereco(),
				retornaContato());
		Empresa b = new Empresa("Mercado Livre SA", "Mercado Livre", "62437664000170", retornaEndereco(),
				retornaContato());
		Empresa c = new Empresa("Mercado Livre SA", "Mercado Livre", "62437664000170", retornaEndereco(),
				retornaContato());
		assertThat(a.equals(b) && b.equals(c), equalTo(a.equals(c)));
	}

	@Test
	public void teste_equals_objetos_nulos_devem_retornar_false() {
		assertNotEquals(emp, null);
	}

	@Test
	public void teste_equals_empresas_com_razaoSocial_diferente_nao_devem_ser_iguais() {
		Empresa empresa1 = new Empresa("Alibaba Comercios ltda", "Alibaba", "38456215000194", retornaEndereco(),
				retornaContato());
		Empresa empresa2 = new Empresa("Mercado Livre SA", "Mercado Livre", "38456215000194", retornaEndereco(),
				retornaContato());
		assertNotEquals(empresa1, empresa2);
	}

	@Test
	public void teste_equals_empresas_com_cnpj_diferente_nao_devem_ser_iguais() {
		Empresa empresa1 = new Empresa("Torresmos do Ricardao Mei", "Alibaba", "38456215000194", retornaEndereco(),
				retornaContato());
		Empresa empresa2 = new Empresa("Torresmos do Ricardao Mei", "Mercado Livre", "32395778000133",
				retornaEndereco(), retornaContato());
		assertNotEquals(empresa1, empresa2);
	}

	@Test
	public void teste_hashcode_consistencia() {
		Empresa a = new Empresa("Alibaba Comercios ltda", "Alibaba", "38456215000194", retornaEndereco(),
				retornaContato());
		Empresa b = new Empresa("Alibaba Comercios ltda", "Alibaba", "38456215000194", retornaEndereco(),
				retornaContato());
		assertEquals(a.hashCode(), b.hashCode());
	}

	@Test
	public void teste_toString() {
		assertNotNull("Esperava receber uma lista", emp.toString());
	}
}
