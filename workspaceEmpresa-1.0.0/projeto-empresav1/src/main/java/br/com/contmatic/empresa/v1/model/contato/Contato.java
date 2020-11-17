package br.com.contmatic.empresa.v1.model.contato;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import br.com.contmatic.empresa.v1.util.RegexModel;

public class Contato {

	private String email; //não deve ficar vázio
	
	private Set<Telefone> contatosLista = new HashSet<>() ;

	public Contato(String email, Telefone contato) {
		setEmail(email);		
		this.contatosLista.add(contato);
	}
	
	public Contato(String email, Telefone contato, Telefone contato2) {
		setEmail(email);		
		this.contatosLista.addAll(Arrays.asList(contato, contato2));
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email == null) {
			throw new NullPointerException("Email não pode ficar vazio");

		} else if (email.length() < 7 || email.length() >= 65) {
			throw new IllegalArgumentException(
					"O Email que você inseriu é muito grande ou muito pequeno para ser um email válido. Tente novamente");

		} else if (!Pattern.compile(RegexModel.EMAIL).matcher(email).matches()) {
			throw new IllegalArgumentException("O modelo de email inserido não corresponde a um modelo de email válido."
					+ "Por Favor tente novamente.");

		} else {
			this.email = email;
		}
	}
	
	public Set<Telefone> getContatosLista() {
		return contatosLista;
	}

	public void setContatosLista(Set<Telefone> contatosLista) {
		// pensar em verificações para esse cara
		this.contatosLista = contatosLista;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		Contato other = (Contato) obj;
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Contato [" + "Email: " + email + ", Lista de Contatos: " + contatosLista  + "]";
	}
	
	public static void main (String[] args) {
		Contato contato = new Contato("Pedroxboy11@hotmail.com", new Telefone("1145649304"));
		Contato contato2 = new Contato("pedrleonardi@gmail.com", new Telefone("11 941063792"), new Telefone("1140028922"));
		System.out.println("Contato 1: " + contato.toString());
		System.out.println("Contato 2: " + contato2.toString());
	}
	
}
