package br.com.contmatic.model.v1.empresa.endereco;

import static br.com.contmatic.model.v1.empresa.endereco.TipoEstado.isEstadoValido;
import static br.com.contmatic.model.v1.empresa.endereco.TipoPais.BRASIL;
import static br.com.contmatic.util.AtributoValidator.validaNulo;
import static br.com.contmatic.util.AtributoValidator.validaTamanho;
import static br.com.contmatic.util.AtributoValidator.validaNomeDigitos;
import static br.com.contmatic.util.AtributoValidator.validaNomeSimples;
import static java.util.Locale.ROOT;

public class Estado {

	private final String nome;

	private final String uf;

	private final TipoPais pais;

	public Estado(TipoEstado uf) {
		validaNulo(TipoEstado.class, "uf", uf);
		this.uf = uf.getUf();
		this.nome = uf.getNome();
		this.pais = uf.getPais();
	}

	public Estado(String nome, String uf, TipoPais pais) {
		this.validaValoresInformados(nome, uf, pais);
		this.nome = nome;
		this.uf = uf.toUpperCase(ROOT);		
		this.pais = pais;
	}

	private void validaValoresInformados(String nome, String uf, TipoPais pais) {
		this.validaEstado(nome);
		this.validaUF(uf);
		this.validaPais(pais);
		this.validaEstadoNacionalInformado(nome, uf, pais);
	}

	private void validaEstadoNacionalInformado(String nome, String uf, TipoPais pais) {
		if (!isEstadoValido(nome, uf) && pais.equals(BRASIL)) {
			throw new IllegalArgumentException(
					"Os valores informados para o estado não condizem com um estado Brasileiro válido.\n"
							+ "Por Favor, verifique se você não esqueceu algum acento no nome da cidade.");
		}
	}

	private void validaPais(TipoPais pais) {
		validaNulo(getClass(), "pais", pais);
	}

	private void validaUF(String uf) {
		validaNulo(getClass(), "uf", uf);
		validaTamanho(getClass(), "uf", uf.length(), 2);
		validaNomeSimples(getClass(), "uf", uf);
	}

	private void validaEstado(String estado) {
		validaNulo(getClass(), "nome", estado);
		validaTamanho(getClass(), "nome", estado.length(), 2, 50);
		validaNomeDigitos(getClass(), "nome", estado);
	}

	public String getNomeEstado() {
		return nome;
	}

	public String getUf() {
		return uf;
	}

	public TipoPais getPais() {
		return pais;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uf == null) ? 0 : uf.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Estado other = (Estado) obj;
		return (uf == other.uf);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ": " + nome + ", UF=" + uf + ", Pais=" + pais.getNome();
	}
}
