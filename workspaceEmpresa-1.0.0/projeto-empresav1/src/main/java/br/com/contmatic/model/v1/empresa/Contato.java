package br.com.contmatic.model.v1.empresa;

import static br.com.contmatic.util.AtributoValidator.validaNulo;
import static br.com.contmatic.util.CamposTypes.CONTATO_EMAIL_TAMANHO_MAX;
import static br.com.contmatic.util.CamposTypes.CONTATO_EMAIL_TAMANHO_MIN;
import br.com.contmatic.model.v1.telefone.Telefone;

public class Contato {

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
		validaNulo(getClass(), "email", email);
		this.validaTamanho(email);
		this.validaEmail(email);
		this.email = email;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		validaNulo(getClass(),"telefone", telefone);
		this.telefone = telefone;
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
		if (email.length() < CONTATO_EMAIL_TAMANHO_MIN || email.length() >= CONTATO_EMAIL_TAMANHO_MAX) {
			throw new IllegalArgumentException(
					"O Email que você inseriu é muito grande ou muito pequeno para ser um email válido. Tente novamente");
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
		return getClass().getSimpleName() + ": Email=" + email + ", Telefone de contato=" + telefone;
	}
}
