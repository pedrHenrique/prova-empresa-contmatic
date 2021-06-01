package br.com.contmatic.util.v1.validator;

import static br.com.contmatic.util.v1.validator.ClassValidator.retornaMensagemExceptionPadronizada;
import static br.com.contmatic.util.v1.validator.ClassValidator.validaClasseCampo;

public final class NumericValidator {

	/**
	 * Compara o tamanho com os valores mínimo e máximo passados.
	 * 
	 * <p>Este método utiliza a seguinte lógica.<p>
	 * 
	 * <code> if (tamanho < tamanhoMin || tamanho > tamanhoMax) { throw new @IllegalArgumentException } </code>
	 * 
	 * @throws IllegalArgumentException Caso o tamanho seja maior que o tamanho máximo. Caso seja menor que o tamanho mínimo, ou se caso algum 
	 * dos valores como classe ou campo tenham sido preenchidos de forma incorreta.
	 *
	 * @param classe - A classe no qual o atributo pertence.
	 * @param campo - O <b>nome do campo</b> o qual pertence a classe.
	 * @param tamanho - O tamanho a ser comparado pelos demais tamanhos.
	 * @param tamanhoMin - O tamanho mínimo a ser comparado.
	 * @param tamanhoMax - O tamanho máximo a ser comparado.
	 */
	public static void validaTamanho(Class<?> classe, String campo, int tamanho, int tamanhoMin, int tamanhoMax) {
		validaClasseCampo(classe, campo);
		validaTamanhoComparandoMinEMax(classe, campo, tamanho, tamanhoMin, tamanhoMax);
	}
	
	
	/**
	 * Verifica se o tamanho passado é diferente do tamanho esperado.
	 * 
	 * <p>Este método utiliza a seguinte lógica.<p>
	 * 
	 * <code> if (tamanho != tamanhoEsperado) { throw new @IllegalArgumentException } </code>
	 * 
	 * @throws IllegalArgumentException Caso o tamanho seja diferente do tamanho esperado, ou se caso algum 
	 * dos valores como classe ou campo tenham sido preenchidos de forma incorreta.
	 *
	 * @param classe - A classe no qual o atributo pertence.
	 * @param campo - O <b>nome do campo</b> o qual pertence a classe.
	 * @param tamanho - O tamanho a ser comparado.
	 * @param tamanhoEsperado - O tamanho esperado que a variável tamanho deve possuir.
	 */
	public static void validaTamanho(Class<?> classe, String campo, int tamanho, int tamanhoEsperado) {
		validaClasseCampo(classe, campo);
		validaTamanhoEsperado(classe, campo, tamanho, tamanhoEsperado);
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
	
	private NumericValidator() {

	}
}
