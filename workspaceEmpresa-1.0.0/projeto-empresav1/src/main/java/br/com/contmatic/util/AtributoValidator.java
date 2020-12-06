package br.com.contmatic.util;

import static java.lang.Character.isDigit;

import java.util.Arrays;
import java.util.List;

public final class AtributoValidator {
	
	private static final List<String> SIMBOLOS_NAO_PERMITIDOS = Arrays.asList("!", "@", "#", "$", "%", "&", "-", "¨", "*", "(", ")",
			  																  "-", "_", "+", "=", "-", "/", ".", ",", "|", "?", ";");

	private static String retornaMensagemExceptionPadronizada(Class<?> classe, String campo, String contexto) {
		return new StringBuilder("O campo " + campo + " da classe " + classe.getSimpleName() + " " + contexto).toString();
	}

	public static void validaNulo(Class<?> classe, String campo, Object valor) {
		validaClasseCampo(classe, campo);
		validaValorNulo(classe, campo, valor);
	}

	public static void validaTamanho(Class<?> classe, String campo, int tamanho, int tamanhoMin, int tamanhoMax) {
		validaClasseCampo(classe, campo);
		validaTamanhoComparandoMinEMax(classe, campo, tamanho, tamanhoMin, tamanhoMax);
	}
	
	public static void validaTamanho(Class<?> classe, String campo, int tamanho, int tamanhoEsperado) {
		validaClasseCampo(classe, campo);
		validaTamanhoEsperado(classe, campo, tamanho, tamanhoEsperado);
	}

	public static void validaEspacamento(Class<?> classe, String campo, String valor, int tamanhoMin) {
		validaClasseCampo(classe, campo);
		validaEspacamentoComBaseNoTamanhoMinimo(classe, campo, valor, tamanhoMin);
	}

	public static void validaNomeSimples(Class<?> classe, String campo, String nome) {
		validaClasseCampo(classe, campo);
		if (nomePossuiDigitos(nome)) {
			throw new IllegalArgumentException(retornaMensagemExceptionPadronizada(classe, campo, "não pode possuir números."));
		} else if (nomePossuiSimbolos(nome)) {
			throw new IllegalArgumentException(
					retornaMensagemExceptionPadronizada(classe, campo, "não pode possuir caracteres especiais."));
		}
	}
	
	public static void validaNomeSimbolos(Class<?> classe, String campo, String nome) {
		validaClasseCampo(classe, campo);
		if (nomePossuiSimbolos(nome)) {
			throw new IllegalArgumentException(
					retornaMensagemExceptionPadronizada(classe, campo, "não pode possuir caracteres especiais."));
		}
	}
	
	public static void validaNomeDigitos(Class<?> classe, String campo, String nome) {
		validaClasseCampo(classe, campo);
		if (nomePossuiDigitos(nome)) {
			throw new IllegalArgumentException(
					retornaMensagemExceptionPadronizada(classe, campo, "não pode possuir dígitos."));
		}
	}
	
	public static void validaCampoDigitos(Class<?> classe, String campo, String valor) {
		validaClasseCampo(classe, campo);
		for (int i = 0; i < valor.length(); ++i) {
			if (!Character.isDigit(valor.charAt(i))) {
				throw new IllegalArgumentException(retornaMensagemExceptionPadronizada(classe, campo, "só pode conter números."));
			}
		}
	}
	
	private static void validaValorNulo(Class<?> classe, String campo, Object valor) {
		if (valor == null) {
			throw new NullPointerException(retornaMensagemExceptionPadronizada(classe, campo, "não pode ser nulo."));
		}
	}
	
	protected static boolean nomePossuiDigitos(String nome) {
		for (int i = 0; i < nome.length(); ++i) {
			if (isDigit(nome.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	protected static boolean nomePossuiSimbolos(String nome) {
		return SIMBOLOS_NAO_PERMITIDOS.stream().anyMatch(simbolo -> nomeContemEsteSimbolo(nome, simbolo));
	}

	protected static boolean nomeContemEsteSimbolo(String nome, String simbolo) {
		return nome.contains(simbolo);
	}

	public static String retornaValorSemEspacamento(String valor) {
		return valor.trim();
	}
	
	private static void validaTamanhoComparandoMinEMax(Class<?> classe, String campo, int tamanho, int tamanhoMin, int tamanhoMax) {
		if (tamanho < tamanhoMin || tamanho > tamanhoMax) {
			throw new IllegalArgumentException(retornaMensagemExceptionPadronizada(classe, campo,
				"não pode ter esse tamanho. Tamanho de " + campo + " precisar ser entre " + tamanhoMin + " e " + tamanhoMax + "."));
		}
	}

	private static void validaTamanhoEsperado(Class<?> classe, String campo, int tamanho, int tamanhoEsperado) {
		if (tamanho != tamanhoEsperado) {
			throw new IllegalArgumentException(retornaMensagemExceptionPadronizada(classe, campo,
				"não pode ter esse tamanho. Tamanho de " + campo + " precisar ser igual a " + tamanhoEsperado + "."));
		}
	}

	private static void validaEspacamentoComBaseNoTamanhoMinimo(Class<?> classe, String campo, String valor, int tamanhoMin) {
		if (retornaValorSemEspacamento(valor).length() < tamanhoMin) {
			throw new IllegalArgumentException(retornaMensagemExceptionPadronizada(classe, campo, "foi informado em branco, " +
			"ou com uma quantidade de espaços em brancos excessivos."));
		}
	}
	
	private static void validaClasseCampo(Class<?> classe, String campo) {
		verificaNuloClasse(classe);
		verificaInstanciaClasse(classe);
		verificaCampoInformado(classe, campo);
	}
	
	private static void verificaCampoInformado(Class<?> classe, String campo) {
		try {
			classe.getDeclaredField(campo);
		} catch (NoSuchFieldException nosu) {
			throw new IllegalArgumentException(
					"O campo " + campo + " não foi encontrado na classe " + classe.getSimpleName() + ".\n"
							+ "Por favor, digite o nome do campo exatamente como escrito na classe.");
		} catch (NullPointerException nu) {
			throw new NullPointerException("Um campo da classe " + classe + " foi informado como nulo.");
		}
	}

	private static void verificaInstanciaClasse(Class<?> classe) {
		if (classe.isInstance(Object.class)) {
			throw new IllegalArgumentException(
					"Classe Object não deve ser validada. Por favor, insira uma classe diferente.");
		}
	}

	private static void verificaNuloClasse(Class<?> classe) {
		if (classe == null) {
			throw new NullPointerException("Você não pode validar uma classe passando a mesma nula.");
		}
	}
	
	private AtributoValidator() {

	}
}
