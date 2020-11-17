package br.com.contmatic.empresa.v1.util;

public final class RegexModel {
	
	private RegexModel() {
		
	}
	
	/**
	 * Permite nomes que não comecem com letras/under_score/espaços em brancos. <p>
	 * Permite letras, numeros, acentos, ponto, e & comercial.
	 */
	public static final String NOMEFANTASIA = "[^\\s\\W_0-9][\\wáéíóúÁÉÍÓÚâêîôûÂÊÎÔÛàèìòùÀÈÌÒÙãõÃÕçÇ \\.&]*";
	
	/**
	 * Mesma coisa que: "xx.xxx.xxx/xxxx-xx" ou "xxxxxxxxxxxxxx"
	 */
	public static final String CNPJ = "\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}\\-\\d{2}|\\d{14}";
	
	/** Permite letras e numeros no primeiro grupo, seguido de um <b>"@"</b> obrigatório, permitindo o restante do email após o arroba.
	 * Seguido de um ponto <b>'.'</b>, tendo que obrigatóriamente especificar um dominio a partir do grupo abaixo, finalizando com um incremento
	 * opcional de a até z.. */
	public static final String EMAIL = "([\\w_]+@[\\w]+)\\.(com|org|net|br)+[\\.a-z]*";
	
	/** Permite a passagem de um número tanto de celular quanto de telefone, podendo já estar corretamente formatado de diversas formas. */
	public static final String TELEFONECELULAR = "\\(\\d{2}\\)\\d{4,5}\\-\\d{4}|\\d{10,11}|\\(\\d{2}\\)\\s\\d{4,5}\\-\\d{4}|\\d{2}\\s\\d{4,5}[-]*[ ]?\\d{4}";
	
	public static final String FORMATATELEFONE = "[\\D\\s]*";

}
