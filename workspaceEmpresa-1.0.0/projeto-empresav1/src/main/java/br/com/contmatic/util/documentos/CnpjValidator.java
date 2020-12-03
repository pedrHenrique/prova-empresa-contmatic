package br.com.contmatic.util.documentos;

import static br.com.contmatic.util.documentos.DocumentoValidator.cnpjValido;
import static br.com.contmatic.util.documentos.DocumentoValidator.digitos;
import static br.com.contmatic.util.documentos.DocumentoValidator.verificador;

public final class CnpjValidator {

	protected static final int[] PESOS_CNPJ = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

	public static final int TAMANHO_CNPJ = 14;
	
	public static void validarCnpj(String cnpj) {
		if (!cnpjValido(cnpj)) {
			throw new IllegalArgumentException(
					"O CNPJ que você inseriu não é válido. Por favor, insira o CNPJ sem nenhuma formatacao");
		}
	}
	
	public static String geraCnpjAleatorio() {
		String digitos = digitos(TAMANHO_CNPJ - 2);
		String verificador1 = verificador(digitos, PESOS_CNPJ);
		String verificador2 = verificador(digitos + verificador1, PESOS_CNPJ);
		return digitos + verificador1 + verificador2;
	}

	private CnpjValidator() {

	}
}
