package br.com.contmatic.model.v1.empresa;

import static br.com.contmatic.util.AtributoValidator.validaNomeSimples;
import static br.com.contmatic.util.AtributoValidator.validaNulo;
import static br.com.contmatic.util.CamposTypes.EMPRESA_NOME_FANTASIA_TAMANHO_MAX;
import static br.com.contmatic.util.CamposTypes.EMPRESA_NOME_FANTASIA_TAMANHO_MIN;
import static br.com.contmatic.util.CamposTypes.EMPRESA_RAZAO_SOCIAL_TAMANHO_MAX;
import static br.com.contmatic.util.CamposTypes.EMPRESA_RAZAO_SOCIAL_TAMANHO_MIN;
import static br.com.contmatic.util.documentos.CnpjValidator.validarCnpj;

import br.com.contmatic.model.v1.empresa.endereco.Endereco;
import br.com.contmatic.util.CamposTypes;

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
		validaNulo(getClass(), "nomeFantasia", nomeFantasia);
		this.validaTamanhoNomeFantasia(nomeFantasia);
		validaNomeSimples(getClass(), "nomeFantasia", nomeFantasia);
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		validaNulo(getClass(), "razaoSocial", razaoSocial);
		this.validaTamanhoRazaoSocial(razaoSocial);
		validaNomeSimples(getClass(), "razaoSocial", razaoSocial);
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

	private void validaTamanhoNomeFantasia(String nomeFantasia) {
		if (nomeFantasia.length() < EMPRESA_NOME_FANTASIA_TAMANHO_MIN || nomeFantasia.length() > EMPRESA_NOME_FANTASIA_TAMANHO_MAX) {
			throw new IllegalArgumentException("Nome Fantasia não pôde conter tamanho inferior a "
					+ EMPRESA_NOME_FANTASIA_TAMANHO_MIN + " ou maior que " + CamposTypes.EMPRESA_NOME_FANTASIA_TAMANHO_MAX + ".");
		}
	}

	private void validaTamanhoRazaoSocial(String razaoSocial) {
		if (razaoSocial.length() < EMPRESA_RAZAO_SOCIAL_TAMANHO_MIN || razaoSocial.length() > EMPRESA_RAZAO_SOCIAL_TAMANHO_MAX) {
			throw new IllegalArgumentException("Razão Social não pôde conter tamanho inferior a "
			+ EMPRESA_RAZAO_SOCIAL_TAMANHO_MIN + " ou maior que " + EMPRESA_RAZAO_SOCIAL_TAMANHO_MAX + ".");
		}
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