package br.com.contmatic.empresa.v1.model;

import java.util.regex.Pattern;

import br.com.contmatic.empresa.v1.model.contato.Contato;
import br.com.contmatic.empresa.v1.util.RegexModel;

import static br.com.contmatic.empresa.v1.util.Documentos.cnpjValido;;

public class Empresa {

	private String nomeFantasia;

	private String cnpj; // Verificar o CNPJ

	private String cep; // Classe Endereco

	private Contato contato; // Classe Contato


	public Empresa(String nome, String cnpj, String endereco, Contato contato) {
		setNomeFantasia(nome);
		setCnpj(cnpj);
		setCep(endereco);
		setContato(contato);
		
	}

	public Empresa() {

	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		if (nomeFantasia == null) {
			throw new NullPointerException("Nome Fantasia não pode ficar vazio");

		} else if (nomeFantasia.length() < 2 || nomeFantasia.length() > 45) {
			throw new IllegalArgumentException("Nome Fantasia não pôde conter tamanho inferior a 2 ou maior que 45");

		} else if (nomeFantasia.matches("  {1,}?")) {
			throw new IllegalArgumentException("O espaçamento dado no nome fantasia está incorreto. Tente novamente.");

		} else if (!Pattern.compile(RegexModel.NOMEFANTASIA)
				.matcher(nomeFantasia).matches()) {
			throw new IllegalArgumentException("Os valores para o nome fantasia estão incorretos...\n"
					+ "Nome Fantasia NÃO pode conter números no seu início, nem como caracteres especíais."
					+ " Caracteres como '&' ou '.' podem ser inseridos");
		} else {
			this.nomeFantasia = nomeFantasia;
		}
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		if (cnpj == null) {
			throw new NullPointerException("CNPJ não pode ficar vazio.");

		} else if (cnpj.matches(RegexModel.CNPJ)) {
			if (cnpjValido(cnpj.replaceAll("\\D", ""))) {
				this.cnpj = cnpj;
				
			} else {
				throw new IllegalArgumentException("O CNPJ que você inseriu não é válido. Por favor tente novamente.");
			}

		} else {
			throw new IllegalArgumentException("O CNPJ que você inseriu não está no padrão. Por favor tente novamente.");
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

	
	public Contato getContato() { // (11) 4564-9304
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
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
		Empresa other = (Empresa) obj;
		if (cnpj == null) {
			if (other.cnpj != null) {
				return false;
			}
		} else if (!cnpj.equals(other.cnpj)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() { 
		return "Empresa: [ Nome: " + nomeFantasia + ", CNPJ: " + getCnpj() + ", Cep: " + getCep() + ", Contato: "
				+ getContato() + "]";
	}
}