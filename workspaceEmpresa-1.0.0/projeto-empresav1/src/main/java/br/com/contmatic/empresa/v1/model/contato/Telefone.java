package br.com.contmatic.empresa.v1.model.contato;

import java.util.regex.Pattern;

import br.com.contmatic.empresa.v1.util.RegexModel;

public class Telefone {

	/** O numero do telefone. */
	private String numeroTelefone;

	/** O ddd - Setado a partir do numero de telefone informado */
	private TipoDDD ddd;

	/** O tipo do telefone - Setado automaticamente se passado como nulo. */
	private TipoTelefone tipoTelefone;

	public Telefone(String numeroTelefone, TipoTelefone tipo) {
		setNumeroTelefone(numeroTelefone);
		setDdd(numeroTelefone.replaceAll(RegexModel.FORMATATELEFONE, "").substring(0, 2));
		setTipoTelefone(tipo);
	}

	public Telefone(String numeroTelefone) {
		setNumeroTelefone(numeroTelefone);
		setDdd(numeroTelefone.replaceAll(RegexModel.FORMATATELEFONE, "").substring(0, 2));
		setTipoTelefone(null);
	}

	public Telefone() {

	}

	public String getNumeroTelefone() {
		return numeroTelefone;
	}

	public void setNumeroTelefone(String telefone) {
		if (telefone == null) {
			throw new NullPointerException("Telefone não pode estar vazio");

		} else if (!Pattern.compile(RegexModel.TELEFONECELULAR).matcher(telefone).matches()) {
			throw new IllegalArgumentException(
					"Este formato de telefone passado não pode ser aceito. Por favor, tente novamente.");

		} else {
			this.numeroTelefone = formataTextoTelefone(telefone);
		}
	}

	public TipoDDD getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		if (ddd == null) {
			throw new NullPointerException("DDD não deveria ser passado como nulo ou vazio");

		} else {
			try {
				this.ddd = (Enum.valueOf(TipoDDD.class, "DDD" + ddd));
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("O DDD " + ddd + " não é válido.");
			}
		}
	}

	public TipoTelefone getTipoTelefone() {
		return tipoTelefone;
	}

	public void setTipoTelefone(TipoTelefone tipo) {
		if (getNumeroTelefone() == null) {
			throw new NullPointerException("Você não pode setar um tipo para um telefone se o mesmo estiver vazio");

		} else {
			int tamanhoTelefone = getNumeroTelefone().replaceAll(RegexModel.FORMATATELEFONE, "").substring(2).length();
			int tamanhoFixo = TipoTelefone.RESIDENCIAL.getTamanho();
			int tamanhoCelular = TipoTelefone.CELULAR.getTamanho();

			if (tipo == TipoTelefone.COMERCIAL && (tamanhoTelefone == tamanhoFixo || tamanhoTelefone == tamanhoCelular)) {
				this.tipoTelefone = TipoTelefone.COMERCIAL;

			} else if ((tipo == null || tipo == TipoTelefone.CELULAR) && tamanhoTelefone == tamanhoCelular) {
				this.tipoTelefone = TipoTelefone.CELULAR;

			} else if ((tipo == null || tipo == TipoTelefone.RESIDENCIAL) && tamanhoTelefone == tamanhoFixo) {
				this.tipoTelefone = TipoTelefone.RESIDENCIAL;

			} else {
				throw new IllegalArgumentException(
						"O Tipo de telefone inserido não condiz com o telefone de contato informado!");
			}
		}
	}

	// Não sei onde propriamente inserir esses métodos auxiliares.
	public static String formataTextoTelefone(String telefone) {
		telefone = telefone.replaceAll("\\s", "");
		// Não permite que os espaços em brancos atrapalhem a obtenção do tamanho real
		// do telefone.

		switch (telefone.length()) { // Verifica se o contato já está com a sua formatação certa. Se não estiver,
										// formata e devolve o contato, se estiver, só retorna o contato formatado
			case 10:
				return "(" + telefone.substring(0, 2) + ")" + telefone.substring(2, 6) + "-" + telefone.substring(6);// fixo
			case 11:
				return "(" + telefone.substring(0, 2) + ")" + telefone.substring(2, 7) + "-" + telefone.substring(7); // celular
			default:
				return telefone; // Se cair aqui. O contato passado já está formatado.
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numeroTelefone == null) ? 0 : numeroTelefone.hashCode());
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
		Telefone other = (Telefone) obj;
		if (numeroTelefone == null) {
			if (other.numeroTelefone != null) {
				return false;
			}
		} else if (!numeroTelefone.equals(other.numeroTelefone)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Telefone [numeroTelefone=" + numeroTelefone + ", ddd=" + ddd + ", tipoTelefone=" + tipoTelefone + "]";
	}
}
