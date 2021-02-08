package br.com.contmatic.model.v1.telefone;

import static br.com.contmatic.model.v1.telefone.DDIType.DDI55;
import static br.com.contmatic.model.v1.telefone.TelefoneType.CELULAR_NACIONAL;
import static br.com.contmatic.model.v1.telefone.TelefoneType.INTERNACIONAL;
import static br.com.contmatic.model.v1.telefone.TelefoneType.RESIDENCIAL_NACIONAL;
import static br.com.contmatic.util.v1.CamposTypes.TELEFONE_TAMANHO_DDD;
import static br.com.contmatic.util.v1.CamposTypes.TELEFONE_TAMANHO_FORMATACAO;
import static br.com.contmatic.util.v1.CamposTypes.TELEFONE_TAMANHO_TELEFONE_FIXO;
import static br.com.contmatic.util.v1.CamposTypes.TELEFONE_TAMANHO_TELEFONE_INTERNACIONAL_MAX;
import static br.com.contmatic.util.v1.CamposTypes.TELEFONE_TAMANHO_TELEFONE_INTERNACIONAL_MIN;
import static br.com.contmatic.util.v1.CamposTypes.TELEFONE_TAMANHO_TELEFONE_MOVEL;
import static br.com.contmatic.util.v1.validator.NumericValidator.validaTamanho;
import static br.com.contmatic.util.v1.validator.StringValidator.validaEspacamento;
import static br.com.contmatic.util.v1.validator.StringValidator.validaNulo;
import static br.com.contmatic.util.v1.validator.StringValidator.verificaSeCampoSoPossuiDigitos;


public class Telefone {
	
	public static final String CAMPO_TELEFONE = "numero";

	private DDIType ddi;
	
	private String ddd;

	private String numero;

	private TelefoneType tipo;

	public Telefone(DDIType ddi, String ddd, String numero, TelefoneType tipo) {
		this.setDdi(ddi);
		this.setDdd(ddd);
		this.setNumero(numero);
		this.setTipo(tipo);
	}
	
	public Telefone(DDIType ddi, String numero, TelefoneType tipo) {
		this.setDdi(ddi);
		this.setDdd(null);
		this.setNumero(numero);
		this.setTipo(tipo);
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.validaNumero(numero);
		this.numero = this.formataTextoTelefoneNacional(numero.trim());
		this.atualizaTipoTelefoneSeNecessario();
	}

	public TelefoneType getTipo() {
		return tipo;
	}

	public void setTipo(TelefoneType tipoTelefone) {
		this.validaTipo(tipoTelefone);
		this.tipo = this.atribuiTipoSeNulo(tipoTelefone);
	}
	
	public DDIType getDdi() {
		return ddi;
	}
	
	public void setDdi(DDIType ddi) {
		validaNulo(getClass(), "ddi", ddi);	
		this.ddi = ddi;
	}
	
	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = validaDDDSeNacionalAtribuiNuloSeInternacional(ddd);
	}

	private String validaDDDSeNacionalAtribuiNuloSeInternacional(String ddd) {
		return (this.isTelefoneNacional()) ? validaDDD(ddd) : null;
	}

	private void validaNumero(String numero) {
		validaNulo(getClass(), CAMPO_TELEFONE, numero);
		this.validaTamanhoTelefone(numero);
		validaEspacamento(getClass(), CAMPO_TELEFONE, numero, TELEFONE_TAMANHO_TELEFONE_INTERNACIONAL_MIN);
		verificaSeCampoSoPossuiDigitos(getClass(), CAMPO_TELEFONE, numero);
	}
	
	private String validaDDD(String ddd) {
		validaNulo(getClass(), "ddd", ddd);
		validaTamanho(getClass(), "ddd", ddd.length(), TELEFONE_TAMANHO_DDD);
		validaEspacamento(getClass(), "ddd", ddd, TELEFONE_TAMANHO_DDD);
		this.validaDDDTelefoneNacional(ddd);
		return ddd;
	}

	private void validaDDDTelefoneNacional(String ddd) {
		try {
			verificaSeDDDInformadoExiste(ddd);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("O DDD " + ddd + " inserido para o telefone nacional não existe");
		}
	}

	private void verificaSeDDDInformadoExiste(String ddd) {
		Enum.valueOf(DDDType.class, "DDD" + ddd).getDdd();
	}

	private void validaTamanhoTelefone(String numero) {
		int tamanhoTelefone = numero.length();
		if (isTamanhoTelefoneNacionalInvalido(tamanhoTelefone)) {
			throw new IllegalArgumentException("O tamanho do telefone inserido está incorreto.\nPara telefones nacionais, apenas insira o DDD e o restante do número sem nenhum tipo de formatação.");
		} else if (isTamanhoTelefoneInternacionalInvalido(tamanhoTelefone)) {
			throw new IllegalArgumentException("O tamanho do telefone inserido está incorreto.\nPara telefones internacionais, apenas insira o telefone sem nenhum tipo de formatação, tendo no mínimo 3 e no máximo 15 dígitos.");
		}
	}

	private boolean isTamanhoTelefoneNacionalInvalido(Integer tamanhoTelefone) {
		return (tamanhoTelefone != TELEFONE_TAMANHO_TELEFONE_FIXO && tamanhoTelefone != TELEFONE_TAMANHO_TELEFONE_MOVEL) && this.isTelefoneNacional();
	}
	
	private boolean isTamanhoTelefoneInternacionalInvalido(int tamanhoTelefone) {
		return tamanhoTelefone < TELEFONE_TAMANHO_TELEFONE_INTERNACIONAL_MIN || tamanhoTelefone > TELEFONE_TAMANHO_TELEFONE_INTERNACIONAL_MAX;
	}

	private void validaTipo(TelefoneType tipoTelefone) {
		if (!isTipoValido(tipoTelefone)) {
			throw new IllegalArgumentException("O Tipo de telefone inserido não condiz com o telefone informado.\nSe você estiver inserindo um telefone internacional, você precisa informar o seu tipo como internacional.");
		}
	}

	private boolean isTipoValido(TelefoneType tipoTelefone) {
		if (this.isTelefoneNacional()) {
			return this.isThisTelefoneFixo() ? this.validaTipoFixo(tipoTelefone) : this.validaTipoMovel(tipoTelefone);			
		} else {
			return this.validaTipoInternacional(tipoTelefone);
		}			
	}

	private boolean isThisTelefoneFixo() {
		return this.retornaTamanhoTelefone() == TELEFONE_TAMANHO_TELEFONE_FIXO;
	}

	private boolean validaTipoInternacional(TelefoneType tipoTelefone) {
		return tipoTelefone == INTERNACIONAL;
	}

	private boolean validaTipoMovel(TelefoneType tipoTelefone) {
		return (tipoTelefone == CELULAR_NACIONAL || tipoTelefone == null);
	}

	private boolean validaTipoFixo(TelefoneType tipoTelefone) {
		return (tipoTelefone == RESIDENCIAL_NACIONAL || tipoTelefone == null);
	}

	private TelefoneType atribuiTipoSeNulo(TelefoneType tipoTelefone) {
		if (tipoTelefone != null) {
			return tipoTelefone;
		} else {
			return (retornaTamanhoTelefone() == TELEFONE_TAMANHO_TELEFONE_FIXO) ? RESIDENCIAL_NACIONAL : CELULAR_NACIONAL;
		}
	}

	private void atualizaTipoTelefoneSeNecessario() {
		if (this.jaExisteUmTipoTelefoneCadastrado()) {
			this.atualizaTipoTelefone();
		}
	}

	private boolean jaExisteUmTipoTelefoneCadastrado() {
		return this.tipo != null;
	}

	private void atualizaTipoTelefone() {
		this.setTipo(retornaTipoTelefone());
	}
	
	private TelefoneType retornaTipoTelefone() {
		return (isThisTelefoneFixo()) ? RESIDENCIAL_NACIONAL : CELULAR_NACIONAL;
	}

	private int retornaTamanhoTelefone() {
		return this.getNumero().length() - TELEFONE_TAMANHO_FORMATACAO;
	}

	public String formataTextoTelefoneNacional(String telefone) {		
		if (isTelefoneNacional()) {
			return (telefone.length() == TELEFONE_TAMANHO_TELEFONE_FIXO) ? formataTextoTelefoneFixo(telefone) : formataTextoTelefoneMovel(telefone);
		} else {
			return telefone;
		}
	}

	private static String formataTextoTelefoneMovel(String telefone) {
		return new StringBuilder(13).append(telefone.substring(0, 5))
									.append("-")
									.append(telefone.substring(5)).toString();
	}

	private static String formataTextoTelefoneFixo(String telefone) {
		return new StringBuilder(12).append(telefone.substring(0, 4))
									.append("-")
									.append(telefone.substring(4)).toString();
	}
	
	private String retornaToStringTelefoneInternacional() {
		return new StringBuilder().append(getClass().getSimpleName())
								  .append(": DDI=").append(ddi.getDdi())
								  .append(", Numero=").append(numero)
								  .append(", Tipo=").append(tipo).toString();
	}

	private String retornaToStringTelefoneNacional() {
		return new StringBuilder().append(getClass().getSimpleName())
								  .append(": DDI=").append(ddi.getDdi())
								  .append(", DDD=").append(getDdd())
								  .append(", Numero=").append(numero)
								  .append(", Tipo=").append(tipo).toString();
	}
	
	private boolean isTelefoneNacional() {
		return this.getDdi() == DDI55;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
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
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}

	@Override
	public String toString() { 
		return (this.isTelefoneNacional()) ? this.retornaToStringTelefoneNacional() : this.retornaToStringTelefoneInternacional(); 			
	}
}
