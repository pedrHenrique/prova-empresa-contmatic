package br.com.contmatic.model.v1.endereco;

import static br.com.contmatic.util.v1.CamposTypes.ENDERECO_NUMERO_TAMANHO_MAX;
import static br.com.contmatic.util.v1.CamposTypes.ENDERECO_NUMERO_TAMANHO_MIN;
import static br.com.contmatic.util.v1.CamposTypes.ENDERECO_TAMANHO_CEP;
import static br.com.contmatic.util.v1.CamposTypes.ENDERECO_TAMANHO_COMPLEMENTO;
import static br.com.contmatic.util.v1.CamposTypes.ENDERECO_TAMANHO_MAX;
import static br.com.contmatic.util.v1.CamposTypes.ENDERECO_TAMANHO_MIN;
import static br.com.contmatic.util.v1.validator.NumericValidator.validaTamanho;
import static br.com.contmatic.util.v1.validator.StringValidator.validaEspacamento;
import static br.com.contmatic.util.v1.validator.StringValidator.validaNulo;
import static br.com.contmatic.util.v1.validator.StringValidator.verificaSeCampoPossuiSimbolos;
import static br.com.contmatic.util.v1.validator.StringValidator.verificaSeCampoSoPossuiDigitos;

public class Endereco {

	private static final String CAMPO_BAIRRO = "bairro";

	private String rua;

	private String bairro;

	private Integer numero;

	private String complemento;

	private String cep;

	private Cidade cidade;

	public Endereco(String rua, String bairro, Integer numero, String cep, String complemento, Cidade cidade) {
		this.setRua(rua);
		this.setBairro(bairro);
		this.setNumero(numero);
		this.setComplemento(complemento);
		this.setCep(cep);
		this.setCidade(cidade);
	}

	public Endereco(String rua, String bairro, Integer numero, String cep, Cidade cidade) {
		this.setRua(rua);
		this.setBairro(bairro);
		this.setNumero(numero);
		this.setCep(cep);
		this.setCidade(cidade);
	}

	public Endereco(Integer numero, String cep) {
		this.setNumero(numero);
		this.setCep(cep);
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		validaNulo(getClass(), "rua", rua);
		validaEspacamento(getClass(), "rua", rua, ENDERECO_TAMANHO_MIN);
		validaTamanho(getClass(), "rua", rua.length(), ENDERECO_TAMANHO_MIN, ENDERECO_TAMANHO_MAX);
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		validaNulo(getClass(), CAMPO_BAIRRO, bairro);
		validaEspacamento(getClass(), CAMPO_BAIRRO, bairro, ENDERECO_TAMANHO_MIN);
		validaTamanho(getClass(), CAMPO_BAIRRO, bairro.length(), ENDERECO_TAMANHO_MIN, ENDERECO_TAMANHO_MAX);
		this.bairro = bairro;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		validaNulo(getClass(), "numero", numero);
		validaTamanho(getClass(), "numero", numero, ENDERECO_NUMERO_TAMANHO_MIN, ENDERECO_NUMERO_TAMANHO_MAX);
		this.numero = numero;
	}

	public String getCep() {
		return cep;
	}

	public void setComplemento(String complemento) {
		validaNulo(getClass(), "complemento", complemento);
		this.validaTamanhoComplemento(complemento);
		verificaSeCampoPossuiSimbolos(getClass(), "complemento", complemento);
		this.complemento = complemento;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCep(String cep) {
		validaNulo(getClass(), "cep", cep);
		validaTamanho(getClass(), "cep", cep.length(), ENDERECO_TAMANHO_CEP);
		verificaSeCampoSoPossuiDigitos(getClass(), "cep", cep);
		this.cep = cep;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setCidade(Cidade cidade) {
		validaNulo(getClass(), "cidade", cidade);
		this.cidade = cidade;
	}

	private void validaTamanhoComplemento(String complemento) {
		if (complemento.length() > ENDERECO_TAMANHO_COMPLEMENTO) {
			throw new IllegalArgumentException("Tamanho de complemento inserido precisa ser menor que " + ENDERECO_TAMANHO_COMPLEMENTO + ".");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
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
		Endereco other = (Endereco) obj;
		if (cep == null) {
			if (other.cep != null) {
				return false;
			}
		} else if (!cep.equals(other.cep)) {
			return false;
		}
		if (numero == null) {
			if (other.numero != null) {
				return false;
			}
		} else if (!numero.equals(other.numero)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ": Rua=" + rua + ", Numero=" + numero + ", Complemento=" + complemento
				+ ", Bairro=" + bairro + ", Cep=" + cep + ", " + cidade;
	}
}
