package br.com.contmatic.model.v2;

import java.util.EnumSet;

import static com.google.common.base.Preconditions.checkArgument;

/*
 * Classe Estado.
 * Contem a lista de todos os estados do Brasil, mais os seus respectivos nomes.
 */
public enum Estado {

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

    private static EnumSet<Estado> estadoLista = EnumSet.allOf(Estado.class); // Talvez será uma boa forma de armazenar todas as constantes
    private String nome; // nome do estado
    private String uf;

    Estado(String descricao, String uf) {
        this.nome = descricao;
        this.uf = uf;
    }
    
    /**
     * Procura a UF na lista de ENUMS.
     *
     * @param uf - A UF que deseja receber
     * @return A ENUM representante da UF procurada
     * @throws IllegalArgumentException Caso a UF inserida não corresponda com nenhuma das cadastradas.
     */
    public static Estado procuraUFEnum(String uf) {
        for(Estado st : getEstadoLista()) {
            if (st.uf.equalsIgnoreCase(uf)) { //Posso estar quebrando a regra de não utilizar um IF aqui.                
                return st; //Se for este caso, voltar para o método antigo
            }
        } 
        throw new IllegalArgumentException("A UF " + uf + " não pode ter sido encontrada."); //Se Cairmos aqui, a UF não foi encontrada
    }

    /**
     * Retorna o nome do estado da instância que chamou o método
     *
     * @return O nome da ENUM instânciada.
     */
    public String getNomeViaLista() {
        checkArgument(estadoLista.contains(this));        
        return this.nome;
    }

    /**
     * Retorna a uf da instância que chamou o método.
     *
     * @return A uf da ENUM instânciada.
     */
    public String getUfViaLista() {
        checkArgument(estadoLista.contains(this));        
        return this.uf;
    }

    public static EnumSet<Estado> getEstadoLista() {
        return estadoLista;
    }

    @Override
    public String toString() {
        return nome;
    }
}
