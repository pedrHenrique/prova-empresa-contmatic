package br.com.contmatic.empresav2.util;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

//TODO JAutoDoc TipoContato
public enum Util {

    //TODO preciso remover tudo isso depois

                  // Enums Tipo/Quantidade de Dígitos
                  CELULAR("Celular", 11),
                  FIXO("Fixo", 10),
                  EMAIL("Email", 50);

    // Variaveis
    private String descricao;
    private int tamanho;

    private Util(String descricao, int tamanho) {
        this.descricao = descricao;
        this.tamanho = tamanho;
    }

    private Util() {

    }

    public static String formataCPF(String cpf) { //está permitindo o cpf conter caracteres além de números
        cpf = cpf.replaceAll("[\\D.-]+", ""); // Remove qualquer tipo de formatação
        checkArgument(cpf.length() == 11, "Esté CPF inserído, não é válido."); // Verifica se o tamanho correspoonde ao de um CPF
        return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9, 11); // formata o cpf
    }

    /**
     * Formata CEP formatará o cep recebido para o Padrão Ex.:"00000-000"<br>
     * Ele também verificará se o CEP obtido atrávês da formatação, condiz com o formato padrão esperado.
     *
     * @param cep - Podendo estar apenas com os números, ou separado com o hífen
     * @return o CEP já formatado
     * @throws IllegalArgumentException Caso o cep formatado não esteja no padrão esperado "00000-000".
     */
    public static String formataCEP(String cep) {
        cep = cep.replaceAll("[\\D-]+", "");
        checkArgument(cep.length() == 8, "Esté CEP inserído, não é válido.");
        return cep.substring(0, 5) + "-" + cep.substring(5, 8);
    }

    public static Util defineTipoContato(String contato) { //intencional
        switch (contato.length()) {
            case 13:
                return Util.FIXO;
            case 14:
                return Util.CELULAR;
            default:
                return Util.EMAIL;
        }
    }
}

/*
 * Remover o Formater das Clases que utilizam contato e inserir ele aqui Link para remoção de dúvidas: http://blog.triadworks.com.br/enums-sao-mais-que-constantes
 * 
 */
