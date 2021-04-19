/**
 * 
 */
package br.com.contmatic.empresav2.builder;

import br.com.contmatic.empresav2.model.Departamento;
import br.com.contmatic.empresav2.model.Endereco;
import br.com.contmatic.empresav2.model.Estado;
import br.com.contmatic.empresav2.model.Funcionario;

/**
 * @author Pedro
 *
 */
public class FuncionarioBuilder {

    private long idFuncionario;

    private String nome;

    private String cpf;

    private Endereco endereco;

    private String contato;

    private double salario;

    private Departamento departamento;

    /**
     * Cadastra as informações básicas do funcionário. Como,
     *
     * @param nome - Nome.
     * @param cpf - CPF
     * @param contato - O Contato, podendo ser um telefone, celular, ou até email.
     * @return O Construtor do funcionario passando os valores do próprio funcionario
     */
    public FuncionarioBuilder funInformacoes(String nome, String cpf, String contato) {
        this.nome = nome;
        this.cpf = cpf;
        this.contato = contato;
        return this;
    }

    /**
     * Cadastra o Endereco completo do Funcionario, através apenas do seu CEP e numero da residência.<br>
     * Caso este método venha a não funcionar eventualmente, você pode utilizar o método <b>funEnderecoManual</b> <br>
     * como uma forma de cadastro alternativo
     *
     * @param cep - CEP do funcionário.
     * @param numero - Número identificador da casa do funcionário
     * @return O Construtor do funcionario passando os valores de endereço
     * @throws IllegalArgumentException Caso o endereço informado não seja válido
     */
    public FuncionarioBuilder funEnderecoViaCep(String cep, String numero) { // Esté método apenas pode receber valores de cep reais.
        this.endereco = Endereco.cadastraEnderecoViaCEP(cep, numero);
        return this;
    }

    /**
     * Método alternativo para cadastro do endereço do Funcionário.<br>
     * Nele você terá que inserir manualmente todos os valores que compõem o endereço do funcionário
     *
     * @param rua - Nome da Rua
     * @param bairro - Nome do Bairro
     * @param numero - Número da residência
     * @param cep - CEP
     * @param cidade - Nome da Cidade
     * @param estado - O Estádo que reside. <br>
     *        Utilizar o método
     * 
     *        <pre>
     *  Estado.(UF_DO_ESTADO_DO_ENDERECO)
     *        </pre>
     * 
     * @return O Construtor do funcionario passando os valores de endereço
     */
    public FuncionarioBuilder funEndereco(String rua, String bairro, String numero, String cep, String cidade, Estado estado) {
        this.endereco = Endereco.cadastraEndereco(rua, bairro, numero, cep, cidade, estado);
        return this;
    }

    /**
     * Método alternativo para cadastro do endereço do Funcionário.<br>
     * Caso você tenha um endereço, você pode passa-lo através deste método.
     *
     * @param endereco - Um endereço que já esteja cadastrado
     * @return the funcionario builder
     * @throws IllegalArgumentException Caso o endereço não seja válido.
     */
    public FuncionarioBuilder funEndereco(Endereco endereco) {
        this.endereco = new Endereco(endereco.getRua(), endereco.getBairro(), endereco.getNumero(), endereco.getCep(), endereco.getCidade(), endereco.getEstado());
        return this;
    }

    /**
     * Cadastra os dados empresáriais do funcionário
     *
     * @param numIdentificador - O ID ou Número que o funcionário possui para indentificalo. Não pode ser repetido
     * @param salario - O Salario do funcionário
     * @param departamento - O Departamento que atua. <br>
     *        Você pode obter um departamento atráves do método
     * 
     *        <pre>
     *        Departamento.solicitaDep(ID_do_Departamento)
     *        </pre>
     * 
     * @return O Construtor do funcionario passando os valores de empresa
     */
    public FuncionarioBuilder funEmpresa(long numIdentificador, double salario, Departamento departamento) {
        this.idFuncionario = numIdentificador;
        this.departamento = departamento;
        this.salario = salario;
        return this;
    }

    public Funcionario constroi() {
        return new Funcionario(this.idFuncionario, this.nome, this.cpf, this.contato, this.endereco, this.departamento, this.salario);
    }

}
