package br.com.contmatic.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public final class DataFormatter {
	
	private static DataFormatter instance;

    public static final String PADRAO_DATA = "dd/MM/yyyy";

	private static final int DATA_TAMANHO_MINIMO = 8;

	private static final int DATA_TAMANHO_MAXIMO = 10;

	private static final List<String> SEPARADORES_DATAS = Arrays.asList(".", "-", " ");

    private final SimpleDateFormat sdf = new SimpleDateFormat(PADRAO_DATA);
    
    private DataFormatter() {
    	
    }
    
    public static DataFormatter getFormatterInstance() {
    	if (instance == null) {
    		instance = new DataFormatter();
    	}
    	return instance;
    }
    
    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public Date formataData(String data) {
		validaNulo(data);
		validaTamanho(data);
		Date dataFormatada;
        try {
			dataFormatada = (data.length() == DATA_TAMANHO_MAXIMO) ? formataDataComposta(data) : formataDataSimples(data);
        } catch (ParseException pe) {
            throw new IllegalArgumentException(
                    "A data " + data + " não pôde ser formatada para uma data padrão.\n" + pe.getLocalizedMessage());
		}      
		return dataFormatada;  
    }
    
    public Date formataData(Date data) {
		validaNulo(data);
		try {
			data = formataDataTipoDate(data);
		} catch (ParseException pe) {
					throw new IllegalArgumentException(
							"A data " + data + " não pôde ser formatada para uma data padrão.\n" + pe.getLocalizedMessage());
		}
		return data;
    }
    
    public static void verificaSeDataEstaNoPassado(Date data) {    	
    	if (data.compareTo(new Date()) > 0) {
    		throw new IllegalArgumentException("Datas no futuro não podem ser aceitas.");
    	}
    }

	private Date formataDataSimples(String data) throws ParseException {
		Date dataAux = new SimpleDateFormat("ddMMyyyy").parse(data);
		return sdf.parse(sdf.format(dataAux));
	}

	private Date formataDataComposta(String data) throws ParseException {
		if (data.contains("/")) {
		    return sdf.parse(data);
		} else { 
			for (String sep : SEPARADORES_DATAS) {
                if (data.contains(sep)) {
                    Date dataAux = new SimpleDateFormat("dd" + sep + "MM" + sep + "yyyy").parse(data);
                    return sdf.parse(sdf.format(dataAux));
                }
			}
		}
		throw new IllegalArgumentException("Este formato de data " + data + " não pode ser aceito.\n" 
                + "Tente informar a data como dd/mm/aaaa.");
	}

	private Date formataDataTipoDate(Date data) throws ParseException {
		String dataFormatada = sdf.format(data);			
		data = sdf.parse(dataFormatada);
		return data;
	}
	
	private static void validaNulo(Object data) {
		if (data == null) {
			throw new NullPointerException("Você não pode informar uma data nula para ser formatada.");
		}
	}
	
	private static void validaTamanho(String data) {
		if (data.length() != DATA_TAMANHO_MINIMO && data.length() != DATA_TAMANHO_MAXIMO) { 
            throw new IllegalArgumentException("Este tamaho para a data " + data + " não pode ser aceito.\n"
                    + "Tente informar a data como dd/mm/aaaa.");
        }
	}
}
