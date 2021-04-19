package br.com.contmatic.empresav2.validator;

public abstract class ModelAnotacao {
    
    private ModelAnotacao() { //Ideia do Sonar
        throw new IllegalStateException("Utility class");
    }     
    
    //poderia ter uma lista contendo todas os padroes aqui abaixo, e criar uma interface contendo apenas essa lista vida desta classe - não deu certo...
    
    public static final String CONTATO = "O Telefone ou Celular não pode ficar vazio";
    
    public static final String TAMANHOCONTATO = "Telefone ou Celular com tamanho inaprópriado. Não esqueça de inserir o DDD";
    
    public static final int CONTATOMIN = 13;
    
    public static final int CONTATOMAX = 14;
    
    public static final String REGEXCONTATO = "\\(\\d{2}\\)\\d{4,5}\\-\\d{4}";

    public static final String REGEXCONTATO2 = "\\(\\d{2}\\)\\d{4,5}\\-\\d{4}|Não informado";
    
    public static final String REGEXCONTATOMSG = "Contato não pode ficar nessa formatação";
    
    public static final String EMAIL = "O Email não pode ficar vazio";
    
    public static final int EMAILMAX = 55;
    
    public static final int EMAILMIN = 7;
    
    public static final String TAMANHOEMAIL = "O email não pode ter tamanho inferior a " + EMAILMIN + " ou tamanho maior que " + EMAILMAX;          

}
