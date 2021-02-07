package br.com.contmatic.util.validator.documentos;

import static br.com.contmatic.util.validator.documentos.CnpjValidator.PESOS_CNPJ;
import static br.com.contmatic.util.validator.documentos.CnpjValidator.TAMANHO_CNPJ;
import static br.com.contmatic.util.validator.documentos.CpfValidator.PESOS_CPF;
import static br.com.contmatic.util.validator.documentos.CpfValidator.TAMANHO_CPF;

import java.util.Random;

public final class DocumentoValidator {
	
	private static Random random = new Random();
	
	public static boolean cnpjValido(final String cnpj) {
		validaNuloCnpj(cnpj);
		validaTamanhoCnpj(cnpj);
		validaFormatoCnpj(cnpj);
		return validaDigitoVerificador(cnpj, TAMANHO_CNPJ, PESOS_CNPJ);
	}
	
	public static boolean cpfValido(final String cpf) {
		validaNuloCpf(cpf);
		validaTamanhoCpf(cpf);
		validaFormatoCpf(cpf);
		return validaDigitoVerificador(cpf, TAMANHO_CPF, PESOS_CPF);
	}
	
	public static String digitos(int quantidade) {
		StringBuilder digitos = new StringBuilder();
		for (int contador = 0; contador < quantidade; contador++) {
			digitos.append(random.nextInt(10));
		}
		return digitos.toString();
	}
	
	public static String verificador(String digitos, int[] pesos) {
		int soma = 0;
		int qtdPesos = pesos.length;
		int qtdDigitos = digitos.length();
		soma = validaDigitos(digitos, pesos, soma, qtdPesos, qtdDigitos);
		return String.valueOf(soma > 9 ? 0 : soma);
	}

	private static int validaDigitos(String digitos, int[] pesos, int soma, int qtdPesos, int qtdDigitos) {
		for (int posicao = qtdDigitos - 1; posicao >= 0; posicao--) {
			int digito = Character.getNumericValue(digitos.charAt(posicao));
			soma += digito * pesos[qtdPesos - qtdDigitos + posicao];
		}
		soma = 11 - soma % 11;
		return soma;
	}

	private static boolean validaDigitoVerificador(final String cnpj, int tamanho, int[] pesos) {
		String digitos = cnpj.substring(0, tamanho - 2);
		String verificador1 = verificador(digitos, pesos);
		String verificador2 = verificador(digitos + verificador1, pesos);
		return (digitos + verificador1 + verificador2).equals(cnpj);
	}

	private static void validaFormatoCnpj(final String cnpj) {
		if (cnpj.matches(cnpj.charAt(0) + "{" + TAMANHO_CNPJ + "}")) {
			throw new IllegalArgumentException("O campo CNPJ da classe Empresa foi informado com o formato errado.\n"
					+ "Por favor, insira o CNPJ sem formatação.");
		}
	}
	
	private static void validaFormatoCpf(final String cpf) {
		if (cpf.matches(cpf.charAt(0) + "{" + TAMANHO_CPF + "}")) {
			throw new IllegalArgumentException("O campo CPF da classe Funcionario foi informado com o formato errado.\n"
					+ "Por favor, insira o CPF sem formatação.");
		}
	}
	
	private static void validaTamanhoCnpj(final String cnpj) {
		if (cnpj.length() != TAMANHO_CNPJ) {
			throw new IllegalArgumentException("O campo CNPJ da classe Empresa foi informado com o tamanho errado.\n"
					+ "Por favor, insira apenas os dígitos do CNPJ.");
		}
	}

	private static void validaTamanhoCpf(final String cpf) {
		if (cpf.length() != TAMANHO_CPF) {
			throw new IllegalArgumentException("O campo CPF da classe Funcionario foi informado com o tamanho errado.\n"
					+ "Por favor, insira apenas os dígitos do CPF.");
		}
	}
	
	private static void validaNuloCnpj(final String cnpj) {
		if (cnpj == null) {
			throw new IllegalArgumentException("O campo CNPJ da classe Empresa não pode ser nulo.");
		}
	}

	private static void validaNuloCpf(final String cpf) {
		if (cpf == null) {
			throw new IllegalArgumentException("O campo cpf da classe Funcionario não pode ser nulo.");
		}
	}
	
	protected DocumentoValidator() {
		
	}

}
