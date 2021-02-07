package br.com.contmatic.model.v1.empresa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

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

import br.com.contmatic.model.v1.telefone.Telefone;
import br.com.contmatic.model.v1.telefone.TelefoneType;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static br.com.contmatic.model.v1.telefone.DDIType.DDI55;
import static br.com.contmatic.testes.util.TestesUtils.NULLSTR;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ContatoTest {

	private String email;

	private Contato con;

	private Telefone tel;

	private static final List<String> EMAIL_LISTA = Arrays.asList("pedroTeste@contmatic.com",
			"email_teste@gmail.com.br", "emailGenerico@softmatic.com");

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println(" --- Início dos Testes da Classe Contato --- \n");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("\n --- Fim dos Testes da Classe Contato --- \n");
	}

	@Before
	public void setUp() throws Exception {
		this.email = retornaNomeAleatorioEmailLista();
		this.tel = new Telefone(DDI55, "55", "40028922", TelefoneType.RESIDENCIAL_NACIONAL);
		this.con = new Contato(email, tel);
	}

	private static String retornaNomeAleatorioEmailLista() {
		return EMAIL_LISTA.get(new Random().nextInt(EMAIL_LISTA.size()));
	}

	@After
	public void tearDown() throws Exception {
		email = null;
		tel = null;
		con = null;
	}

	@Test(expected = Test.None.class)
	public void deve_aceitar_email_valido() {
		con.setEmail(EMAIL_LISTA.get(0));
	}

	@Test
	public void nao_deve_aceitar_email_nulo() {
		Exception nu = assertThrows("Email não deve aceitar nulos", IllegalArgumentException.class,
				() -> con.setEmail(NULLSTR));
		assertThat(nu.getMessage(), equalTo("O campo email da classe Contato não pode ser nulo."));
	}

	@Test
	public void nao_deve_aceitar_email_com_tamanho_inapropriado() {
		String emailMuitoLongo = "Xx_xX_rogerinho_ClauDiu_roberto_de_souza_silva1234Xx_xX@hotmail.com";
		Exception e = assertThrows("Emails muito longos ou pequenos não devem ser aceitos",
				IllegalArgumentException.class, () -> con.setEmail(emailMuitoLongo));
		assertThat(e.getMessage(), startsWith("O campo email da classe Contato não pode ter esse tamanho."));
	}

	@Test
	public void nao_deve_aceitar_email_com_valores_invalidos() {
		String[] emailsInvalidos = {"joaoCleber@Dominio.cao", "juaohotmail.com", "Junior@gmail,com", "maria@julianet" };
		for (String email : emailsInvalidos) {			
			Exception e = assertThrows("Não devem ser aceitos emails fora de um padrão",
					IllegalArgumentException.class, () -> con.setEmail(email));
			assertThat(e.getMessage(),
					startsWith("O modelo de email inserido não corresponde a um modelo de email válido."));
		}
	}

	@Test(expected = Test.None.class)
	public void deve_aceitar_telefone_valido() {
		con.setTelefone(new Telefone(DDI55, "11", "45643904", null));
	}

	@Test
	public void nao_deve_aceitar_telefone_nulo() {
		Exception nu = assertThrows("Telefone não pode ser informado como nulo", IllegalArgumentException.class,
				() -> con.setTelefone(null));
		assertThat(nu.getMessage(), equalTo("O campo telefone da classe Contato não pode ser nulo."));
	}
	
	@Test
	public void teste_equals_simetria() {
		Contato a = new Contato(EMAIL_LISTA.get(1), new Telefone(DDI55, "11", "945643904", null));
		Contato b = new Contato(EMAIL_LISTA.get(1), new Telefone(DDI55, "11", "45643904", null));
		assertTrue(a.equals(b) && b.equals(a));
	}
	
	@Test
	public void teste_equals_reflexividade() {
		assertEquals(con, con);
	}
	
	@Test
	public void teste_equals_transitividade() {
		Contato a = new Contato(EMAIL_LISTA.get(2), new Telefone(DDI55, "11", "945643904", null));
		Contato b = new Contato(EMAIL_LISTA.get(2), new Telefone(DDI55, "55", "941063792", null));
		Contato c = new Contato(EMAIL_LISTA.get(2), new Telefone(DDI55, "11", "45643904", null));
		assertTrue((a.equals(b) && b.equals(c)) && (a.equals(c)));
	}
	
	@Test
	public void teste_equals_objetos_nulos_devem_retornar_false() {
		assertNotEquals(con, null);
	}
	
	@Test
	public void teste_equals_contatos_com_emails_diferentes_nao_devem_ser_iguais() {
		Contato contato1 = new Contato(EMAIL_LISTA.get(1), new Telefone(DDI55, "11", "945643904", null));
		Contato contato2 = new Contato(EMAIL_LISTA.get(2), new Telefone(DDI55, "11", "998420563", null));
		assertNotEquals(contato1, contato2);
	}
	
	@Test
	public void teste_hashcode_consistencia() {
		Contato a = new Contato(EMAIL_LISTA.get(0), new Telefone(DDI55, "11", "945643904", null));
		Contato b = new Contato(EMAIL_LISTA.get(0), new Telefone(DDI55, "11", "945643904", null));
		assertEquals(a.hashCode(), b.hashCode());
	}

	@Test
	public void teste_toString() {
		assertNotNull("Não deveria estar nulo", con.toString());
	}
}
