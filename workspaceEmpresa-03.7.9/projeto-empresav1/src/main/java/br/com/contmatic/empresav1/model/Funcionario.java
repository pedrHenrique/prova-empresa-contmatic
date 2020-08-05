package br.com.contmatic.empresav1.model;

import java.util.Collection;
import java.util.Iterator;

public class Funcionario extends Pessoa {

	// Variáveis

	// Para amanhã 
	 //Cada Funcionario terá um ID de Identificação
	 //Cada ID terá como base um CPF que é atributo de Pessoa
	 // Se o ID já estiver cadastrado, o funcionario não pode ser criado
	 // Se o CPF da pessoa já estiver cadastrado. O Funcionario e a pessoa não podem ser criados
	
	private long idFuncionario;
	private String email;
	private double salario;
	private Departamento departamento = new Departamento();
	// TODO private ProjetoFuncionarioProjeto e DtAdmissão furutamente planejados

	// Construtores

	public Funcionario(long idPessoa, String nome, String cpf, String cep, String telefone, String email,
			long dep, double salario) {
		
		super(idPessoa, nome, cpf, cep, telefone);
		setEmail(email);
		buscaDepartamento(departamento.solicitarDep(dep));
		setSalario(salario);
	}

	public Funcionario() {

	}

	// Métodos

	@Override
	public Pessoa solicitarPessoa(String cpf) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Collection<Pessoa> listarPessoa() {
		getPessoaLista().forEach(System.out::println);
		return Pessoa.getPessoaLista();
	}

	@Override
	public Pessoa solicitarPessoa(long id) {
		Iterator<Pessoa> iterator = getPessoaLista().iterator();
		Pessoa obj = new Funcionario();
		while(iterator.hasNext()) {
			obj = iterator.next();
			
			if (obj.getIdPessoa() != id && !(iterator.hasNext())) {
				throw new IllegalArgumentException("O Funcionario com o ID: " + id + " não existe\n");
			} else if (obj.getIdPessoa() == id) {
				break;
			}
		}
		return obj;
	}
	
	@Override
	public Pessoa cadastrarPessoa(long id, String nome, String cpf, String cep, String telefone, 
			String email, long dep, double salario) {
			
		return new Funcionario(id, nome, cpf, cep, telefone, email, dep, salario);
	}
	
	@Override
	public Pessoa removerPessoa(long id) {
		Iterator<Pessoa> iterator = getPessoaLista().iterator();
		Pessoa obj = new Funcionario();

		while (iterator.hasNext()) {
			obj = iterator.next();

			if (obj.getIdPessoa() != id && !(iterator.hasNext())) {
				throw new IllegalArgumentException("A pessoa com o ID: " + id + " não existe\n");
			} else if (obj.getIdPessoa() == id) {
				iterator.remove();
				break;
			}
		}
		return obj;
	}

	// Getters and Setters

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email.contains("@") && email.contains(".com") && !(email.isEmpty() && email.length() < 5)) {
			this.email = email;
		} else {
			throw new IllegalArgumentException(
					"Por favor, digite um email válido. Ex.: pedro.silva@contmatic.com.br\n");
		}
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public Departamento buscaDepartamento(Departamento departamento) { // Está Verificando
		if (Departamento.getDepartamentoLista().contains(departamento)) {
			this.departamento = departamento;
			return departamento;
		} else {
			throw new IllegalArgumentException("Este departamento não possui registro\n");
		}
	}
		

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		if (salario > 0 && salario <= 10000.00) {
			this.salario = salario;
		} else {
			throw new IllegalArgumentException("Salario está incorreto!");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (idFuncionario ^ (idFuncionario >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Funcionario other = (Funcionario) obj;
		if (idFuncionario != other.idFuncionario)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Funcionario: [ID= " + getIdPessoa() + ", Nome= " + getNome() + ", Cpf= " + getCpf() + " Cep= "
				+ getCep() + " Telefone= " + getTelefone() + " Email= " + getEmail() + " Salario=  " + getSalario() 
				+ " " + getDepartamento() + "]";
	}

}

//public String toString() {
// Exemplo de Concatenar
// String s = "Pessoa: [idPessoa= " + getIdPessoa() + ", nome= " + getNome() + " [teste=" + teste + "]" ;
// s += super.toString();
// return s;
