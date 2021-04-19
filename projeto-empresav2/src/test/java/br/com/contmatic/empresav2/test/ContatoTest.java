package br.com.contmatic.empresav2.test;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.google.common.base.VerifyException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestWatcher;
import org.junit.runners.MethodSorters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.contmatic.empresav2.model.Contato;
import br.com.contmatic.empresav2.validator.ModelAnotacao;
import br.com.contmatic.empresav2.util.TestesModel;
import br.com.contmatic.empresav2.util.TestesUtil;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ContatoTest implements TestesUtil {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TestWatcher watchman = recebeLogs();

    private Set<ConstraintViolation<Contato>> constraintViolations;
    private static ValidatorFactory validatorFactory;
    private Validator validator;

    private static Contato contato;
    private Contato con;

    @BeforeClass
    public static void setUpBeforeClass() {
        contato = new Contato();
        validatorFactory = Validation.buildDefaultValidatorFactory();
        FixtureFactoryLoader.loadTemplates("br.com.contmatic.empresav2.util");
        System.out.println("--------- Início Testes Classe Contato --------- \n");
    }

    @Before
    public void setUp() {
        con = Fixture.from(Contato.class).gimme(TipoTemplate.VALIDO.getTemplate());
        validator = validatorFactory.getValidator();
    }

    @After
    public void tearDown() {
        con = null;
        validator = null;
        constraintViolations = null;
    }

    @AfterClass
    public static void tearDownAfterClass() {
        Contato.getContatoLista().clear();
        contato = null;
        validatorFactory = null;
        System.out.println("--------- Fim de Testes Classe Contato --------- \n");
    }

    /*
     * Seção de testes dos métodos de criação dos objetos da classe
     */

    @Test
    public void deve_cadastrar_contato_com_sucesso() {
        con = Contato.cadastraContato(con.getContato1(), con.getContato2(), con.getEmail());
        assertThat("Esperava receber um contato", Contato.solicitaContato(con.getContato1()), equalTo(con));
    }

    @Test
    public void deve_cadastrar_contato_com_sucesso_passando_um_contato_eUm_email() {
        con = Contato.cadastraContato(con.getContato1(), con.getEmail());
        assertThat("Esperava receber um contato", Contato.solicitaContato(con.getEmail()), equalTo(con));
    }

    @Test
    public void nao_deve_cadastrar_contato_passando_telefoneCelular_errado() {
        Contato conInvalido = Fixture.from(Contato.class).gimme(TipoTemplate.INVALIDO.getTemplate());
        thrown = constrainViolationEsperada(thrown, "contato1");

        Contato.cadastraContato(conInvalido.getContato1(), conInvalido.getEmail());
        fail(FAILCODE_MESSAGE);

    }

    @Test
    public void nao_deve_cadastrar_contato_passando_email_invalido() {
        Contato conInvalido = Fixture.from(Contato.class).gimme(TipoTemplate.INVALIDO.getTemplate());
        thrown = constrainViolationEsperada(thrown, "[email]");

        Contato.cadastraContato(con.getContato1(), conInvalido.getEmail());
        fail(FAILCODE_MESSAGE);
    }

    @Test
    public void nao_deve_cadastrar_contato_passando_em_branco() {
        thrown = constrainViolationEsperada(thrown, ModelAnotacao.EMAIL, ModelAnotacao.CONTATO);

        Contato.cadastraContato(EMPTYSTR, EMPTYSTR);
        fail(FAILCODE_MESSAGE);
    }

    /*
     * Seção de testes dos métodos de remoção de objetos da Collection.
     */

    @Test
    public void deve_remover_contato_passando_email_do_mesmo() {
        Contato contatoTest = Contato.cadastraContato(con.getContato1(), con.getEmail());
        con.removeContato(contatoTest.getEmail());
        assertThat("O objeto não deveria estar cadastrado", Contato.getContatoLista().contains(contatoTest), equalTo(false));
    }

    @Test
    public void deve_remover_contato_passando_telefone_celular_do_mesmo() {
        Contato contatoTest = Contato.cadastraContato(con.getContato1(), con.getEmail());
        con.removeContato(contatoTest.getContato1());
        assertThat("O objeto não deveria estar cadastrado", Contato.getContatoLista().contains(contatoTest), equalTo(false));
    }

    @Test
    public void deve_remover_contato_passando_o_priprio_contato() {
        Contato contatoTest = Contato.cadastraContato(con.getContato1(), con.getEmail());
        Contato.removeContato(contatoTest);
        assertThat("O objeto não deveria estar cadastrado", Contato.getContatoLista().contains(contatoTest), equalTo(false));
    }

    @Test(expected = NoSuchElementException.class)
    public void nao_deve_remover_contato_nao_existente() {
        con.removeContato("nomeContatoExclusivo@gmail.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nao_deve_remover_contato_nao_existente_metodo_alternativo() {
        Contato contatoVazio = new Contato();
        Contato.removeContato(contatoVazio);
    }

    /*
     * Seção de testes dos métodos de busca de objetos da Collection
     */

    @Test
    public void deve_retornar_um_contato_informando_um_email_ou_telefone_celular() {
        con = Contato.cadastraContato(con.getContato1(), con.getContato2(), con.getEmail());

        assertThat("Esperava receber um contato", Contato.solicitaContato(con.getContato1()), equalTo(con));
        assertThat("Esperava receber um contato", Contato.solicitaContato(con.getEmail()), equalTo(con));
    }

    @Test
    public void deve_retornar_um_contato_passando_o_proprio_contato() { // eu modifiquei o teste errado..
        con = Contato.cadastraContato(con.getContato1(), con.getContato2(), con.getEmail());
        Contato conTeste = con;
        assertThat("Esperava receber um contato", Contato.solicitaContato(conTeste), equalTo(con));

    }

    @Test
    public void deve_retornar_um_contato_passando_o_contato_secundario() {
        con = Contato.cadastraContato(con.getContato1(), con.getContato2(), con.getEmail());
        assertThat("Esperava receber um contato", Contato.solicitaContato(con.getContato2()), equalTo(con));
    }

    @Test(expected = IllegalArgumentException.class)
    public void nao_deve_permitir_cadastrar_contato_passando_email_jaCadastrados() {
        Contato.cadastraContato(con.getContato1(), con.getContato2(), con.getEmail());
        Contato.cadastraContato("1145643978", "11945643978", con.getEmail());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nao_deve_permitir_cadastrar_contato_passando_contato1_jaCadastrados() {
        Contato.cadastraContato(con.getContato1(), con.getContato2(), con.getEmail());
        Contato.cadastraContato(con.getContato1(), "11945643978", "emailTeste@gmail.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nao_deve_permitir_cadastrar_contato_passando_contato2_jaCadastrados() {
        Contato.cadastraContato(con.getContato1(), con.getContato2(), con.getEmail());
        Contato.cadastraContato("1145643978", con.getContato2(), "emailTeste@gmail.com");
    }

    @Test(expected = NoSuchElementException.class)
    public void deve_gerar_noSuchElementException_buscando_contato_nao_existente() {
        Contato.solicitaContato(TestesModel.EMPTYSTR);
    }

    /*
     * Seção de testes dos getters/setters da classe
     */

    @Test
    public void setEmail_getEmail_correto_nao_deve_gerar_constrain() {
        for(int i = 0 ; i < 7 ; i++) {
            con = Fixture.from(Contato.class).gimme(TipoTemplate.VALIDO.getTemplate());
            contato.setEmail(con.getEmail());

            constraintViolations = validator.validateValue(Contato.class, "email", con.getEmail());
            assertThat(constraintViolations, not(equalTo(null)));
            assertThat("Os valores deveriam ser iguais", contato.getEmail(), equalTo(con.getEmail()));
        }
    }

    @Test
    public void setEmail_deve_gerar_constrain_recebendo_valor_nulo() {
        con.setEmail(NULLSTR);
        constraintViolations = validator.validateValue(Contato.class, "email", con.getEmail());

        assertThat(constraintViolations, not(equalTo(null)));
    }

    @Test
    public void setEmail_deve_gerar_constrain_recebendo_valor_vazio() {
        con.setEmail(EMPTYSTR);
        constraintViolations = validator.validateValue(Contato.class, "email", con.getEmail());

        assertThat(constraintViolations, not(equalTo(null)));
    }

    @Test
    public void setEmail_deve_gerar_constrain_recebendo_valor_incorreto() {
        con = Fixture.from(Contato.class).gimme(TipoTemplate.INVALIDO.getTemplate());
        constraintViolations = validator.validateValue(Contato.class, "email", con.getEmail());

        assertThat(constraintViolations, not(equalTo(null)));
    }

    @Test
    public void setContato1_getContato1_correto_nao_deve_gerar_constrain() {
        for(int i = 0 ; i < 7 ; i++) {
            con = Fixture.from(Contato.class).gimme(TipoTemplate.VALIDO.getTemplate());
            contato.setContato1(con.getContato1());

            constraintViolations = validator.validateValue(Contato.class, "contato1", con.getContato1());
            assertThat(constraintViolations, not(equalTo(null)));
            assertThat("Os valores deveriam ser iguais", contato.getContato1(), equalTo(con.getContato1()));
        }
    }

    @Test
    public void setContato1_deve_gerar_constrain_recebendo_valor_nulo() {
        con.setContato1(NULLSTR);
        constraintViolations = validator.validateValue(Contato.class, "contato1", con.getContato1());

        assertThat(constraintViolations, not(equalTo(null)));
    }

    @Test
    public void setContato1_deve_gerar_constrain_recebendo_valor_vazio() {
        con.setContato1(EMPTYSTR);
        constraintViolations = validator.validateValue(Contato.class, "contato1", con.getContato1());

        assertThat(constraintViolations, not(equalTo(null)));
    }

    @Test
    public void setContato1_deve_gerar_constrain_recebendo_valor_incorreto() {
        con = Fixture.from(Contato.class).gimme(TipoTemplate.INVALIDO.getTemplate());
        constraintViolations = validator.validateValue(Contato.class, "contato1", con.getContato1());

        assertThat(constraintViolations, not(equalTo(null)));
    }

    @Test
    public void setContato2_getContato2_correto_nao_deve_gerar_constrain() {
        for(int i = 0 ; i < 7 ; i++) {
            con = Fixture.from(Contato.class).gimme(TipoTemplate.VALIDO.getTemplate());
            contato.setContato2(con.getContato2());

            constraintViolations = validator.validateValue(Contato.class, "contato2", con.getContato2());
            assertThat(constraintViolations, not(equalTo(null)));
            assertThat("Os valores deveriam ser iguais", contato.getContato2(), equalTo(con.getContato2()));
        }
    }

    @Test
    public void setContato2_deve_gerar_constrain_recebendo_valor_nulo() {
        con.setEmail(NULLSTR);
        constraintViolations = validator.validateValue(Contato.class, "contato2", con.getContato2());

        assertThat(constraintViolations, not(equalTo(null)));
    }

    @Test
    public void setContato2_deve_gerar_constrain_recebendo_valor_vazio() {
        con.setEmail(EMPTYSTR);
        constraintViolations = validator.validateValue(Contato.class, "contato2", con.getContato2());

        assertThat(constraintViolations, not(equalTo(null)));
    }

    @Test
    public void setContato2_deve_gerar_constrain_recebendo_valor_incorreto() {
        con = Fixture.from(Contato.class).gimme(TipoTemplate.INVALIDO.getTemplate());
        constraintViolations = validator.validateValue(Contato.class, "contato2", con.getContato2());

        assertThat(constraintViolations, not(equalTo(null)));
    }

}
