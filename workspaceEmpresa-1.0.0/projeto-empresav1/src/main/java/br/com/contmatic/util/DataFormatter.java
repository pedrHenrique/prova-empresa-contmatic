package br.com.contmatic.util;

import static br.com.contmatic.util.AtributoValidator.validaNulo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public final class DataFormatter {

    public static final String PADRAO_DATA = "dd/MM/yyyy";

	private static final int DATA_TAMANHO_MINIMO = 8;

	private static final int DATA_TAMANHO_MAXIMO = 10;

	private static final List<String> SEPARADORES_DATAS = Arrays.asList(".", "-", " ");

    private final Date data;

    private final SimpleDateFormat sdf = new SimpleDateFormat(PADRAO_DATA);

    public DataFormatter(String data) {
        this.data = formataData(data);
        this.sdf.setLenient(false);
    }

    private static void validaData(String data) {
        validaNulo(DataFormatter.class, "data", data);
        validaTamanho(data);
    }

    public Date formataData(String data) {
        validaData(data);
        try {
        	if (data.length() == DATA_TAMANHO_MAXIMO) {
        		return formataDataPossuindoSimbolos(data);
        	} else {
                return formataDataNaoPossuindoSimbolos(data);              
            }
        } catch (ParseException pe) {
            throw new IllegalArgumentException(
                    "A data " + data + " não pôde ser formatada para uma data padrão.\n" + pe.getLocalizedMessage());
        }        
    }
    
    public static void verificaSeDataEstaNoPassado(Date data) {    	
    	if (data.compareTo(new Date()) >= 0) {
    		throw new IllegalArgumentException("Datas no futuro não podem ser aceitas.");
    	}
    }

	private Date formataDataNaoPossuindoSimbolos(String data) throws ParseException {
		Date dataAux = new SimpleDateFormat("ddMMyyyy").parse(data);
		return sdf.parse(sdf.format(dataAux));
	}

	private Date formataDataPossuindoSimbolos(String data) throws ParseException {
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

    public Date getData() {
        return data;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

	private static void validaTamanho(String data) {
		if (data.length() != DATA_TAMANHO_MINIMO && data.length() != DATA_TAMANHO_MAXIMO) { 
            throw new IllegalArgumentException("Este tamaho para a data " + data + " não pode ser aceito.\n"
                    + "Tente informar a data como dd/mm/aaaa.");
        }
	}
}
