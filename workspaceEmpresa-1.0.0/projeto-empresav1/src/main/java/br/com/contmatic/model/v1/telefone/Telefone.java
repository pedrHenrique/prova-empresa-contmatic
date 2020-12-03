package br.com.contmatic.model.v1.telefone;

import static br.com.contmatic.util.AtributoValidator.validaCampoDigitos;
import static br.com.contmatic.util.AtributoValidator.validaNulo;
import static br.com.contmatic.util.CamposTypes.TELEFONE_TAMANHO_FORMATACAO;
import static br.com.contmatic.util.CamposTypes.TELEFONE_TAMANHO_TELEFONE_FIXO;
import static br.com.contmatic.util.CamposTypes.TELEFONE_TAMANHO_TELEFONE_MOVEL;

public class Telefone {

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
		validaNulo(getClass(), "numeroTelefone", numeroTelefone);
		this.validaTamanhoTelefone(numeroTelefone);
		this.validaDDDTelefone(numeroTelefone);
		validaCampoDigitos(getClass(), "numeroTelefone", numeroTelefone);
		this.numeroTelefone = formataTextoTelefone(numeroTelefone);
		validaTipoTelefone();
	}

	public TipoTelefone getTipoTelefone() {
		return tipoTelefone;
	}

	public void setTipoTelefone(TipoTelefone tipoTelefone) {
		this.validaTipo(tipoTelefone);
		this.tipoTelefone = this.atribuiTipoSeNulo(tipoTelefone);
	}

	private void validaDDDTelefone(String numeroTelefone) {
		String ddd = numeroTelefone.substring(0, 2);
		try {
			Enum.valueOf(TipoDDD.class, "DDD" + ddd).getDdd();
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("O DDD " + ddd + " inserido para o telefone não existe");
		}
	}

	private void validaTamanhoTelefone(String numeroTelefone) {
		int tamanhoTelefone = numeroTelefone.length();
		if (tamanhoTelefone != TELEFONE_TAMANHO_TELEFONE_FIXO && tamanhoTelefone != TELEFONE_TAMANHO_TELEFONE_MOVEL) {
			throw new IllegalArgumentException("O tamanho do telefone inserido está incorreto.\n"
					+ "Apenas insira o DDD e o restante do número de telefone sem nenhum tipo de formatação.");
		}
	}

	private void validaTipo(TipoTelefone tipoTelefone) {
		if (!isTipoValido(tipoTelefone)) {
			throw new IllegalArgumentException("O Tipo de telefone inserido não condiz com o telefone informado.");
		}
	}

	private boolean isTipoValido(TipoTelefone tipoTelefone) {
		if (retornaTamanhoTelefone() == TELEFONE_TAMANHO_TELEFONE_FIXO) {
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

	private TipoTelefone atribuiTipoSeNulo(TipoTelefone tipoTelefone) {
		if (tipoTelefone != null) {
			return tipoTelefone;
		} else {
			return (retornaTamanhoTelefone() == TELEFONE_TAMANHO_TELEFONE_FIXO) ? TipoTelefone.RESIDENCIAL : TipoTelefone.CELULAR;
		}
	}

	private void validaTipoTelefone() {
		if (this.tipoTelefone != null) {
			atualizaTipoTelefone();
		}
	}

	private void atualizaTipoTelefone() {
		this.setTipoTelefone(null);
	}

	private int retornaTamanhoTelefone() {
		return this.getNumeroTelefone().length() - TELEFONE_TAMANHO_FORMATACAO;
	}

	public static String formataTextoTelefone(String telefone) {
		return (telefone.length() == TELEFONE_TAMANHO_TELEFONE_FIXO) ? formataTextoFixo(telefone) : formataTextoMovel(telefone);
	}

	private static String formataTextoMovel(String telefone) {
		return "(" + telefone.substring(0, 2) + ")" + telefone.substring(2, 7) + "-" + telefone.substring(7);
	}

	private static String formataTextoFixo(String telefone) {
		return "(" + telefone.substring(0, 2) + ")" + telefone.substring(2, 6) + "-" + telefone.substring(6);
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Telefone other = (Telefone) obj;
		if (numeroTelefone == null) {
			if (other.numeroTelefone != null)
				return false;
		} else if (!numeroTelefone.equals(other.numeroTelefone))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ": Numero=" + numeroTelefone + ", Tipo Telefone=" + tipoTelefone;
	}
}
