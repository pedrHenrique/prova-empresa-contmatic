package br.com.contmatic.util.v1.validator.documentos;

import static br.com.contmatic.util.v1.validator.documentos.DocumentoValidator.cpfValido;
import static br.com.contmatic.util.v1.validator.documentos.DocumentoValidator.digitos;
import static br.com.contmatic.util.v1.validator.documentos.DocumentoValidator.verificador;

public final class CpfValidator {
	
	public static final int TAMANHO_CPF = 11;

	protected static final int[] PESOS_CPF = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
	
	public static void validarCpf(String cpf) {
		if (!cpfValido(cpf)) {
			throw new IllegalArgumentException("O CPF que você inseriu não é válido. Por favor, insira o CPF sem nenhuma formatacao");
		}
	}
	
	public static String geraCpfAleatorio() {
		String digitos = digitos(TAMANHO_CPF - 2);
		String verificador1 = verificador(digitos, PESOS_CPF);
		String verificador2 = verificador(digitos + verificador1, PESOS_CPF);
		return digitos + verificador1 + verificador2;
	}

	public static String formataCpf(String cpf) {
		return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
	}

	private CpfValidator() {
		
	}
}
