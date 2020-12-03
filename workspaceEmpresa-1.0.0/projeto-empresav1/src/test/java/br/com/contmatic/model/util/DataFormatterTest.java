package br.com.contmatic.model.util;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.contmatic.util.DataFormatter;

public class DataFormatterTest {

	private SimpleDateFormat sdf;

	private DataFormatter df;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println(" --- Início dos Testes da Classe Util DataFormatter --- \n");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("\n --- Fim dos Testes da Classe Util DataFormatter --- \n");
	}

	@Before
	public void setUp() throws Exception {
		this.df = new DataFormatter("01/01/1970");
		this.sdf = df.getSdf();
	}

	@After
	public void tearDown() throws Exception {
		this.df = null;
		this.sdf = null;
	}

	@Test(expected = Test.None.class)
	public void deve_aceitar_data_formatada_corretamente() {
		String data = "04/02/2000";
		Date dataFormatada = new DataFormatter(data).getData();
		assertThat(sdf.format(dataFormatada), equalTo(data));
	}

	@Test(expected = Test.None.class)
	public void deve_aceitar_e_formatar_data_sem_formatacao() {
		String data = "04022000";
		Date dataFormatada = new DataFormatter(data).getData();
		assertThat(sdf.format(dataFormatada), equalTo(retornaDataFormatada(data)));
	}

	@Test(expected = Test.None.class)
	public void deve_aceitar_datas_mal_formatadas_e_retornar_elas_formatadas() {
		String[] datas = { "04 02 2000", "04.02.2000", "04-02-2000" };
		for (String data : datas) {
			Date dataFormatada = new DataFormatter(data).getData();
			assertThat(sdf.format(dataFormatada), equalTo(retornaDataFormatada(data)));
		}
	}

	@Test
	public void nao_deve_aceitar_data_nula() {
		Exception nu = assertThrows("Nulo não deve ser aceito como data", NullPointerException.class,
				() -> new DataFormatter(null));
		assertThat(nu.getMessage(), equalTo("O campo data da classe DataFormatter não pode ser nulo."));
	}

	@Test
	public void nao_deve_aceitar_datas_com_tamanho_invalido() {
		String[] datasInvalidas = { "040200", "1981999", "08/8/2000", "100200300" };
		for (String data : datasInvalidas) {
			Exception e = assertThrows("Datas fora do tamanho padrão não devem ser aceitas como data",
					IllegalArgumentException.class, () -> new DataFormatter(data));
			assertThat(e.getMessage(), startsWith("Este tamaho para a data " + data + " não pode ser aceito."));
		}
	}

	@Test
	public void nao_deve_aceitar_datas_separadas_por_caracteres_nao_permitidos() {
		String[] datasInvalidas = { "04A02A2000", "31*11*2011", "09\\08\\2000", "10_02_1200" };
		for (String data : datasInvalidas) {
			Exception e = assertThrows("Datas não devem ser separadas por qualquer tipo de caractere",
					IllegalArgumentException.class, () -> new DataFormatter(data));
			assertThat(e.getMessage(), startsWith("Este formato de data " + data + " não pode ser aceito"));
		}
	}
	
	@Test
	public void nao_deve_aceitar_datas_fora_do_padrao_estabelecido() {
		String[] datasInvalidas = { "4/2/2000", "4/12/200", "8/8/2000", "10/2/200" };
		for (String data : datasInvalidas) {
			Exception e = assertThrows("Datas fora do padrão não devem ser aceitas como data",
					IllegalArgumentException.class, () -> new DataFormatter(data));
			assertThat(e.getMessage(), startsWith("A data " + data + " não pôde ser formatada para uma data padrão."));
		}
	}

	@Test
	public void nao_deve_aceitar_nada_que_nao_for_data() {
		String[] datasInvalidas = { "dd-MM-yyyy", "ddMMyyyy", "ab/bc/abcd" };
		for (String data : datasInvalidas) {
			Exception e = assertThrows("Textos que não forem datas não devem ser aceitos",
					IllegalArgumentException.class, () -> new DataFormatter(data));
			assertThat(e.getMessage(), startsWith("A data " + data + " não pôde ser formatada para uma data padrão"));
		}
	}

	private String retornaDataFormatada(String data) {
		String dataLimpa = data.replaceAll("\\D", "");
		try {
			return new StringBuilder(10).append(dataLimpa.substring(0, 2)).append("/").append(dataLimpa.substring(2, 4))
					.append("/").append(dataLimpa.substring(4, 8)).toString();
		} catch (IndexOutOfBoundsException in) {
			throw new IndexOutOfBoundsException(in.getLocalizedMessage());
		}
	}
}
