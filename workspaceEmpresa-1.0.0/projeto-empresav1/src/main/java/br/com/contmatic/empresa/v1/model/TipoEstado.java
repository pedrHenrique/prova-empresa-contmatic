package br.com.contmatic.empresa.v1.model;

/**
 * Enumerador TipoEstado.<p>
 * Contem a lista de todos os estados do Brasil, mais os seus respectivos nomes e seus UF's.
 */
public enum TipoEstado {

        // Definição de Todos os estados mais o seu nome
        AC("Acre", "AC"),
        AL("Alagoas", "AL"),
        AP("Amapá", "AP"),
        AM("Amazonas", "AM"),
        BA("Bahia", "BA"),
        CE("Ceará", "CE"),
        DF("Distrito Federal", "DF"),
        ES("Espírito Santo", "ES"),
        GO("Goiás", "GO"),
        MA("Maranhão", "MA"),
        MT("Mato Grosso", "MT"),
        MS("Mato Grosso do Sul", "MS"),
        MG("Minas Gerais", "MG"),
        PA("Pará", "PA"),
        PB("Paraíba", "PB"),
        PR("Paraná", "PR"),
        PE("Pernanbuco", "PE"),
        PI("Piauí", "PI"),
        RJ("Rio de Janeiro", "RJ"),
        RN("Rio Grande do Norte", "RN"),
        RS("Rio Grande do Sul", "RS"),
        RO("Rondônia", "RO"),
        RR("Roraima","RR"),
        SC("Santa Catarina", "SC"),
        SP("São Paulo", "SP"),
        SE("Sergipe", "SE"),
        TO("Tocantins", "TO"),
        NA("Não Informado", "NA");

	private String nome; // nome do estado
    private String uf;

    TipoEstado(String descricao, String uf) {
        this.nome = descricao;
        this.uf = uf;
    }
    
    public String getNome() {
		return nome;
	}

	public String getUf() {
		return uf;
	}

	/**
     * Procura a UF na lista de ENUMS.
     *
     * @param uf - A UF que deseja receber
     * @return A UF encontrada
     * @throws IllegalArgumentException Caso a UF inserida não corresponda com nenhuma das cadastradas.
     */
    public static TipoEstado procuraUFEnum(String uf) {
        for(TipoEstado st : TipoEstado.values()) {
            if (st.uf.equalsIgnoreCase(uf)) { 
                return st; 
            }
        } 
        throw new IllegalArgumentException("A UF " + uf + " não pôde ter sido encontrada."); //Se Cairmos aqui, a UF não foi encontrada
    }

}
