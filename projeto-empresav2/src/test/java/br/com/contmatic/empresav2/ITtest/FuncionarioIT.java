package br.com.contmatic.empresav2.ITtest;

import static org.hamcrest.CoreMatchers.equalTo;
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
import org.junit.Rule;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.rules.ExpectedException;

import br.com.contmatic.empresav2.builder.FuncionarioBuilder;
import br.com.contmatic.empresav2.util.TestesModel;
import br.com.contmatic.model.v2.Departamento;
import br.com.contmatic.model.v2.Endereco;
import br.com.contmatic.model.v2.Funcionario;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

/**
 * @author Pedro
 *         <p>
 *         Está classe irá testar as funcionalidades de Funcionario em conjunto com as demais classes que possuem conexão com ele.
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FuncionarioIT implements TestesModel {

    private static ValidatorFactory validatorFactory;
    private Validator validator;
    private Funcionario fun;
    private Departamento dep;
    private Endereco end;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        FixtureFactoryLoader.loadTemplates("br.com.contmatic.empresav2.util");
        validatorFactory = Validation.buildDefaultValidatorFactory();
        System.out.println("--------- Início dos Testes Integrados da Classe Funcionario --------- \n\n");
    }

    @Before
    public void setUp() throws Exception {
        fun = Fixture.from(Funcionario.class).gimme("valido");
        dep = Fixture.from(Departamento.class).gimme("valido");
        end = Fixture.from(Endereco.class).gimme("valido");

    }

    @After
    public void tearDown() throws Exception {
        fun = null;
        dep = null;
        end = null;
        validator = null;
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        validatorFactory = null;
        Funcionario.getFuncionarioLista().clear();
        Departamento.getDepartamentoLista().clear();
        System.out.println("--------- Fim dos Testes Integrados da Classe Funcionario --------- \n\n");
    }

    /*
     * Seção de testes dos métodos de criação dos objetos da classe
     */

    @Test
    public void deve_criar_funcionario_valido_atraves_doFuncionarioBuilder_utilizando_Fixture() {
        assertThat("O Funcionario deveria ter sido criado e armazenado", Funcionario.getFuncionarioLista().contains(fun = cadastraFuncionario(TipoTemplate.VALIDO)), equalTo(true));
    }

    @Test
    public void nao_deve_criar_funcionario_invalido_atraves_doFuncionarioBuilder_utilizando_Fixture() {
        Funcionario invalido = cadastraFuncionario(TipoTemplate.INVALIDO);
        System.out.println("Funcionário inválido registro:\n" + invalido.toString());

        assertThat("O Funcionario NÃO deveria ter sido criado e armazenado", Funcionario.getFuncionarioLista().contains(invalido), equalTo(false));
    }

    @Test(expected = NullPointerException.class)
    public void deveGerar_exception_criando_funcionario_nulo() {
        new Endereco(end.getRua(), end.getBairro(), end.getNumero(), end.getCep(), end.getCidade(), end.getEstado());
        new Departamento(dep.getIdDepartamento(), dep.getNome(), dep.getRamal());

        new Funcionario(TestesModel.NULLONG, TestesModel.NULLSTR, TestesModel.NULLSTR, TestesModel.NULLSTR, end, dep, TestesModel.NULLDOUBLE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveGerar_exceptions_criando_funcionairo_vazio() { // exceptions gerador pelo CEP e pelo Contato
        new Endereco(end.getRua(), end.getBairro(), end.getNumero(), end.getCep(), end.getCidade(), end.getEstado());
        new Departamento(dep.getIdDepartamento(), dep.getNome(), dep.getRamal());

        new Funcionario(TestesModel.EMPTYLONG, TestesModel.EMPTYSTR, TestesModel.EMPTYSTR, TestesModel.EMPTYSTR, end, dep, TestesModel.EMPTYLONG);
    }

    /*
     * Seção de testes dos métodos de remoção de objetos da Collection.
     */

    @Test
    public void deve_remover_objeto_ja_existente_daCollection_com_sucesso() {
        fun = cadastraFuncionario(TipoTemplate.VALIDO);
        Funcionario.removeFuncionario(fun.getIdFuncionario());
        assertThat("O Funcionario não devia estar cadastrado", Funcionario.getFuncionarioLista().contains(fun), equalTo(false));

    }

    @Test(expected = IllegalArgumentException.class)
    public void nao_deve_remover_funcionario_nao_existente() {
        Funcionario.removeFuncionario(99999);
    }

    /*
     * Seção de testes dos métodos de busca de objetos da Collection
     */

    @Test
    public void deve_retornar_funcionario_ja_cadastrado() {
        Funcionario fun1 = cadastraFuncionario(TipoTemplate.VALIDO);
        Funcionario fun2 = cadastraFuncionario(TipoTemplate.VALIDO);
        Funcionario fun3 = cadastraFuncionario(TipoTemplate.VALIDO);

        assertNotNull("Esperava receber um objeto", Funcionario.solicitaFuncionario(fun1.getIdFuncionario()));
        assertNotNull("Esperava receber um objeto", Funcionario.solicitaFuncionario(fun2.getIdFuncionario()));
        assertNotNull("Esperava receber um objeto", Funcionario.solicitaFuncionario(fun3.getIdFuncionario()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void nao_deve_retornar_funcionario_nao_existente() {
        Funcionario.solicitaFuncionario(9041);
    }
    
    /*
     * Está seção de testes tem o intuito de testar os métodos de listagem
     */

    @Test
    public void teste_toString() {
        fun = cadastraFuncionario(TipoTemplate.VALIDO); 
        assertNotNull("Os valores deveriam ser iguais", fun.toString());
    }

    /**
     * Teste Específico: Não deveria registrar Funcionario se o CPF informado já estiver sendo usado por outro funcionario.
     */

    @Test // Este tipo de teste é considerado deprecated no Junit 4.13
    public void nao_deve_registrar_funcionario_se_oCPF_ja_estiver_sendo_usado() { // Teste com novo HashCode e Equals :D
        fun = cadastraFuncionario(TipoTemplate.VALIDO);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(CoreMatchers.containsString("O ID ou CPF do Funcionario: " + fun.getNome() + " de CPF: " + fun.getCpf() + " já possui registro"));

        new FuncionarioBuilder().funInformacoes(fun.getNome(), fun.getCpf(), "1145643904").funEnderecoViaCep("59631-240", "456") // preciso passar um Endereço diferente do anterior.
                .funEmpresa(fun.getIdFuncionario() + 1, fun.getSalario(), new Departamento(dep.getIdDepartamento(), dep.getNome(), dep.getRamal())).constroi();

    }

    @Test
    public void nao_deve_criar_funcionario_seEndereco_informado_jaEstiver_sendo_usado() {
        Funcionario funJaRegistrado = cadastraFuncionario(TipoTemplate.VALIDO);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(CoreMatchers.containsString("Este endereço:\n" + funJaRegistrado.getEndereco() +
            " já está sendo usado por alguma empresa ou funcionario. Insira um endereço alternativo, ou verifique se vc digitou a numeração correta.\n"));

        new FuncionarioBuilder().funInformacoes(fun.getNome(), fun.getCpf(), fun.getContato()).funEndereco(funJaRegistrado.getEndereco()).funEmpresa(fun.getIdFuncionario(), fun.getSalario(),
            funJaRegistrado.getDepartamento());
    }

    @Test //As vezes esté teste pode bugar e acabar gerando um hascode diferente, mesmo utilizando o mesmo CPF. Limpar o projeto quando isto acontecer.
    public void nao_deve_criar_funcionario_seDepartamento_informado_nao_existir() {
        dep = new Departamento();
        
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(CoreMatchers.containsString("O departamento inserido não existe na lista de departamentos"));        
        
        new FuncionarioBuilder().funInformacoes(fun.getNome(), fun.getCpf(), fun.getContato())
                                .funEndereco(fun.getEndereco())
                                .funEmpresa(fun.getIdFuncionario(), fun.getSalario(), dep)
                                .constroi();                      
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

    /**
     * Método auxiliar. Cadastra funcionario com o parâmetro que você passou..
     *
     * @param tipoTemplate - Cadastrará um funcionário, com base em uma especificação.
     *        <p>
     *        <b>Valido</b> - Retornará um funcionário que poderá ser cadastrado.<br>
     *        <b>Invalido</b> - Retornará um funcionário com dados que não poderão ser cadastrado.
     */
    private Funcionario cadastraFuncionario(TipoTemplate tipoTemplate) {
        Funcionario funcionario = Fixture.from(Funcionario.class).gimme(tipoTemplate.getTemplate());
        Endereco endereco = Fixture.from(Endereco.class).gimme(tipoTemplate.getTemplate());
        Departamento departamento = Fixture.from(Departamento.class).gimme(tipoTemplate.getTemplate());

        try {
            return funcionario = new FuncionarioBuilder().funInformacoes(funcionario.getNome(), funcionario.getCpf(), funcionario.getContato())
                    .funEndereco(endereco.getRua(), endereco.getBairro(), endereco.getNumero(), endereco.getCep(), endereco.getCidade(), endereco.getEstado())
                    .funEmpresa(funcionario.getIdFuncionario(), funcionario.getSalario(), new Departamento(departamento.getIdDepartamento(), departamento.getNome(), departamento.getRamal()))
                    .constroi();
        } catch (IllegalArgumentException e) {
            return funcionario;
        }

    }

}
