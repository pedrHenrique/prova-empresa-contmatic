package br.com.contmatic.model.v1.empresa;

import static br.com.contmatic.util.v1.CamposTypes.EMPRESA_CAMPO_NOMEFANTASIA;
import static br.com.contmatic.util.v1.CamposTypes.EMPRESA_CAMPO_RAZAOSOCIAL;
import static br.com.contmatic.util.v1.CamposTypes.EMPRESA_NOME_FANTASIA_TAMANHO_MAX;
import static br.com.contmatic.util.v1.CamposTypes.EMPRESA_NOME_FANTASIA_TAMANHO_MIN;
import static br.com.contmatic.util.v1.CamposTypes.EMPRESA_RAZAO_SOCIAL_TAMANHO_MAX;
import static br.com.contmatic.util.v1.CamposTypes.EMPRESA_RAZAO_SOCIAL_TAMANHO_MIN;
import static br.com.contmatic.util.v1.validator.NumericValidator.validaTamanho;
import static br.com.contmatic.util.v1.validator.StringValidator.validaEspacamento;
import static br.com.contmatic.util.v1.validator.StringValidator.validaNomeSimples;
import static br.com.contmatic.util.v1.validator.StringValidator.validaNulo;
import static br.com.contmatic.util.v1.validator.documentos.CnpjValidator.validarCnpj;

import br.com.contmatic.model.v1.endereco.Endereco;

public class Empresa {

	private String razaoSocial;

	private String nomeFantasia;

	private String cnpj;

	private Endereco endereco;

	private Contato contato;

	public Empresa(String razaoSocial, String cnpj) {
		this.setRazaoSocial(razaoSocial);
		this.setCnpj(cnpj);
	}

	public Empresa(String cnpj) {
		this.setCnpj(cnpj);
	}

	public Empresa(String razaoSocial, String nomeFantasia, String cnpj, Endereco endereco, Contato contato) {
		this.setRazaoSocial(razaoSocial);
		this.setNomeFantasia(nomeFantasia);
		this.setCnpj(cnpj);
		this.setEndereco(endereco);
		this.setContato(contato);
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		validaNulo(getClass(), EMPRESA_CAMPO_NOMEFANTASIA, nomeFantasia);
		validaEspacamento(getClass(), EMPRESA_CAMPO_NOMEFANTASIA, nomeFantasia, EMPRESA_NOME_FANTASIA_TAMANHO_MIN);
		validaTamanho(getClass(), EMPRESA_CAMPO_NOMEFANTASIA, nomeFantasia.length(), EMPRESA_NOME_FANTASIA_TAMANHO_MIN, EMPRESA_NOME_FANTASIA_TAMANHO_MAX);
		validaNomeSimples(getClass(), EMPRESA_CAMPO_NOMEFANTASIA, nomeFantasia);
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		validaNulo(getClass(), EMPRESA_CAMPO_RAZAOSOCIAL, razaoSocial);
		validaEspacamento(getClass(), EMPRESA_CAMPO_RAZAOSOCIAL, razaoSocial, EMPRESA_RAZAO_SOCIAL_TAMANHO_MIN);
		validaTamanho(getClass(), EMPRESA_CAMPO_RAZAOSOCIAL, razaoSocial.length(), EMPRESA_RAZAO_SOCIAL_TAMANHO_MIN, EMPRESA_RAZAO_SOCIAL_TAMANHO_MAX);
		validaNomeSimples(getClass(), EMPRESA_CAMPO_RAZAOSOCIAL, razaoSocial);
		this.razaoSocial = razaoSocial;
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		validaNulo(getClass(), "contato", contato);
		this.contato = contato;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		validarCnpj(cnpj);
		this.cnpj = cnpj;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		validaNulo(getClass(), "endereco", endereco);
		this.endereco = endereco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		result = prime * result + ((razaoSocial == null) ? 0 : razaoSocial.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empresa other = (Empresa) obj;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
		if (razaoSocial == null) {
			if (other.razaoSocial != null)
				return false;
		} else if (!razaoSocial.equals(other.razaoSocial))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ": Razão Social=" + razaoSocial + ", Nome Fantasia=" + nomeFantasia
				+ ", CNPJ=" + cnpj + "\nEndereco=" + endereco + "\nContato=" + contato;
	}
}