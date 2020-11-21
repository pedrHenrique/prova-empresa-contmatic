package br.com.contmatic.empresa.v1.model.contato;

public class Telefone {

	private static final int TAMANHO_DDD_E_FORMATACAO = 5;

	private static final int TAMANHO_TELEFONE_MOVEL = 11;

	private static final int TAMANHO_TELEFONE_FIXO = 10;

	private String numeroTelefone;

	private TipoTelefone tipoTelefone;

	public Telefone(String numeroTelefone, TipoTelefone tipo) {
		this.setNumeroTelefone(numeroTelefone);
		this.setTipoTelefone(tipo);
	}

	public Telefone(String numeroTelefone) {
		this.setNumeroTelefone(numeroTelefone);
		this.setTipoTelefone(null);
	}

	public String getNumeroTelefone() {
		return numeroTelefone;
	}

	public void setNumeroTelefone(String numeroTelefone) {		
		this.validaNuloTelefone(numeroTelefone);
		numeroTelefone = removeEspacamento(numeroTelefone);
		this.validaTamanhoTelefone(numeroTelefone);
		this.validaDDDTelefone(numeroTelefone);
		this.validaFormatoTelefone(numeroTelefone);
		this.numeroTelefone = formataTextoTelefone(numeroTelefone);
	}

	public TipoTelefone getTipoTelefone() {
		return tipoTelefone;
	}

	public void setTipoTelefone(TipoTelefone tipoTelefone) {
		validaTipo(tipoTelefone);
		this.tipoTelefone = tipoTelefone;
	}

	private String removeEspacamento(String numeroTelefone) {
		numeroTelefone = numeroTelefone.trim();
		return numeroTelefone;
	}

	private void validaDDDTelefone(String numeroTelefone) {
		String ddd = numeroTelefone.substring(0, 2);

		if (!ddd.equalsIgnoreCase(Enum.valueOf(TipoDDD.class, "DDD" + ddd).getDdd())) {
			throw new IllegalArgumentException("O DDD " + ddd + " inserido para o telefone não existe");
		}
	}

	private void validaTamanhoTelefone(String numeroTelefone) {
		int tamanhoTelefone = numeroTelefone.length();

		if (tamanhoTelefone != TAMANHO_TELEFONE_FIXO || tamanhoTelefone != TAMANHO_TELEFONE_MOVEL) {
			throw new IllegalArgumentException("O tamanho do telefone inserido está incorreto.\n"
					+ "Apenas insira o DDD e o restante do numero de telefone sem nenhum tipo de formatação");
		}
	}

	private void validaFormatoTelefone(String numeroTelefone) {
		int tamanhoTelefone = numeroTelefone.length();

		for (int i = 0; i < tamanhoTelefone; ++i) {
			if (!Character.isDigit(numeroTelefone.charAt(i))) {
				throw new IllegalArgumentException(
						"Este formato de telefone passado não pode ser aceito. Por favor, tente novamente.");
			}
		}
	}

	private void validaNuloTelefone(String telefone) {
		if (telefone == null) {
			throw new NullPointerException("Telefone não pode ser nulo");
		}
	}

	private void validaTipo(TipoTelefone tipoTelefone) {
		if (!isTipoValido(tipoTelefone)) {
			throw new IllegalArgumentException("O Tipo inserido não é válido");
		}
	}

	private boolean isTipoValido(TipoTelefone tipoTelefone) {
		int tamanhoTelefone = this.getNumeroTelefone().length() - TAMANHO_DDD_E_FORMATACAO;
		int tamanhoFixo = TipoTelefone.RESIDENCIAL.getTamanho();

		if (tamanhoTelefone == tamanhoFixo) {
			return validaFixo(tipoTelefone);
		} else {
			return validaMovel(tipoTelefone);
		}
	}

	private boolean validaMovel(TipoTelefone tipoTelefone) {
		return tipoTelefone == TipoTelefone.CELULAR || tipoTelefone == null;
	}

	private boolean validaFixo(TipoTelefone tipoTelefone) {
		return tipoTelefone == TipoTelefone.RESIDENCIAL || tipoTelefone == null;
	}

	public static String formataTextoTelefone(String telefone) {
		if (telefone.length() == 10) {
			return "(" + telefone.substring(0, 2) + ")" + telefone.substring(2, 6) + "-" + telefone.substring(6);
		} else {
			return "(" + telefone.substring(0, 2) + ")" + telefone.substring(2, 7) + "-" + telefone.substring(7);
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
		return "Telefone [Numero: " + numeroTelefone + " Tipo Telefone: " + tipoTelefone + "]";
	}
}
