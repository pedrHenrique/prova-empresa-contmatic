package br.com.contmatic.empresa.v1.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.startsWith;

import br.com.contmatic.empresa.v1.model.Contato;
import br.com.contmatic.empresa.v1.model.contato.Telefone;
import br.com.contmatic.empresa.v1.model.contato.TipoTelefone;
import util.TestesUtils;

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
		this.tel = new Telefone("5540028922", TipoTelefone.RESIDENCIAL);
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
	public void setEmail_nao_deve_gerar_exception_recebendo_email_valido() {
		con.setEmail(EMAIL_LISTA.get(0));
	}

	@Test
	public void setEmail_deve_gerar_exception_recebendo_valor_nulo() {
		Exception nu = Assert.assertThrows("Email não deve aceitar nulos", NullPointerException.class,
				() -> con.setEmail(TestesUtils.NULLSTR));

		assertThat(nu.getMessage(), equalTo("Email não pode ser nulo"));
	}

	@Test
	public void setEmail_deve_gerar_exception_recebendo_tamanho_inapropriado() {
		String emailMuitoLongo = "Xx_xX_rogerinho_ClauDiu_roberto_de_souza_silva1234Xx_xX@hotmail.com";

		Exception e = Assert.assertThrows("Emails muito longos ou pequenos não devem ser aceitos",
				IllegalArgumentException.class, () -> con.setEmail(emailMuitoLongo));

		assertThat(e.getMessage(), equalTo(
				"O Email que você inseriu é muito grande ou muito pequeno para ser um email válido. Tente novamente"));
	}

	@Test
	public void setEmail_deve_gerar_exception_recebendo_valores_invalidos() {
		String[] emailsInvalidos = {"joaoCleber@Dominio.cao", "juaohotmail.com", "Junior@gmail,com", "maria@julianet" };
		
		for (String email : emailsInvalidos) {			
			Exception e = Assert.assertThrows("Não devem ser aceitos emails fora de um padrão",
					IllegalArgumentException.class, () -> con.setEmail(email));

			assertThat(e.getMessage(),
					startsWith("O modelo de email inserido não corresponde a um modelo de email válido."));
		}
	}

	@Test(expected = Test.None.class)
	public void setTelefone_nao_deve_gerar_exception_recebendo_telefone_valido() {
		con.setTelefone(new Telefone("1145643904"));
	}

	@Test
	public void setTelefone_deve_gerar_exception_recebendo_nulo() {
		Exception nu = Assert.assertThrows("Telefone não pode ser informado como nulo", NullPointerException.class,
				() -> con.setTelefone(null));

		assertThat(nu.getMessage(), equalTo("Telefone não pode ser nulo"));
	}
	
	@Test
	public void teste_equals_simetria() {
		Contato a = new Contato(EMAIL_LISTA.get(1), new Telefone("11945643904"));
		Contato b = new Contato(EMAIL_LISTA.get(1), new Telefone("1145643904"));
		assertTrue(a.equals(b) && b.equals(a));
	}
	
	@Test
	public void teste_equals_reflexividade() {
		Assert.assertEquals(con, con);
	}
	
	@Test
	public void teste_equals_transitividade() {
		Contato a = new Contato(EMAIL_LISTA.get(2), new Telefone("11945643904"));
		Contato b = new Contato(EMAIL_LISTA.get(2), new Telefone("55941063792"));
		Contato c = new Contato(EMAIL_LISTA.get(2), new Telefone("1145643904"));
		assertThat(a.equals(b) && b.equals(c), equalTo(a.equals(c)));
	}
	
	@Test
	public void teste_equals_objetos_nulos_devem_retornar_false() {
		Assert.assertNotEquals(con, null);
	}
	
	@Test
	public void teste_equals_contatos_com_emails_diferentes_nao_devem_ser_iguais() {
		Contato contato1 = new Contato(EMAIL_LISTA.get(1), new Telefone("11945643904"));
		Contato contato2 = new Contato(EMAIL_LISTA.get(2), new Telefone("11998420563"));
		Assert.assertNotEquals(contato1, contato2);
	}
	
	@Test
	public void teste_hashcode_consistencia() {
		Contato a = new Contato(EMAIL_LISTA.get(0), new Telefone("11945643904"));
		Contato b = new Contato(EMAIL_LISTA.get(0), new Telefone("11945643904"));
		Assert.assertEquals(a.hashCode(), b.hashCode());
	}

	@Test
	public void teste_toString() {
		Assert.assertNotNull("Não deveria estar nulo", con.toString());
	}
}
