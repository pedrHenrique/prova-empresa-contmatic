package br.com.contmatic.model.v1.empresa;

import static br.com.contmatic.util.validator.StringValidator.validaEspacamento;
import static br.com.contmatic.util.validator.StringValidator.validaNomeSimbolos;
import static br.com.contmatic.util.validator.StringValidator.validaNulo;
import static br.com.contmatic.util.validator.StringValidator.verificaSeCampoSoPossuiDigitos;
import static br.com.contmatic.util.validator.NumericValidator.validaTamanho;
import static br.com.contmatic.util.CamposTypes.DEPARTAMENTO_NOME_TAMANHO_MAX;
import static br.com.contmatic.util.CamposTypes.DEPARTAMENTO_NOME_TAMANHO_MIN;
import static br.com.contmatic.util.CamposTypes.DEPARTAMENTO_RAMAL_TAMANHO_MAX;
import static br.com.contmatic.util.CamposTypes.DEPARTAMENTO_RAMAL_TAMANHO_MIN;
import static br.com.contmatic.util.CamposTypes.DEPARTAMENTO_ID_TAMANHO_MAX;
import static br.com.contmatic.util.CamposTypes.DEPARTAMENTO_ID_TAMANHO_MIN;

public class Departamento {

	private static final String RAMAL_NOME_CAMPO = "ramal";

	private Long id;

	private String nome;

	private String ramal;

	public Departamento(Long id, String nome, String ramal) {
		this.setId(id);
		this.setNome(nome);
		this.setRamal(ramal);
	}

	public Departamento(Long id, String nome) {
		this.setId(id);
		this.setNome(nome);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		validaNulo(getClass(), "id", id);
		validaTamanho(getClass(), "id", id.intValue(), (int) DEPARTAMENTO_ID_TAMANHO_MIN,
				(int) DEPARTAMENTO_ID_TAMANHO_MAX);
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		validaNulo(getClass(), "nome", nome);
		validaEspacamento(getClass(), "nome", nome, DEPARTAMENTO_NOME_TAMANHO_MIN);
		validaTamanho(getClass(), "nome", nome.length(), DEPARTAMENTO_NOME_TAMANHO_MIN, DEPARTAMENTO_NOME_TAMANHO_MAX);
		validaNomeSimbolos(getClass(), "nome", nome);
		this.nome = nome;
	}

	public String getRamal() {
		return ramal;
	}

	public void setRamal(String ramal) {
		validaNulo(getClass(), RAMAL_NOME_CAMPO, ramal);
		validaTamanho(getClass(), RAMAL_NOME_CAMPO, ramal.length(), DEPARTAMENTO_RAMAL_TAMANHO_MIN, DEPARTAMENTO_RAMAL_TAMANHO_MAX);
		verificaSeCampoSoPossuiDigitos(getClass(), RAMAL_NOME_CAMPO, ramal);
		this.ramal = ramal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Departamento other = (Departamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ": ID=" + id + ", Nome=" + nome + ", Ramal=" + ramal;
	}
}
