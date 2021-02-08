package br.com.contmatic.util.v1.validator;

public final class ValidatorUtils {
	
	protected static String retornaMensagemExceptionPadronizada(Class<?> classe, String campo, String contexto) {
		return new StringBuilder("O campo " + campo + " da classe " + classe.getSimpleName() + " " + contexto).toString();
	}
	
	protected static void validaClasseCampo(Class<?> classe, String campo) {
		verificaNuloClasse(classe);
		verificaInstanciaClasse(classe);
		verificaCampoInformado(classe, campo);
	}
	
	protected static void verificaCampoInformado(Class<?> classe, String campo) {
		try {
			verificaSeCampoPassadoExisteNaClasse(classe, campo);
		} catch (NoSuchFieldException nosu) {
			throw new IllegalArgumentException("O campo " + campo + " não foi encontrado na classe " + classe.getSimpleName() + ".\n"
							+ "Por favor, digite o nome do campo exatamente como escrito na classe.");
		} catch (NullPointerException nu) {
			throw new IllegalArgumentException("Um campo da classe " + classe + " foi informado como nulo.");
		}
	}

	protected static void verificaSeCampoPassadoExisteNaClasse(Class<?> classe, String campo) throws NoSuchFieldException {
		classe.getDeclaredField(campo);
	}

	protected static void verificaInstanciaClasse(Class<?> classe) {
		if (classe.isInstance(Object.class)) {
			throw new IllegalArgumentException("Classe Object não deve ser validada. Por favor, insira uma classe diferente.");
		}
	}

	protected static void verificaNuloClasse(Class<?> classe) {
		if (classe == null) {
			throw new IllegalArgumentException("Você não pode validar uma classe passando a mesma nula.");
		}
	}
	
	private ValidatorUtils() {
		
	}

}
