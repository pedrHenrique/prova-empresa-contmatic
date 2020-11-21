package br.com.contmatic.empresa.v1.model;

public class Departamento {

	private static final int NOME_TAMANHO_MAX = 40;

	private static final int NOME_TAMANHO_MIN = 2;

	private static final int RAMAL_TAMANHO_MAX = 8;

	private static final int RAMAL_TAMANHO_MIN = 3;

	private String nome;

	private String ramal;

	public Departamento(String nome, String ramal) {
		setNome(nome);
		setRamal(ramal);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.validaNulo(nome);
		this.validaTamanhoNome(nome);
		this.validaFormato(nome);
		this.nome = nome;
	}

	public String getRamal() {
		return ramal;
	}

	public void setRamal(String ramal) {
		this.validaNulo(ramal);
		this.validaTamanhoRamal(ramal);
		this.validaFormatoRamal(ramal);
		this.ramal = ramal;
	}

	private void validaFormatoRamal(String ramal) {
		for (int i = 0; i < ramal.length(); ++i) {
			if (!Character.isDigit(ramal.charAt(i))) {
				throw new IllegalArgumentException(
						"Ramal só pode conter números");
			}
		}
	}

	private void validaTamanhoRamal(String ramal) {
		if (ramal.length() < RAMAL_TAMANHO_MIN || ramal.length() > RAMAL_TAMANHO_MAX) {
			throw new IllegalArgumentException(
				"O tamanho para o ramal do departamento não pode ser menor que " + RAMAL_TAMANHO_MIN + " ou maior que " + RAMAL_TAMANHO_MAX);
		}
	}

	private void validaTamanhoNome(String nome) {
		if (nome.length() < NOME_TAMANHO_MIN || nome.length() > NOME_TAMANHO_MAX) {
			throw new IllegalArgumentException(
				"O tamanho para o nome do departamento não pode ser menor que " + NOME_TAMANHO_MIN + " ou maior que " + NOME_TAMANHO_MAX);
		}
	}

	private void validaNulo(String valor) {
		if (valor == null) {
			throw new NullPointerException("Valor não pode ser nulo");
		}
	}

	private void validaFormato(String nome) {
		if (nome.contains("_-!@#$%¨&*()?/|.,;")) {
			throw new IllegalArgumentException("O nome do departamento não pode possuir caracteres especiais ou números");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((ramal == null) ? 0 : ramal.hashCode());
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
		Departamento other = (Departamento) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (ramal == null) {
			if (other.ramal != null)
				return false;
		} else if (!ramal.equals(other.ramal))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return ("Departamento: " + nome + ", Ramal: " + ramal);

	}
}
