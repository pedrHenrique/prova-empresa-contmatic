package br.com.contmatic.empresav1.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.HashSet;

public class Empresa {

	// Variáveis
	private long idEmpresa;
	private String nome;
	private String cnpj;
	private String cep;
	private String telefone;
	private static Collection<Empresa> empresaLista = new HashSet<Empresa>();

	// Construtores
	public Empresa(long idEmpresa, String nome, String cnpj, String endereco, String telefone) {
		setIdEmpresa(idEmpresa);
		setNome(nome);
		setCnpj(cnpj);
		setCep(endereco);
		setTelefone(telefone);
		salvarRegistro(this);
	}

	public Empresa() {

	}

	// Métodos

	public Collection<Empresa> listarEmpresas() {
		empresaLista.forEach(System.out::println);
		return empresaLista;
	}

	public void registrarEmpresa(long idEmpresa, String nome, String cnpj, String endereco, String telefone) {
		setIdEmpresa(idEmpresa);
		setNome(nome);
		setCnpj(cnpj);
		setCep(endereco);
		setTelefone(telefone);
		new Empresa(idEmpresa, nome, cnpj, endereco, telefone);
	}

	private void salvarRegistro(Empresa departamento) {
		if (empresaLista.contains(departamento)) {
			throw new IllegalArgumentException("A empresa: " + getIdEmpresa() + " já possui registro\n");
		} else {
			empresaLista.add(departamento);
		}
	}

	public Empresa solicitarEmpresa(long id) {
		Iterator<Empresa> iterator = getEmpresaLista().iterator();
		Empresa obj = new Empresa();
		while (iterator.hasNext()) {
			obj = iterator.next();
			
			if (obj.getIdEmpresa() != id && !(iterator.hasNext())) {
				throw new IllegalArgumentException("Departamento " + id + " não existe\n");
			} else if (obj.getIdEmpresa() == id) {
				return obj;
			}
		}

		return null;
	}

	public Empresa removerEmpresa(long id) {
		Iterator<Empresa> iterator = empresaLista.iterator();
		Empresa obj = new Empresa();
		while (iterator.hasNext()) {
			obj = iterator.next();

			if (obj.getIdEmpresa() != id && iterator.hasNext() == false) {
				throw new IllegalArgumentException("A Empresa " + id + " não existe\n");
			} else if (obj.getIdEmpresa() == id) {
				iterator.remove();
				break;
			}
		}
		return obj;
	}

	// Getters and Setters
	
	public long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(long idEmpresa) {
		if (idEmpresa > 0 && idEmpresa <= 500) {
			this.idEmpresa = idEmpresa;
		} else {
			throw new IllegalArgumentException("O ID da empresa deve ser maior que zero e menor que 500!");
		}
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if ((nome.length() >= 5) && !(nome.isEmpty())) {
			this.nome = nome;
		} else {
			throw new IllegalArgumentException("Nome deve ter 5 ou mais caracteres!");
		}
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		String aux = cnpj.replaceAll("\\D", "");
		if (aux.length() == 14) {
			this.cnpj = aux.substring(0,2) + "." + aux.substring(2,5) + "." + aux.substring(5,8) +
					"/" + aux.substring(8,12) + "-" + aux.substring(12,14);
		} else {
			throw new IllegalArgumentException("Digite apenas os números do CNPJ!!"); //Ex CNPJ: 00.000.000/0001-00
		}
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		String aux = cep.replaceAll("\\D", "");
		if (aux.length() >= 8) {
			this.cep = aux.substring(0,5) + "-" + aux.substring(5,8);
		} else {
			throw new IllegalArgumentException("Digite apenas os números do CEP"); //Ex CNPJ: 03575-090
		}
		
	}

	public String getTelefone() { //(11) 4564-9304 
		return telefone;
	}

	public void setTelefone(String telefone) {
		String aux = telefone.replaceAll("\\D", "");
		if (aux.length() >= 10) {
			this.telefone = "(" + aux.substring(0,2) + ") " + aux.substring(2,6) + "-" + aux.substring(6) ;
		} else {
			throw new IllegalArgumentException("Digite o DDD e o número do telefone/celular juntos.");
		}

	}

	public static Collection<Empresa> getEmpresaLista() {
		return empresaLista;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idEmpresa ^ (idEmpresa >>> 32));
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
		if (idEmpresa != other.idEmpresa)
			return false;
		return true;
	}

	@Override
	public String toString() { //Ex CNPJ: 00.000.000/0001-00
		return "Empresa: [" + idEmpresa + ", Nome: " + nome + ", CNPJ: " + getCnpj() + 
				", Cep: " + getCep() + ", Telefone: " + getTelefone() + "]";
	}

}
