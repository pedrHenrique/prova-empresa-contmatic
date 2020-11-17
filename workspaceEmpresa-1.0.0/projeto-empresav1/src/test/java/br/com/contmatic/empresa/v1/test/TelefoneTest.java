package br.com.contmatic.empresa.v1.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.startsWith;

import br.com.contmatic.empresa.v1.model.contato.Telefone;
import br.com.contmatic.empresa.v1.model.contato.TipoTelefone;
import br.com.contmatic.empresa.v1.util.RegexModel;
import util.ConstantesParaTestes;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TelefoneTest {

	private Telefone tel;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		tel = new Telefone();
	}

	@After
	public void tearDown() throws Exception {
		tel = null;
	}

	/*
	 * Está seção de testes tem o intuito de testar a criação de objetos da classe
	 */

	@Test(expected = Test.None.class)
	public void setTipoContato_nao_deve_gerar_exception_recebendo_nulo_na_chamada_de_construtor() {
		new Telefone("27 4002-8922", null);
	}
	
	@Test
	public void tipoContato_deve_ser_setado_automaticamente_mesmo_nao_sendo_informado() {
		Telefone tel = new Telefone("27 94002-8922"); // -> Informando um celular
		Telefone tel2 = new Telefone("27 4002-8922"); // -> Informando um Fixo
		
		assertThat(tel.getTipoTelefone(), equalTo(TipoTelefone.CELULAR));
		assertThat(tel2.getTipoTelefone(), equalTo(TipoTelefone.RESIDENCIAL));
	}
	
	@Test(expected = Test.None.class)
	public void tipoContato_comercial_pode_ser_aceito_recebendo_tamanhos_de_telefone_e_celular() {
 		Telefone tel = new Telefone("27 94002-8922", TipoTelefone.COMERCIAL); // -> Informando um celular
		Telefone tel2 = new Telefone("27 4002-8922", TipoTelefone.COMERCIAL); // -> Informando um Fixo
		
		assertThat(tel.getTipoTelefone(), equalTo(TipoTelefone.COMERCIAL));
		assertThat(tel2.getTipoTelefone(), equalTo(TipoTelefone.COMERCIAL));
	}
	
	/*
	 * Seção de Teste dos getters/setters
	 */

	@Test(expected = Test.None.class)
	public void setNumeroTelefone_nao_deve_gerar_exception_recebendo_valores_validos() {
		String[] valoresValidos = { "1145643904", "11941063792", "(11)4564-9304", "(11)94106-3792", "(11) 94106-3792",
				"11 941063792", "11 94106-3792", "11 4106-3792", "11 44897-8789", "11 44897 8789" };

		for (String value : valoresValidos) {
			tel.setNumeroTelefone(value);
		}
	}

	@Test
	public void setNumeroTelefone_deve_gerar_exception_recebendo_valor_nulo() {
		Exception nu = Assert.assertThrows("Numero Telefone sendo passado nulo deve gerar exception",
				NullPointerException.class, () -> tel.setNumeroTelefone(ConstantesParaTestes.NULLSTR));

		assertThat(nu.getMessage(), equalTo("Telefone não pode estar vazio"));

	}

	@Test
	public void setNumeroTelefone_deve_gerar_exception_recebendo_valor_vazio() {
		Exception e = Assert.assertThrows("Numero Telefone sendo passado vazio deve gerar exception",
				IllegalArgumentException.class, () -> tel.setNumeroTelefone(ConstantesParaTestes.EMPTYSTR));

		assertThat(e.getMessage(), startsWith("Este formato de telefone passado não pode ser aceito."));
	}

	@Test
	public void setNumeroTelefone_deve_gerar_exception_recebendo_formato_com_valores_invalidos() {
		String[] telInvalidos = { "(xx)xxxx-xxxx", "__abcdefghi", "11 40028922K", "11     -    ", "1194105      " };

		for (String value : telInvalidos) {
			Exception e = Assert.assertThrows("Numero Telefone sendo passado incorretamente deve gerar exception",
					IllegalArgumentException.class, () -> tel.setNumeroTelefone(value));

			assertThat(e.getMessage(), startsWith("Este formato de telefone passado não pode ser aceito."));
		}
	}

	@Test(expected = Test.None.class)
	public void setDdd_nao_deve_gerar_exception_recebendo_valores_validos() {
		String[] valoresValidos = { "5445643904", "12941063792", "(14)4564-9304", "(18)94106-3792", "(33) 94106-3792",
				"41 941063792", "42 94106-3792", "87 4106-3792", "71 44897-8789", "91 44897 8789" };

		for (String value : valoresValidos) {
			tel.setDdd(value.replaceAll(RegexModel.FORMATATELEFONE, "").substring(0, 2));
		}
	}

	@Test
	public void setDdd_deve_gerar_exception_recebendo_valor_nulo() {
		Exception nu = Assert.assertThrows("DDD sendo passado nulo deve gerar exception", NullPointerException.class,
				() -> tel.setDdd(ConstantesParaTestes.NULLSTR));

		assertThat(nu.getMessage(), equalTo("DDD não deveria ser passado como nulo ou vazio"));
	}

	@Test
	public void setDdd_deve_gerar_exception_recebendo_valor_vazio() {
		Exception e = Assert.assertThrows("DDD sendo passado vazio deve gerar exception",
				IllegalArgumentException.class, () -> tel.setDdd(ConstantesParaTestes.EMPTYSTR));

		assertThat(e.getMessage(), equalTo("O DDD " + ConstantesParaTestes.EMPTYSTR + " não é válido."));
	}

	@Test
	public void setDdd_deve_gerar_exception_recebendo_DDDs_invalidos() {
		// Lista de números corretos porém com DDD's não existentes
		String[] dddInvalidos = { "(10)4002-8922", "2040028922", "29 940028922", "80 4002 8922", "50 4002-8922" };

		for (String valor : dddInvalidos) {
			String dddFormatado = valor.replaceAll(RegexModel.FORMATATELEFONE, "").substring(0, 2);

			Exception e = Assert.assertThrows("DDD sendo passado incorretamente deve gerar exception",
					IllegalArgumentException.class, () -> tel.setDdd(dddFormatado));

			assertThat(e.getMessage(), equalTo("O DDD " + dddFormatado + " não é válido."));
		}
	}

	@Test(expected = Test.None.class)
	public void setTipoTelefone_nao_deve_gerar_exception_recebendo_tipos_corretos_sobre_telefone(){
		Telefone telefone1 = new Telefone();  
		Telefone telefone2 = new Telefone();
		Telefone telefone3 = new Telefone();
		
		telefone1.setNumeroTelefone("11 4002-8922");
		telefone1.setTipoTelefone(TipoTelefone.RESIDENCIAL);	
		
		telefone2.setNumeroTelefone("(11) 94002-8922");
		telefone2.setTipoTelefone(TipoTelefone.CELULAR);

		telefone3.setNumeroTelefone("1140028922");
		telefone3.setTipoTelefone(TipoTelefone.COMERCIAL);		
	}
	
	@Test
	public void setTipoTelefone_deve_gerar_exception_recebendo_tipos_incorretos_sobre_telefone(){
		List<Exception> nu = new ArrayList<>();
		Telefone telefone1 = new Telefone();  
		Telefone telefone2 = new Telefone();
		
		telefone1.setNumeroTelefone("11 4002-8922");
		nu.add(Assert.assertThrows(IllegalArgumentException.class, () -> telefone1.setTipoTelefone(TipoTelefone.CELULAR)));	
		
		telefone2.setNumeroTelefone("(11) 94002-8922");
		nu.add(Assert.assertThrows(IllegalArgumentException.class, () -> telefone2.setTipoTelefone(TipoTelefone.RESIDENCIAL)));
		
		assertThat(nu.size(), equalTo(2));
		assertThat(nu.get(0).getMessage(), equalTo("O Tipo de telefone inserido não condiz com o telefone de contato informado!"));
		assertThat(nu.get(1).getMessage(), equalTo("O Tipo de telefone inserido não condiz com o telefone de contato informado!"));
	}

	@Test
	public void setTipoTelefone_deve_gerar_exception_se_numero_telefone_estiver_nulo() {
		Exception nu = Assert.assertThrows("não deve existir um tipo para um número de telefone nulo",
				NullPointerException.class, () -> tel.setTipoTelefone(TipoTelefone.CELULAR));

		assertThat(nu.getMessage(), equalTo("Você não pode setar um tipo para um telefone se o mesmo estiver vazio"));
	}
	
	@Test
	public void teste_toString() {
		Assert.assertNotNull("Não deveria estar nulo", new Telefone("1140028922").toString());
	}
	
	
}
