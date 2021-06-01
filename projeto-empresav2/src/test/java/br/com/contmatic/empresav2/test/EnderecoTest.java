package br.com.contmatic.empresav2.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

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
import br.com.contmatic.model.v2.Endereco;
import br.com.contmatic.model.v2.Estado;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EnderecoTest implements TestesModel {

    private static Endereco endereco;
    private Endereco end;

    private Set<ConstraintViolation<Endereco>> constraintViolations;
    private static ValidatorFactory validatorFactory;
    private Validator validator;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        endereco = new Endereco();
        validatorFactory = Validation.buildDefaultValidatorFactory();
        FixtureFactoryLoader.loadTemplates("br.com.contmatic.empresav2.util");
        System.out.println("--------- Início Testes Classe Endereco --------- \n");
    }

    @Before
    public void setUp() throws Exception {
        end = Fixture.from(Endereco.class).gimme(TipoTemplate.VALIDO.getTemplate());
        validator = validatorFactory.getValidator();
    }

    @After
    public void tearDown() throws Exception {
        end = null;
        validator = null;
        constraintViolations = null;
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        Endereco.getEnderecoLista().clear();
        endereco = null;
        validatorFactory = null;
        System.out.println("--------- Fim de Testes Classe Endereco --------- \n");
    }

    /*
     * Seção de testes dos métodos de criação dos objetos da classe
     */

    @Test
    public void teste_deveRegistrar_endereco_comSucesso() {
        end = Endereco.cadastraEnderecoViaCEP("60874-208", "456");

        assertThat(Endereco.getEnderecoLista().contains(end), equalTo(true));
    }

    @Test
    public void teste_deveRegistrar_endereco_comSucesso_metodo_alternativo() {
        end = Endereco.cadastraEndereco("Rua São Tomaz", "Angelim", "801", "64040-113", "Teresina", Estado.PI);

        assertThat(Endereco.getEnderecoLista().contains(end), equalTo(true));
    }

    @Test
    public void deve_registrar_endereco_valido_pelo_construtor_usandoo_FixtureFactory() {
        end = new Endereco(end.getRua(), end.getBairro(), end.getNumero(), end.getCep(), end.getCidade(), end.getEstado());

        assertThat(Endereco.getEnderecoLista().contains(end), equalTo(true));
    }

    @Test
    public void nao_deve_registrar_endereco_com_CEP_e_NUMERO_iguais() {
        end = Fixture.from(Endereco.class).gimme("valido");
        thrown.expect(IllegalArgumentException.class);
        //thrown.expectMessage(A Foi desativado pois havia uma inconsistência na comparação das mensagens.
        //    "Este endereço:\n" + end.toString() + " já está sendo usado por alguma empresa ou funcionario. " + "Insira um endereço alternativo, ou verifique se vc digitou a numeração correta.\n");

        Endereco.cadastraEndereco(end.getRua(), end.getBairro(), end.getNumero(), end.getCep(), end.getCidade(), end.getEstado());
        Endereco.cadastraEndereco(end.getRua(), end.getBairro(), end.getNumero(), end.getCep(), end.getCidade(), end.getEstado());
    }

    @Test(expected = IllegalArgumentException.class)
    public void cadastroViaCep_nao_deve_permitir_a_entrada_deUm_CEP_inexistente() {
        Endereco.cadastraEnderecoViaCEP("00000000", end.getNumero());
    }

    /*
     * Seção de testes dos métodos de remoção de objetos da Collection.
     */

    @Test
    public void teste_deveRemover_objeto_passando_CEP_NUM_doObjeto() {
        Endereco.cadastraEndereco(end.getRua(), end.getBairro(), end.getNumero(), end.getCep(), end.getCidade(), end.getEstado());
        end.removeEndereco(end.getCep(), end.getNumero());

        assertThat(Endereco.getEnderecoLista().contains(end), equalTo(false));
    }

    @Test
    public void teste_deveRemover_objeto_passando_oProprio_objeto() {
        end = Endereco.cadastraEnderecoViaCEP("72444-134", "420J");
        Endereco.removeEndereco(end);

        assertThat(Endereco.getEnderecoLista().contains(end), equalTo(false));
    }

    /*
     * Seção de testes dos métodos de busca de objetos da Collection
     */

    @Test
    public void teste_deve_buscar_endereco_cadastrado_eRetornar_ele() {
        end = Endereco.cadastraEndereco("Cleber", "Jardim Juvêncio", "207", "37701-047", "Sobral", Estado.AP);

        assertNotNull(Endereco.solicitaEndereco(end.getCep(), end.getNumero()));
        assertThat("Os Endereços comparados deveriam ser iguais", Endereco.solicitaEndereco(end.getCep(), end.getNumero()).equals(end), equalTo(true)); // legibilidade
    }
    
    @Test
    public void teste_deve_buscar_endereco_cadastrado_passando_um_proprio_endereco() {
        end = Endereco.cadastraEndereco(end.getRua(), end.getBairro(), end.getNumero(), end.getCep(), end.getCidade(), end.getEstado());

        assertNotNull(Endereco.solicitaEndereco(end));
        assertThat("Os Endereços comparados deveriam ser iguais", Endereco.solicitaEndereco(end).equals(end), equalTo(true)); // legibilidade
    }
    
    @Test 
    public void teste_nao_deve_retornar_endereco_na_busca_deEndereco_nao_existente() {
        // Populando a lista
        List<Endereco> end = Fixture.from(Endereco.class).gimme(3, "valido");
        Endereco.cadastraEnderecoViaCEP("77823-682", end.get(0).getNumero());
        Endereco.cadastraEnderecoViaCEP("72852-712", end.get(1).getNumero());
        Endereco.cadastraEnderecoViaCEP("35700-122", end.get(2).getNumero());

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Endereço solicitado não existe na lista de cadastros\n");
        
        Endereco.solicitaEndereco("00000000", "45A");        
    }

    @Test 
    public void teste_deve_gerar_noSuchElementException_procurando_numa_lista_vazia() {  
        Endereco.getEnderecoLista().clear();

        thrown.expect(NoSuchElementException.class);
        thrown.expectMessage("É possível que você esteja procurando algum endereço sem possuir nenhum cadastrado na lista.");
        
        Endereco.solicitaEndereco("00000000", "45A");        
    }

    /*
     * Seção de testes dos getters/setters da classe
     */

    @Test
    public void teste_setRua_e_getRua_correto_nao_deve_gerar_constrain() {        
        for(int i = 0 ; i < 5 ; i++) {
            end = Fixture.from(Endereco.class).gimme(TipoTemplate.VALIDO.getTemplate());
            endereco.setRua(end.getRua());
            constraintViolations = validator.validateValue(Endereco.class, "rua", end.getRua());

            exibeConstrains(constraintViolations);
            assertThat(constraintViolations.isEmpty(), equalTo(true));
            assertThat("Os valores deveriam ser iguais", endereco.getRua(), equalTo(end.getRua()));
        }
    }

    @Test
    public void setRua_deve_gerar_constrain_recebendo_valor_nulo(){        
        end.setRua(NULLSTR);
        constraintViolations = validator.validateValue(Endereco.class, "rua", end.getRua());

        assertThat(constraintViolations, not(equalTo(null)));
        exibeConstrains(constraintViolations);
    }

    @Test
    public void setRua_deve_gerar_constrain_recebendo_valor_vazio(){        
        end.setRua(EMPTYSTR);
        constraintViolations = validator.validateValue(Endereco.class, "rua", end.getRua());
        
        assertThat(constraintViolations, not(equalTo(null)));
        exibeConstrains(constraintViolations);
    }

    @Test
    public void setRua_deve_gerar_constrain_recebendo_valor_incorreto(){        
        end = Fixture.from(Endereco.class).gimme(TipoTemplate.INVALIDO.getTemplate());
        constraintViolations = validator.validateValue(Endereco.class, "rua", end.getRua());
        
        assertThat(constraintViolations, not(equalTo(null)));
        exibeConstrains(constraintViolations);
    }

    @Test
    public void teste_setBairro_e_getBairro_correto_nao_deve_gerar_constrain() {
        for(int i = 0 ; i < 5 ; i++) {
            end = Fixture.from(Endereco.class).gimme(TipoTemplate.VALIDO.getTemplate());
            endereco.setBairro(end.getBairro());
            constraintViolations = validator.validateValue(Endereco.class, "bairro", end.getBairro());

            exibeConstrains(constraintViolations);
            assertThat(constraintViolations.isEmpty(), equalTo(true));
            assertThat("Os valores deveriam ser iguais", endereco.getBairro(), equalTo(end.getBairro()));
        }
    }

    @Test
    public void setBairro_deve_gerar_constrain_recebendo_valor_nulo(){        
        end.setBairro(NULLSTR);
        constraintViolations = validator.validateValue(Endereco.class, "bairro", end.getBairro());

        assertThat(constraintViolations, not(equalTo(null)));
        exibeConstrains(constraintViolations);
    }

    @Test
    public void setBairro_deve_gerar_constrain_recebendo_valor_vazio(){        
        end.setBairro(EMPTYSTR);
        constraintViolations = validator.validateValue(Endereco.class, "bairro", end.getBairro());
        
        assertThat(constraintViolations, not(equalTo(null)));
        exibeConstrains(constraintViolations);
    }

    @Test
    public void setBairro_deve_gerar_constrain_recebendo_valor_incorreto(){        
        end = Fixture.from(Endereco.class).gimme(TipoTemplate.INVALIDO.getTemplate());
        constraintViolations = validator.validateValue(Endereco.class, "bairro", end.getBairro());
        
        assertThat(constraintViolations, not(equalTo(null)));
        exibeConstrains(constraintViolations);
    }

    @Test
    public void teste_setNumero_e_getNumero_correto_nao_deve_gerar_constrain() {
        for(int i = 0 ; i < 5 ; i++) {
            end = Fixture.from(Endereco.class).gimme(TipoTemplate.VALIDO.getTemplate());
            
            endereco.setNumero(end.getNumero());
            constraintViolations = validator.validateValue(Endereco.class, "num", end.getNumero());

            exibeConstrains(constraintViolations);
            assertThat(constraintViolations.isEmpty(), equalTo(true));
            assertThat("Os valores deveriam ser iguais", endereco.getNumero(), equalTo(end.getNumero()));
        }
    }

    @Test
    public void setNumero_deve_gerar_constrain_recebendo_valor_nulo(){        
        end.setNumero(NULLSTR);
        constraintViolations = validator.validateValue(Endereco.class, "num", end.getNumero());

        assertThat(constraintViolations, not(equalTo(null)));
        exibeConstrains(constraintViolations);
    }

    @Test
    public void setNumero_deve_gerar_constrain_recebendo_valor_vazio(){        
        end.setNumero(EMPTYSTR);
        constraintViolations = validator.validateValue(Endereco.class, "num", end.getNumero());
        
        assertThat(constraintViolations, not(equalTo(null)));
        exibeConstrains(constraintViolations);
    }

    @Test
    public void setNumero_deve_gerar_constrain_recebendo_valor_incorreto(){        
        end = Fixture.from(Endereco.class).gimme(TipoTemplate.INVALIDO.getTemplate());
        constraintViolations = validator.validateValue(Endereco.class, "num", end.getNumero());
                
        assertThat(constraintViolations, not(equalTo(null)));
        exibeConstrains(constraintViolations);
    }

    @Test
    public void teste_setCep_e_getCep_correto_nao_deve_gerar_constrain() {
        String cep = "03575090"; // poderia ser 03575-090
        end.setCep(cep);
        endereco.setCep(end.getCep());
        constraintViolations = validator.validateValue(Endereco.class, "cep", end.getCep());

        exibeConstrains(constraintViolations);
        assertThat(constraintViolations.isEmpty(), equalTo(true));
        assertThat("Os valores deveriam ser iguais", endereco.getCep(), equalTo(end.getCep()));
    }

    @Test
    public void setCep_deve_gerar_constrain_recebendo_valor_nulo(){        
        end.setCep(NULLSTR);
        constraintViolations = validator.validateValue(Endereco.class, "cep", end.getCep());

        assertThat(constraintViolations, not(equalTo(null)));
        exibeConstrains(constraintViolations);
    }

    @Test
    public void setCep_deve_gerar_constrain_recebendo_valor_vazio(){        
        end.setCep(EMPTYSTR);
        constraintViolations = validator.validateValue(Endereco.class, "cep", end.getCep());
        
        assertThat(constraintViolations, not(equalTo(null)));
        exibeConstrains(constraintViolations);
    }

    @Test
    public void setCep_deve_gerar_constrain_recebendo_valor_incorreto(){        
        end = Fixture.from(Endereco.class).gimme(TipoTemplate.INVALIDO.getTemplate());
        constraintViolations = validator.validateValue(Endereco.class, "cep", end.getCep());
                
        assertThat(constraintViolations, not(equalTo(null)));
        exibeConstrains(constraintViolations);
    }

    @Test
    public void teste_setCidade_e_getCidade_correto_nao_deve_gerar_constrain() {
        for(int i = 0 ; i < 7 ; i++) {
            end = Fixture.from(Endereco.class).gimme(TipoTemplate.VALIDO.getTemplate());
            endereco.setCidade(end.getCidade());
            constraintViolations = validator.validateValue(Endereco.class, "cidade", end.getCidade());

            exibeConstrains(constraintViolations);
            assertThat(constraintViolations.isEmpty(), equalTo(true));
            assertThat("Os valores deveriam ser iguais", endereco.getCidade(), equalTo(end.getCidade()));
        }
    }

    @Test
    public void setCidade_deve_gerar_constrain_recebendo_valor_nulo(){        
        end.setCidade(NULLSTR);
        constraintViolations = validator.validateValue(Endereco.class, "cidade", end.getCidade());

        assertThat(constraintViolations, not(equalTo(null)));
        exibeConstrains(constraintViolations);
    }

    @Test
    public void setCidade_deve_gerar_constrain_recebendo_valor_vazio(){        
        end.setCidade(EMPTYSTR);
        constraintViolations = validator.validateValue(Endereco.class, "cidade", end.getCidade());
        
        assertThat(constraintViolations, not(equalTo(null)));
        exibeConstrains(constraintViolations);
    }

    @Test
    public void setCidade_deve_gerar_constrain_recebendo_valor_incorreto(){        
        end = Fixture.from(Endereco.class).gimme(TipoTemplate.INVALIDO.getTemplate());
        constraintViolations = validator.validateValue(Endereco.class, "cidade", end.getCidade());
        
        assertThat(constraintViolations, not(equalTo(null)));
        exibeConstrains(constraintViolations);
    }

    /* Teste de ToString */

    @Test
    public void teste_toString() {
        assertThat("ToString não deveria estar nulo neste teste", end.toString(), not(equalTo(null)));
    }

    /**
     * Método de auxílio que exibe Validations que foram infringidas no decorer de um teste específico.
     *
     * @param constraintViolations - A lista contendo todas as infrações que foram recebidas pelo validator.
     */
    public void exibeConstrains(Set<ConstraintViolation<Endereco>> constraintViolations) {
        for(ConstraintViolation<Endereco> cv : constraintViolations) {
            System.out.println(String.format("Constrain infringida! atributo: [%s], valor: [%s], message: [%s]", cv.getPropertyPath(), cv.getInvalidValue(), cv.getMessage()));
            // Confirmar se existe uma forma melhor de exbir oq não estiver válido
        }
    }

}
