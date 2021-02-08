package br.com.contmatic.model.v1.endereco;

import java.util.Locale;

public enum EstadoType {

	AC("Acre", "AC", PaisType.BRASIL),
	AL("Alagoas", "AL", PaisType.BRASIL),
	AP("Amapá", "AP", PaisType.BRASIL),
	AM("Amazonas", "AM", PaisType.BRASIL),
	BA("Bahia", "BA", PaisType.BRASIL),
	CE("Ceará", "CE", PaisType.BRASIL),
	DF("Distrito Federal", "DF", PaisType.BRASIL),
	ES("Espírito Santo", "ES", PaisType.BRASIL),
	GO("Goiás", "GO", PaisType.BRASIL),
	MA("Maranhão", "MA", PaisType.BRASIL),
	MT("Mato Grosso", "MT", PaisType.BRASIL),
	MS("Mato Grosso do Sul", "MS", PaisType.BRASIL),
	MG("Minas Gerais", "MG", PaisType.BRASIL),
	PA("Pará", "PA", PaisType.BRASIL),
	PB("Paraíba", "PB", PaisType.BRASIL),
	PR("Paraná", "PR", PaisType.BRASIL),
	PE("Pernanbuco", "PE", PaisType.BRASIL),
	PI("Piauí", "PI", PaisType.BRASIL),
	RJ("Rio de Janeiro", "RJ", PaisType.BRASIL),
	RN("Rio Grande do Norte", "RN", PaisType.BRASIL),
	RS("Rio Grande do Sul", "RS", PaisType.BRASIL),
	RO("Rondônia", "RO", PaisType.BRASIL),
	RR("Roraima", "RR", PaisType.BRASIL),
	SC("Santa Catarina", "SC", PaisType.BRASIL),
	SP("São Paulo", "SP", PaisType.BRASIL),
	SE("Sergipe", "SE", PaisType.BRASIL),
	TO("Tocantins", "TO", PaisType.BRASIL);

	private String nome;

	private String uf;

	private PaisType pais;

	EstadoType(String descricao, String uf, PaisType pais) {
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

	public PaisType getPais() {
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
