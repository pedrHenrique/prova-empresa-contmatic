package br.com.contmatic.util.v1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public final class DataFormatter {

	private static DataFormatter instance;

	private static final Date DATALIMITE = retornaDataLimiteParaCadastro();

	private static final int TAMANHO_DATA_SEM_SEPARADOR = 8;

	private static final int TAMANHO_DATA_COM_SEPARADOR = 10;

	private static final List<String> LISTA_SEPARADORES = Arrays.asList(".", "-", " ", "/");

	public static final String PADRAO_DATA = "dd/MM/yyyy";

	private final SimpleDateFormat formatoDataPadrao = new SimpleDateFormat(PADRAO_DATA);

	private DataFormatter() {

	}

	public static DataFormatter getDataFormatterInstance() {
		if (instance == null) {
			instance = new DataFormatter();
		}
		return instance;
	}

	public SimpleDateFormat getFormatoDataPadrao() {
		return formatoDataPadrao;
	}

	public Date formataDataParaPadrao(Date data) {
		validaNulo(data);
		return this.retornaDataFormatada(data);
	}

	private Date retornaDataFormatada(Date data) {
		try {
			return formataDataTipoDate(data);
		} catch (ParseException pe) {
			throw new IllegalArgumentException("A data " + data + " não pôde ser convertida para uma data padrão.\n" + pe.getLocalizedMessage());
		}
	}

	private Date formataDataTipoDate(Date data) throws ParseException {
		return formatoDataPadrao.parse(formatoDataPadrao.format(data));
	}

	public Date formataDataParaPadrao(String data) {
		validaNulo(data);
		validaTamanho(data);
		return this.retornaDataFormatada(data);
	}

	private Date retornaDataFormatada(String data) {
		try {
			return formataData(data);
		} catch (ParseException pe) {
			throw new IllegalArgumentException("A data " + data + " não pôde ser formatada para uma data padrão.\n" + pe.getLocalizedMessage());
		}
	}

	private Date formataData(String data) throws ParseException {
		if (dataPossuiCaracterSeparador(data)) {
			return this.formataDataComSeparador(data);
		} else {
			return this.formataDataSemSeparador(data);
		}
	}

	private static boolean dataPossuiCaracterSeparador(String data) {
		return data.length() == TAMANHO_DATA_COM_SEPARADOR;
	}

	private Date formataDataComSeparador(String data) throws ParseException {
		for (String separador : LISTA_SEPARADORES) {
			if (data.contains(separador)) {
				return this.converteDataUtilizandoSeparador(data, separador);
			}
		}
		throw new IllegalArgumentException("Este formato de data " + data + " não pôde ser aceito.\n" + "Tente informar a data como dd/mm/aaaa.");
	}

	private Date converteDataUtilizandoSeparador(String data, String sep) throws ParseException {
		return formatoDataPadrao.parse(formatoDataPadrao.format(new SimpleDateFormat("dd" + sep + "MM" + sep + "yyyy").parse(data)));
	}

	private Date formataDataSemSeparador(String data) throws ParseException {
		return this.converteADataSemAPresencaDeUmSeparador(data);
	}

	private Date converteADataSemAPresencaDeUmSeparador(String data) throws ParseException {
		return formatoDataPadrao.parse(formatoDataPadrao.format(new SimpleDateFormat("ddMMyyyy").parse(data)));
	}

	public static void verificaSeDataEstaNoPassado(Date data) {
		validaNulo(data);
		if (isDataNoFuturo(data)) {
			throw new IllegalArgumentException("Datas no futuro não podem ser aceitas.");
		}
	}

	public static void verificaSeDataEMuitoAntiga(Date data) {
		validaNulo(data);
		if (isDataAbaixoDe1900(data)) {
			throw new IllegalArgumentException("A data informada é muito antiga para ser aceita. Por favor, informe uma data acima do ano de 1900");
		}
	}

	private static boolean isDataAbaixoDe1900(Date data) {
		return data.compareTo(DATALIMITE) <= 0;
	}

	private static boolean isDataNoFuturo(Date data) {
		return data.compareTo(new Date()) > 0;
	}

	private static void validaNulo(Object data) {
		if (data == null) {
			throw new IllegalArgumentException("Você não pode informar uma data nula para ser validada.");
		}
	}

	private static void validaTamanho(String data) {
		if (data.length() != TAMANHO_DATA_SEM_SEPARADOR && data.length() != TAMANHO_DATA_COM_SEPARADOR) {
			throw new IllegalArgumentException("Este tamaho para a data " + data + " não pode ser aceito.\n"
					+ "Tente informar a data como dd/mm/aaaa.");
		}
	}

	private static Date retornaDataLimiteParaCadastro() {
		Calendar cal = Calendar.getInstance();
		cal.set(1900, Calendar.JANUARY, 1);
		return cal.getTime();
	}
}
