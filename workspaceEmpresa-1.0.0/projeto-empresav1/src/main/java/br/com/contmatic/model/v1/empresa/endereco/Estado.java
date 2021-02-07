package br.com.contmatic.model.v1.empresa.endereco;

import static java.util.Locale.ROOT;

import static br.com.contmatic.util.CamposTypes.ESTADO_TAMANHO_MAX;
import static br.com.contmatic.util.CamposTypes.ESTADO_TAMANHO_MIN;
import static br.com.contmatic.model.v1.empresa.endereco.PaisType.BRASIL;
import static br.com.contmatic.model.v1.empresa.endereco.EstadoType.isEstadoValido;
import static br.com.contmatic.util.validator.StringValidator.validaEspacamento;
import static br.com.contmatic.util.validator.StringValidator.validaNomeSimples;
import static br.com.contmatic.util.validator.StringValidator.validaNulo;
import static br.com.contmatic.util.validator.StringValidator.verificaSeCampoPossuiDigitos;
import static br.com.contmatic.util.validator.NumericValidator.validaTamanho;

import java.util.Objects;

public class Estado {

	private final String nome;

	private final String uf;

	private final PaisType pais;

	public Estado(EstadoType uf) {
		validaNulo(EstadoType.class, "uf", uf);
		this.uf = uf.getUf();
		this.nome = uf.getNome();
		this.pais = uf.getPais();
	}

	public Estado(String nome, String uf, PaisType pais) {
		this.validaValoresInformados(nome, uf, pais);
		this.nome = nome;
		this.uf = uf.toUpperCase(ROOT);		
		this.pais = pais;
	}

	private void validaValoresInformados(String nome, String uf, PaisType pais) {
		this.validaEstado(nome);
		this.validaUF(uf);
		this.validaPais(pais);
		this.validaEstadoNacionalInformado(nome, uf, pais);
	}

	private void validaEstadoNacionalInformado(String nome, String uf, PaisType pais) {
		if (!isEstadoValido(nome, uf) && pais.equals(BRASIL)) {
			throw new IllegalArgumentException("Os valores informados para o estado não condizem com um estado Brasileiro válido.\n"
							+ "Por Favor, verifique se você não esqueceu algum acento no nome da cidade.");
		}
	}

	private void validaPais(PaisType pais) {
		validaNulo(getClass(), "pais", pais);
	}

	private void validaUF(String uf) {
		validaNulo(getClass(), "uf", uf);
		validaEspacamento(getClass(), "uf", uf, ESTADO_TAMANHO_MIN);
		validaTamanho(getClass(), "uf", uf.length(), ESTADO_TAMANHO_MIN);
		validaNomeSimples(getClass(), "uf", uf);
	}

	private void validaEstado(String estado) {
		validaNulo(getClass(), "nome", estado);
		validaEspacamento(getClass(), "nome", estado, ESTADO_TAMANHO_MIN);
		validaTamanho(getClass(), "nome", estado.length(), ESTADO_TAMANHO_MIN, ESTADO_TAMANHO_MAX);
		verificaSeCampoPossuiDigitos(getClass(), "nome", estado);
	}

	public String getNomeEstado() {
		return nome;
	}

	public String getUf() {
		return uf;
	}

	public PaisType getPais() {
		return pais;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(uf);
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
		return Objects.equals(uf, other.uf);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ": " + nome + ", UF=" + uf + ", Pais=" + pais.getNome();
	}
}
