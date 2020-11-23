package br.com.contmatic.empresa.v1.model;

import static br.com.contmatic.empresa.v1.util.Documentos.cpfValido;

public class Funcionario {

	private static final int NOME_TAMANHO_MAX = 75;

	private static final int NOME_TAMANHO_MIN = 10;

	private static final double SALARIO_VALOR_MAX = 99999.00;

	private static final double SALARIO_VALOR_MIN = 0.0;

	private String nome;

	private String cpf;

	private Endereco endereco;

	private Contato contato;

	private double salario;

	private Departamento departamento;

	public Funcionario(String nome, String cpf, Endereco endereco, Contato contato, double salario,
			Departamento departamento) {
		setNome(nome);
		setCpf(cpf);
		setEndereco(endereco);
		setContato(contato);
		setSalario(salario);
		setDepartamento(departamento);		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.validaNulo(nome);
		this.validaTamanhoNome(nome);
		this.validaNome(nome);
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.validaNulo(cpf);
		this.validaCpf(cpf);
		this.cpf = formataCpf(cpf);
	}

	private void validaCpf(String cpf) {
		if (!cpfValido(cpf)) {
			throw new IllegalArgumentException(
					"O CPF informado não pôde ser aceito. Por favor, insira o CPF sem formatação");
		}
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.validaNuloEndereco(endereco);
		this.endereco = endereco;
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.validaNuloContato(contato);
		this.contato = contato;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.validaSalario(salario);
		this.salario = salario;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.validaNuloDepartamento(departamento);
		this.departamento = departamento;
	}

	public static String formataCpf(String cpf) {
		return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
	}

	private void validaNome(String nome) {
		int tamanhoNome = nome.length();
		for (int i = 0; i < tamanhoNome; ++i) {
			if (Character.isDigit(nome.charAt(i))) {
				throw new IllegalArgumentException(
						"O nome do funcionario não pode possuir caracteres especiais ou números");
			} else {
				if (nomePossuiSimbolos(nome)) {
					throw new IllegalArgumentException(
							"O nome do funcionario não pode possuir caracteres especiais ou números");
				}
			}
		}
	}

	private boolean nomePossuiSimbolos(String nome) {
		return (nome.contains("!") || nome.contains("@") || nome.contains("#") || nome.contains("$")
		|| nome.contains("%") || nome.contains("&") || nome.contains("-") || nome.contains("_")
		|| nome.contains("()") || nome.contains("?") || nome.contains(".") || nome.contains(","));
	}

	private void validaTamanhoNome(String nome) {
		if (nome.length() < NOME_TAMANHO_MIN || nome.length() > NOME_TAMANHO_MAX) {
			throw new IllegalArgumentException(
					"Nome do funcionario deve ter tamanho entre " + NOME_TAMANHO_MIN + " a " + NOME_TAMANHO_MAX);
		}
	}

	private void validaNulo(String valor) {
		if (valor == null) {
			throw new NullPointerException("Valor não pode ser nulo");
		}
	}

	private void validaSalario(double salario) {
		if (salario <= SALARIO_VALOR_MIN || salario >= SALARIO_VALOR_MAX) {
			throw new IllegalArgumentException("Este salário não pode ser aceito...");
		}
	}

	private void validaNuloEndereco(Endereco endereco) {
		if (endereco == null) {
			throw new NullPointerException("Endereco não pode ser nulo");
		}
	}

	private void validaNuloContato(Contato contato) {
		if (contato == null) {
			throw new NullPointerException("Contato não pode ser nulo");
		}
	}

	private void validaNuloDepartamento(Departamento departamento) {
		if (departamento == null) {
			throw new NullPointerException("Departamento não pode ser nulo");
		}
	}

	public static int getNomeTamanhoMax() {
		return NOME_TAMANHO_MAX;
	}

	public static int getNomeTamanhoMin() {
		return NOME_TAMANHO_MIN;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
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
		Funcionario other = (Funcionario) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " Nome=" + nome + ", CPF=" + cpf + "\nDepartamento=" + departamento
				+ "\nEndereco=" + endereco + "\nContato=" + contato + "\nSalario=" + salario;
	}
}
