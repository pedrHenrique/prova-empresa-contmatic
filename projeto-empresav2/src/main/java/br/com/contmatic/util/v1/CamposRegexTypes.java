package br.com.contmatic.util.v1;

public final class CamposRegexTypes {
    
    /**
     * Permite nomes que não comecem com números/under_score/espaços em brancos. <p>
     * Permite letras, numeros, acentos, ponto, e & comercial.
     */
    public static final String NOMEFANTASIA = "[^\\s\\W_0-9][\\wáéíóúÁÉÍÓÚâêîôûÂÊÎÔÛàèìòùÀÈÌÒÙãõÃÕçÇ \\.&]*";
    
    /** Permite letras e letras acentuadas. Não permite símbolos ou números */
    public static final String NOMESIMPLES = "[a-zA-záéíóúÁÉÍÓÚâêîôûÂÊÎÔÛàèìòùÀÈÌÒÙãõÃÕçÇ ]+";
    
    /** Permite letras e números no primeiro grupo, seguido de um <b>'@'</b> obrigatório. <p>
     * O restante do email após o arroba precisa ser seguido de um ponto <b>'.'</b>. Tendo que obrigatóriamente especificar um domínio reconhecido. Exemplo:<p>
     * <code>net,org,com,br</code><p>
     * Podendo ser finalizando com uma continuação de caracteres opcional de a até z..
     */
    public static final String EMAIL = "([\\w_]+@[\\w]+)\\.(com|org|net|br)+[\\.a-z]*";
    
    /** Permite a passagem de um número tanto de celular quanto de telefone, podendo já estar corretamente formatado de diversas formas. */
    public static final String TELEFONECELULAR = "\\(\\d{2}\\)\\d{4,5}\\-\\d{4}|\\d{10,11}|\\(\\d{2}\\)\\s\\d{4,5}\\-\\d{4}|\\d{2}\\s\\d{4,5}[-]*[ ]?\\d{4}";
    
    public static final String FORMATATELEFONE = "[\\D\\s]*";
    
    
    private CamposRegexTypes() {
        
    }

}
