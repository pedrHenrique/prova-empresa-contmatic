package br.com.contmatic.empresa.v1.model;

import static br.com.contmatic.empresa.v1.util.Documentos.cnpjValido;

public class Empresa {

	private static final int RAZAO_SOCIAL_TAMANHO_MAX = 90;

	private static final int RAZAO_SOCIAL_TAMANHO_MIN = 10;

	private static final int NOME_FANTASIA_TAMANHO_MIN = 2;

	private static final int NOME_FANTASIA_TAMANHO_MAX = 50;
	
	private String razaoSocial;

	private String nomeFantasia;

	private String cnpj; 

	private Endereco endereco; 

	private Contato contato; 

	public Empresa(String razaoSocial, String nome, String cnpj, Endereco endereco, Contato contato) {
		this.setRazaoSocial(razaoSocial);
		this.setNomeFantasia(nome);
		this.setCnpj(cnpj);
		this.setEndereco(endereco);
		this.setContato(contato);
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.validaNulo(nomeFantasia); 
		nomeFantasia = this.removeEspacamento(nomeFantasia); 
		this.validaTamanhoNomeFantasia(nomeFantasia); 
		this.validaNome(nomeFantasia);
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.validaNulo(razaoSocial);
		razaoSocial = this.removeEspacamento(razaoSocial);
		this.validaTamanhoRazaoSocial(razaoSocial);
		this.validaNome(razaoSocial);
		this.razaoSocial = razaoSocial;
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		validaNuloContato(contato);
		this.contato = contato;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.validaNulo(cnpj);
		this.validaCnpj(cnpj);
		this.cnpj = cnpj;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	private void validaNome(String nome) {
		if (nome.contains("0123456789_-!@#$%¨&*()?/|.,;")) {
			throw new IllegalArgumentException("O nome não pode possuir caracteres especiais ou números");
		}
	}
	
	private String removeEspacamento(String nome) {
		return nome.trim();
	}

	private void validaTamanhoNomeFantasia(String nomeFantasia) {
		if (nomeFantasia.length() < NOME_FANTASIA_TAMANHO_MIN || nomeFantasia.length() > NOME_FANTASIA_TAMANHO_MAX) {
			throw new IllegalArgumentException("Nome Fantasia não pôde conter tamanho inferior a 2 ou maior que 45");
		}
	}

	private void validaNulo(String valor) {
		if (valor == null) {
			throw new NullPointerException("Nulo não pode ser passado");
		}
	}

	private void validaNuloContato(Contato contato) {
		if (contato == null){
			throw new NullPointerException("Contato não pode ser passado como nulo");
		}
	}

	private void validaTamanhoRazaoSocial(String razaoSocial) {
		if (razaoSocial.length() > RAZAO_SOCIAL_TAMANHO_MIN || razaoSocial.length() > RAZAO_SOCIAL_TAMANHO_MAX) {
			throw new IllegalArgumentException("Razão Social ou está muito longa, ou está muito pequena");
		}
	}

	private void validaCnpj(String cnpj) {
		if (!cnpjValido(cnpj)) {
			throw new IllegalArgumentException("O CNPJ que você inseriu não é válido. Insira o CNPJ sem nenhuma formatacao");
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
		return "Empresa: [ " + "Razão Social: " + razaoSocial + ", Nome Fantasia: " + nomeFantasia + ", CNPJ: " + getCnpj() + ", Endereco: " + getEndereco() + ", Contato: "
				+ getContato() + "]";
	}
}