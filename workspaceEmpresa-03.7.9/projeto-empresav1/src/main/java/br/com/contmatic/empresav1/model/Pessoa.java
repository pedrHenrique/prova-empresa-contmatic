package br.com.contmatic.empresav1.model;

import java.util.Collection;
import java.util.HashSet;

public abstract class Pessoa {

	// Variáveis

	private long idPessoa;
	private String nome;
	private String cpf;
	private String cep;
	private String telefone;
	private static Collection<Pessoa> pessoaLista = new HashSet<Pessoa>();

	// Construtores

	public Pessoa(long idPessoa, String nome, String cpf, String cep, String telefone) {
		setNome(nome);
		setCpf(cpf);
		setCep(cep);
		salvarRegistro(this);
	}

	public Pessoa() {

	}

	// Métodos

	// Método de listagem inicial.
	public abstract Pessoa solicitarPessoa(long id);

	public abstract Pessoa solicitarPessoa(String cpf);

	public abstract Collection<Pessoa> listarPessoa();

	// Sonar: Major
	public abstract Pessoa cadastrarPessoa(long idPessoa, String nome, String cpf, String endereco, String telefone,
			String email, long dep, double salario);

	public abstract Pessoa removerPessoa(long id);

	private void salvarRegistro(Pessoa p) {
		if (pessoaLista.contains(p)) {
			throw new IllegalArgumentException("A Pessoa " + getIdPessoa() + " já possui registro\n");
		} else {
			pessoaLista.add(p);
		}
	}

	// Getters and Setters

	public long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(long idPessoa) {
		if (idPessoa > 0) {
			this.idPessoa = idPessoa;
		} else {
			throw new IllegalArgumentException("ID para pessoa precisa ser mais de 0");
		}

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if (!(nome.length() < 3) && !(nome.isEmpty())) { // Se Nome ñ tiver um tam > que 5 e nome não estiver vazio
			this.nome = nome;// adicione nome
		} else {
			throw new IllegalArgumentException("Nome deve ter 3 ou mais caracteres!");
		}
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		String aux = cpf.replaceAll("\\D", "");
		if (aux.length() == 11) {
			this.cpf = aux.substring(0, 3) + "." + aux.substring(3, 6) + "." + aux.substring(6, 9) + "-"
					+ aux.substring(9, 11);
		} else {
			throw new IllegalArgumentException("Digite apenas os números do CPF");
		}
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		String aux = cep.replaceAll("\\D", "");
		if (aux.length() == 8) {
			this.cep = aux.substring(0, 5) + "-" + aux.substring(5, 8);
		} else {
			throw new IllegalArgumentException("Digite apenas os números do CEP"); // Ex CNPJ: 03575-090
		}
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		String aux = telefone.replaceAll("\\D", "");
		if (aux.length() >= 10) {
			this.telefone = "(" + aux.substring(0, 2) + ") " + aux.substring(2, 6) + "-" + aux.substring(6);
		} else {
			throw new IllegalArgumentException(
					"Digite o DDD e o número do telefone/celular juntos." + "Ex.: 11941063792");
		}
	}

	public static Collection<Pessoa> getPessoaLista() {
		return pessoaLista;
	} 

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idPessoa ^ (idPessoa >>> 32));
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
		Pessoa other = (Pessoa) obj;
		if (idPessoa != other.idPessoa)
			return false;
		return true;
	}

	@Override
	public abstract String toString();

}
