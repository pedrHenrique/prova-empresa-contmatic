package br.com.contmatic.empresa.v1.model;

import br.com.contmatic.empresa.v1.model.contato.Telefone;

public class Contato {

	private static final int EMAIL_TAMANHO_MAX = 55;

	private static final int EMAIL_TAMANHO_MIN = 7;

	private String email;

	private Telefone telefone;

	public Contato(String email, Telefone contato) {
		this.setEmail(email);
		this.setTelefone(contato);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.validaNuloEmail(email);
		this.validaTamanho(email);
		this.validaEmail(email);
		this.email = email;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.validaNuloTelefone(telefone);
		this.telefone = telefone;
	}

	private void validaNuloTelefone(Telefone telefone) {
		if (telefone == null) {
			throw new NullPointerException("Telefone não pode ser nulo");
		}
	}

	private void validaEmail(String email) {
		if (!validaInicioEmail(email) || !validaDomioEmail(email)) {
			throw new IllegalArgumentException("O modelo de email inserido não corresponde a um modelo de email válido."
					+ "Por Favor tente novamente.");
		}
	}

	private boolean validaInicioEmail(String email) {
		return email.contains("@") && email.contains(".");
	}

	private boolean validaDomioEmail(String email) {
		return (email.contains("com") || email.contains("br")) || (email.contains("org") || email.contains("net"));
	}

	private void validaTamanho(String email) {
		if (email.length() < EMAIL_TAMANHO_MIN || email.length() >= EMAIL_TAMANHO_MAX) {
			throw new IllegalArgumentException(
					"O Email que você inseriu é muito grande ou muito pequeno para ser um email válido. Tente novamente");
		}
	}

	private void validaNuloEmail(String valor) {
		if (valor == null) {
			throw new NullPointerException("Email não pode ser nulo");
		}
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contato other = (Contato) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "Email=" + email + ", Telefone de contato=" + telefone;
	}
}
