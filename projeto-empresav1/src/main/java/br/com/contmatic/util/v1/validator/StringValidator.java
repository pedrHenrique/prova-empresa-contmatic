package br.com.contmatic.util.v1.validator;

import static br.com.contmatic.util.v1.validator.ClassValidator.retornaMensagemExceptionPadronizada;
import static br.com.contmatic.util.v1.validator.ClassValidator.validaClasseCampo;
import static java.lang.Character.isDigit;

import java.util.Arrays;
import java.util.List;

public final class StringValidator {
	
	private static final List<String> SIMBOLOS_NAO_PERMITIDOS = Arrays.asList("!", "@", "#", "$", "%", "&", "-", "¨", "*", "(", ")",
			  "-", "_", "+", "=", "-", "/", ".", ",", "|", "?", ";");
	
	public static void validaNulo(Class<?> classe, String campo, Object valor) {
		validaClasseCampo(classe, campo);
		validaValorNulo(classe, campo, valor);
	}
	
	/**
	 * Verifica o tamanho do campo após remover os espaços vazios do começo e do final da String.
	 * Comparando o tamanho obtido com um valor mínimo passado, e também verificando se o campo ficou vazio
	 * após a remoção dos espaçamentos.
	 *
	 * @throws IllegalArgumentException Caso o campo após ser tratado estiver vazio. Caso o 
	 * tamanho do campo fique inferior ao tamanho mínimo. Ou se caso algum 
	 * dos valores como classe ou campo tenham sido preenchidos de forma incorreta.
	 *
	 * @param classe - A classe no qual o atributo pertence.
	 * @param campo - O <b>nome do campo</b> o qual pertence a classe.
	 * @param valor - O valor do campo a ser validado.
	 * @param tamanhoMin - O tamanho mínimo a ser comparado após a remoção dos espaçamentos.
	 */
	public static void validaEspacamento(Class<?> classe, String campo, String valor, int tamanhoMin) {
		validaClasseCampo(classe, campo);
		validaEspacamentoComBaseNoTamanhoMinimo(classe, campo, valor, tamanhoMin);
	}

	/**
	 * Valida se o campo é simples.<br>
	 * Verifica se o campo possui <i>dígitos</i> ou se possuí <i>caracteres especiais</i>.
	 * 
	 *<h3> Lista de caracteres especiais não permitidos:</h3>
	 *<pre>!, @, #, $, %, &, -, ¨, *, (, ), -, _, +, =, -, /, ., ,, |, ?, ;</pre><p>
	 *
	 * @throws IllegalArgumentException Caso o campo passado possua dígitos/símbolos. Ou se caso algum 
	 * dos valores como classe ou campo tenham sido preenchidos de forma incorreta.
	 * 
	 * @param classe - A classe no qual o atributo pertence.
	 * @param campo - O <b>nome do campo</b> o qual pertence a classe.
	 * @param valor - O valor do campo a ser validado.
	 */
	public static void validaNomeSimples(Class<?> classe, String campo, String valor) {
		validaClasseCampo(classe, campo);
		validaSeCampoPossuiDigitos(classe, campo, valor); 
		validarSeNomePossuiSimbolos(classe, campo, valor);
	}
	
	/**
	 * Verifica se o campo possui <i>caracteres especiais</i>.
	 * 
	 *<h3> Lista de caracteres especiais não permitidos:</h3>
	 *<pre>!, @, #, $, %, &, -, ¨, *, (, ), -, _, +, =, -, /, ., ,, |, ?, ;</pre><p>
	 *
	 * @throws IllegalArgumentException Caso o campo passado possua símbolos. Ou se caso algum 
	 * dos valores como classe ou campo tenham sido preenchidos de forma incorreta.
	 *
	 * @param classe - A classe no qual o atributo pertence.
	 * @param campo - O <b>nome do campo</b> o qual pertence a classe.
	 * @param valor - O valor do campo a ser validado.
	 */
	public static void verificaSeCampoPossuiSimbolos(Class<?> classe, String campo, String valor) {
		validaClasseCampo(classe, campo);
		validarSeNomePossuiSimbolos(classe, campo, valor);
	}
	
	/**
	 * Verifica se o campo passado possui <i>dígitos</i>.
	 * 
	 *
	 * @throws IllegalArgumentException Caso o campo passado possua qualquer dígito. Ou se caso algum 
	 * dos valores como classe ou campo tenham sido preenchidos de forma incorreta.
	 * 
	 * @param classe - A classe no qual o atributo pertence.
	 * @param campo - O <b>nome do campo</b> o qual pertence a classe.
	 * @param valor - O valor do campo a ser validado.
	 */
	public static void verificaSeCampoPossuiDigitos(Class<?> classe, String campo, String valor) {
		validaClasseCampo(classe, campo);
		validaSeCampoPossuiDigitos(classe, campo, valor);
	}
	
	/**
	 * Verifica se cada posição do campo informado é composto por <i>dígitos</i>.
	 * 
	 *
	 * @throws IllegalArgumentException Caso o campo passado possua qualquer tipo de carácter que for diferente de um dígito. Ou se caso algum 
	 * dos valores como classe ou campo tenham sido preenchidos de forma incorreta.
	 * 
	 * @param classe - A classe no qual o atributo pertence.
	 * @param campo - O <b>nome do campo</b> o qual pertence a classe.
	 * @param valor - O valor do campo a ser validado.
	 */
	public static void verificaSeCampoSoPossuiDigitos(Class<?> classe, String campo, String valor) {
		validaClasseCampo(classe, campo);
		verificaSeOCampoECompostoDeDigitos(classe, campo, valor);
	}
	
	private static void verificaSeOCampoECompostoDeDigitos(Class<?> classe, String campo, String valor) {
		for (int i = 0; i < valor.length(); ++i) {
			if (!Character.isDigit(valor.charAt(i))) {
				throw new IllegalArgumentException(retornaMensagemExceptionPadronizada(classe, campo, "só pode possuir dígitos."));
			}
		}
	}

	private static void validaSeCampoPossuiDigitos(Class<?> classe, String campo, String nome) {
		if (nomePossuiDigitos(nome)) {
			throw new IllegalArgumentException(retornaMensagemExceptionPadronizada(classe, campo, "não pode possuir dígitos."));
		}
	}

	private static void validarSeNomePossuiSimbolos(Class<?> classe, String campo, String nome) {
		if (nomePossuiSimbolos(nome)) {
			throw new IllegalArgumentException(retornaMensagemExceptionPadronizada(classe, campo, "não pode possuir caracteres especiais."));
		}
	}
	
	
	protected static boolean nomePossuiSimbolos(String nome) {
		return SIMBOLOS_NAO_PERMITIDOS.stream().anyMatch(simbolo -> nomeContemEsteSimbolo(nome, simbolo));
	}

	protected static boolean nomeContemEsteSimbolo(String nome, String simbolo) {
		return nome.contains(simbolo);
	}

	private static String retornaValorSemEspacamento(String valor) {
		return valor.trim();
	}
	
	private static boolean nomePossuiDigitos(String nome) {
		for (int i = 0; i < nome.length(); ++i) {
			if (isDigit(nome.charAt(i))) {
				return true;
			}
		}
		return false;
	}
	
	private static void validaEspacamentoComBaseNoTamanhoMinimo(Class<?> classe, String campo, String valor, int tamanhoMin) {
		if (retornaValorSemEspacamento(valor).length() < tamanhoMin || retornaValorSemEspacamento(valor).isEmpty()) {
			throw new IllegalArgumentException(retornaMensagemExceptionPadronizada(classe, campo, "foi informado em branco, ou com uma quantidade de espaços em brancos excessivos."));
		}
	}
	
	private static void validaValorNulo(Class<?> classe, String campo, Object valor) {
		if (valor == null) {
			throw new IllegalArgumentException(retornaMensagemExceptionPadronizada(classe, campo, "não pode ser nulo."));
		}
	}
	
	private StringValidator() {
		
	}

}
