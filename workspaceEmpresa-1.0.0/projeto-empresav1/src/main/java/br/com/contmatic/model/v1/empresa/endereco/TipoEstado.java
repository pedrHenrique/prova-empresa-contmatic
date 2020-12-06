package br.com.contmatic.model.v1.empresa.endereco;

import java.util.Locale;

public enum TipoEstado {

	AC("Acre", "AC", TipoPais.BRASIL),
	AL("Alagoas", "AL", TipoPais.BRASIL),
	AP("Amapá", "AP", TipoPais.BRASIL),
	AM("Amazonas", "AM", TipoPais.BRASIL),
	BA("Bahia", "BA", TipoPais.BRASIL),
	CE("Ceará", "CE", TipoPais.BRASIL),
	DF("Distrito Federal", "DF", TipoPais.BRASIL),
	ES("Espírito Santo", "ES", TipoPais.BRASIL),
	GO("Goiás", "GO", TipoPais.BRASIL),
	MA("Maranhão", "MA", TipoPais.BRASIL),
	MT("Mato Grosso", "MT", TipoPais.BRASIL),
	MS("Mato Grosso do Sul", "MS", TipoPais.BRASIL),
	MG("Minas Gerais", "MG", TipoPais.BRASIL),
	PA("Pará", "PA", TipoPais.BRASIL),
	PB("Paraíba", "PB", TipoPais.BRASIL),
	PR("Paraná", "PR", TipoPais.BRASIL),
	PE("Pernanbuco", "PE", TipoPais.BRASIL),
	PI("Piauí", "PI", TipoPais.BRASIL),
	RJ("Rio de Janeiro", "RJ", TipoPais.BRASIL),
	RN("Rio Grande do Norte", "RN", TipoPais.BRASIL),
	RS("Rio Grande do Sul", "RS", TipoPais.BRASIL),
	RO("Rondônia", "RO", TipoPais.BRASIL),
	RR("Roraima", "RR", TipoPais.BRASIL),
	SC("Santa Catarina", "SC", TipoPais.BRASIL),
	SP("São Paulo", "SP", TipoPais.BRASIL),
	SE("Sergipe", "SE", TipoPais.BRASIL),
	TO("Tocantins", "TO", TipoPais.BRASIL);

	private String nome;

	private String uf;

	private TipoPais pais;

	TipoEstado(String descricao, String uf, TipoPais pais) {
		this.nome = descricao;
		this.uf = uf;
		this.pais = pais;
	}

	public String getNome() {
		return nome;
	}

	public String getUf() {
		return uf;
	}

	public TipoPais getPais() {
		return pais;
	}
	
	public static boolean isEstadoValido(String nomeEstado, String uf) {
		try {
			return isNomeEstadoExistente(nomeEstado, uf) && isUfExistente(uf);
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	private static boolean isUfExistente(String uf) {
		return uf.equalsIgnoreCase(valueOf(uf.toUpperCase(Locale.ROOT)).getUf());
	}

	private static boolean isNomeEstadoExistente(String nomeEstado, String uf) {
		return nomeEstado.equalsIgnoreCase(valueOf(uf.toUpperCase(Locale.ROOT)).getNome());
	}
}
