package br.com.contmatic.empresav2.util;

/**
 * Simples interface que está servindo como modelo para utilização de algumas constantes utilizadas por grande maioria das classes de teste.
 * <p>
 * 
 */
public interface TestesModel {

    public enum TipoTemplate { //Para o Fixture Factory

                              VALIDO("valido"),
                              INVALIDO("invalido");

        private String template;

        TipoTemplate(String template) {
            this.template = template;
        }

        public String getTemplate() {
            return this.template;
        }
    }

    public String CONSTRAINT_VIOLATED = "Restrições foram violadas.";
    public String FAILCODE_MESSAGE = "Execução do teste não deveria ter chego até esse ponto.";
    public Object NULLOBJ = null;
    public String NULLSTR = null;
    public String EMPTYSTR = "";
    public Long NULLONG = null;
    public Long EMPTYLONG = (long) 0;
    public Double NULLDOUBLE = null;
    public Double EMPTYDOUBLE = 0.0;
    public int EMPTYINTEGER = 0;
    public Integer NULLINTEGER = null;
}
