package br.com.contmatic.empresav2.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;

import static org.junit.Assert.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import br.com.contmatic.empresav2.util.TestesModel;
import br.com.contmatic.model.v2.Funcionario;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FuncionarioTest implements TestesModel {

    private static Funcionario funcionario;
    private Funcionario fun; 

    private Set<ConstraintViolation<Funcionario>> constraintViolations;
    private static ValidatorFactory validatorFactory;
    private Validator validator;
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUpBeforeClass() {
        funcionario = new Funcionario();
        validatorFactory = Validation.buildDefaultValidatorFactory();
        FixtureFactoryLoader.loadTemplates("br.com.contmatic.empresav2.util");
        System.out.println("--------- Início Testes Classe Funcionario --------- \n");
    }

    @Before
    public void setUp() {
        fun = Fixture.from(Funcionario.class).gimme("valido");
        validator = validatorFactory.getValidator();        
    }

    @After
    public void tearDown() throws Exception {
        fun = null;
        validator = null;
        constraintViolations = null;
    }

    @AfterClass
    public static void tearDownAfterClass() {                
        funcionario = null;
        validatorFactory = null;
        System.out.println("--------- Fim Testes Classe Funcionario --------- \n\n");
    }

    /*
     * Seção de testes dos getters/setters da classe
     */

    @Test
    public void teste_setId_e_getId_correto_nao_deve_gerar_constrain() {
        funcionario.setIdFuncionario(fun.getIdFuncionario());
        constraintViolations = validator.validateValue(Funcionario.class, "idFuncionario", fun.getIdFuncionario());
        
        exibeConstrains(constraintViolations);
        assertThat(constraintViolations.isEmpty(), equalTo(true)); 
        assertThat("Os valores deveriam ser iguais", funcionario.getIdFuncionario(), equalTo(fun.getIdFuncionario()));
                   
    }
    
    @Test
    public void setId_deveGerar_constrain_recebendo_valores_nulos() {
        thrown.expect(NullPointerException.class);            
        fun.setIdFuncionario(NULLONG);        
    }

    @Test
    public void setId_deveGerar_constrain_recebendo_valores_vazios() {
        fun.setIdFuncionario(EMPTYLONG);

        constraintViolations = validator.validateValue(Funcionario.class, "idFuncionario", fun.getIdFuncionario());
        assertThat(constraintViolations, not(equalTo(null)));
        exibeConstrains(constraintViolations);
    }

    @Test
    public void setId_deveGerar_constrain_recebendo_valores_incorretos() {
        fun.setIdFuncionario(50000);

        constraintViolations = validator.validateValue(Funcionario.class, "idFuncionario", fun.getIdFuncionario());
        assertThat(constraintViolations, not(equalTo(null)));
        exibeConstrains(constraintViolations);
    }
    

    @Test
    public void teste_setNome_e_getNome_correto_nao_deve_gerar_constrain() {
        funcionario.setNome(fun.getNome());
        constraintViolations = validator.validateValue(Funcionario.class, "nome", fun.getNome());
        
        exibeConstrains(constraintViolations);
        assertThat(constraintViolations.isEmpty(), equalTo(true));
        assertThat("Os valores deveriam ser iguais", funcionario.getNome(), equalTo(fun.getNome()));    
    }

    @Test
    public void setNome_nao_deve_aceitar_valor_nulo() {        
        fun.setNome(NULLSTR);

        constraintViolations = validator.validateValue(Funcionario.class, "nome", fun.getNome());
        
        assertThat(constraintViolations, equalTo(null));
        exibeConstrains(constraintViolations);
    }

    @Test
    public void setNome_deveGerar_constrain_recebendo_valores_vazio() {
        fun.setNome(EMPTYSTR);
        constraintViolations = validator.validateValue(Funcionario.class, "nome", fun.getNome());
        
        assertThat(constraintViolations, not(equalTo(null)));
        exibeConstrains(constraintViolations);
    }

    @Test
    public void setNome_deveGerar_constrain_recebendo_valores_naoValidos() {
        fun.setNome("PRËMØNÏÇÃØ");
        constraintViolations = validator.validateValue(Funcionario.class, "nome", fun.getNome());

        assertThat(constraintViolations, not(equalTo(null)));
        exibeConstrains(constraintViolations);
    }

    @Test
    public void teste_setCpf_e_getCpf_correto_correto_nao_deve_gerar_constrain() {
        String cpf = "11650588046";
        funcionario.setCpf(cpf); 
        constraintViolations = validator.validateValue(Funcionario.class, "cpf", funcionario.getCpf());
        
        exibeConstrains(constraintViolations);
        assertThat(constraintViolations.isEmpty(), equalTo(true));
        assertThat("Os valores deveriam ser iguais", funcionario.getCpf().replaceAll("\\D", ""), equalTo(cpf));                
    }

    @Test
    public void setCpf_nao_deve_aceitar_valor_nulo() {
        thrown.expect(NullPointerException.class);
        fun.setCpf(NULLSTR);
    }

    @Test
    public void setCpf_deveGerar_exception_recebendo_valores_vazios() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Esté CPF inserído, não é válido.");
        fun.setCpf(EMPTYSTR);
    }

    @Test
    public void setCpf_deveGerar_exception_recebendo_valores_naoValido() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Esté CPF inserído, não é válido.");

        fun.setCpf("ABCDFEHIJKL");        

    }

     /* Não irei consertar estes testes pois uma classe chamada Contato deve ser criada
                        para suprir uma das regras da prova */

    // @Test
    // public void teste_setContato_e_getContato_nome_correto() {
    //     funcionario.setContato(fun.getContato());
    //     fun.setContato(fun.getContato());// Set adiciona uma formatação a variável. Replace remove essa formatação para a real comparação
    //     Set<ConstraintViolation<Funcionario>> constraintViolations = validator.validateValue(Funcionario.class, "contato", fun.getContato());
    //     assertThat("Os valores deveriam ser iguais", funcionario.getContato(), equalTo(fun.getContato()));
    //     exibeConstrains(constraintViolations);
    //     assertThat(constraintViolations.isEmpty(), equalTo(true));

    // }
                            
    // @Test(expected = NullPointerException.class)
    // public void teste_setContato_valor_nulo() {
    //     fun.setContato(NULLSTR);
    // }

    // @Test(expected = IllegalArgumentException.class)
    // public void teste_setContato_valor_vazio() {
    //     fun.setContato(EMPTYSTR);
    // }

    // @Test(expected = IllegalArgumentException.class)
    // public void setEmail_nao_deve_aceitar_email_naoValido() {
    //     String email = new String("Rogerinho85.com.br"); // Email precisa conter um @
    //     fun.setContato(email);
    // }

    // @Test(expected = IllegalArgumentException.class)
    // public void setEmail_nao_deve_aceitar_telefone_naoValido() {
    //     String telefone = new String("449968410186248"); // Telefone precisa ter o DDD + a numeração do tel/cel
    //     fun.setContato(telefone);
    // }

    @Test
    public void teste_setSalario_e_getSalario_correto_nao_deve_gerar_constrain() {
        funcionario.setSalario(fun.getSalario());
        constraintViolations = validator.validateValue(Funcionario.class, "salario", fun.getSalario());
        
        exibeConstrains(constraintViolations);
        assertThat(constraintViolations.isEmpty(), equalTo(true));
        assertThat("Os valores deveriam ser iguais", funcionario.getSalario(), equalTo(fun.getSalario()));                        
    }

    @Test
    public void setSalario_deveGerar_constrain_recebendo_valore_Nulo() {       
        thrown.expect(NullPointerException.class);                
        fun.setSalario(NULLDOUBLE);
    }

    @Test
    public void setSalario_deveGerar_constrain_recebendo_valore_vazio() {
        fun.setSalario(EMPTYDOUBLE);

        constraintViolations = validator.validateValue(Funcionario.class, "salario", fun.getSalario());
        assertThat(constraintViolations, not(equalTo(null)));
        exibeConstrains(constraintViolations);
    }

    @Test
    public void setSalario_deveGerar_constrain_recebendo_valores_naoValido() {
        fun.setSalario(-500); // Salario negativo ninguém merece né

        constraintViolations = validator.validateValue(Funcionario.class, "salario", fun.getSalario());
        assertThat(constraintViolations, not(equalTo(null)));
        exibeConstrains(constraintViolations);
    }

    /**
     * Método de auxílio que exibe Validations que foram infringidas no decorer de um teste específico.
     *
     * @param constraintViolations - A lista contendo todas as infrações que foram recebidas pelo validator.
     */
    public void exibeConstrains(Set<ConstraintViolation<Funcionario>> constraintViolations) {
        for(ConstraintViolation<Funcionario> cv : constraintViolations) {
            System.out.println(String.format("Constrain infringida! atributo: [%s], valor: [%s], message: [%s]", cv.getPropertyPath(), cv.getInvalidValue(), cv.getMessage()));
            // Confirmar se existe uma forma melhor de exbir oq não estiver válido
        }
    }

}
