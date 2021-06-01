package br.com.contmatic.model.v2;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Verify.verify;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.google.common.base.VerifyException;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.br.CPF;

import br.com.contmatic.empresav2.util.Util;

//TODO JAutoDoc Funcionario
public class Funcionario {

    // Variáveis

    // Número máximo de funcionarios é 3000 e seus valores só podem ser positivos
    
    @Max(value = 3000, message = "Numero máximo de funcionarios não pode ser maior que 3000")
    @Positive(message = "ID não pode ser negativo")
    private long idFuncionario;

    // Nome não pode estar vazio, possui um tamanho específico, e uma recomendação de expressão regular
    @NotBlank(message = "Campo nome não pode ficar vázio")
    @Length(min = 3, max = 40, message = "Nome não pode ser desse tamanho.")
    @Pattern(regexp = "[a-zA-ZáéíóúâêîôûãõÁÉÍÓÚÂÊÎÔÛÃÕ ]+", message = "Nome invalido. Recomenda-se mudar")
    private String nome;

    // CPF não pode estar vazio e possui seu próprio tipo de annotation
    @NotBlank(message = "CPF não pode ficar vázio")
    @CPF
    private String cpf;

    // Endereco Precisa ser válido, seguindo as regras das annotations de sua classe
    @Valid
    private Endereco endereco;

    // tipoContato sempre será um valor de referência presente na classe, portanto ele não poderá ser nulo
    @NotNull
    @Valid
    private Util tipoContato;

    // contato pode ser um telefone, celular, ou até mesmo um email.
    @Size(min = 7, max = 50)
    @Pattern(regexp = "[\\w@._()-]+", message = "Contato não permitido. Você pode fornecer tanto um Telefone quanto um email.")
    private String contato;

    // Salario possui um valor mínimo, máximo e sempre deve ser positivo
    @NotNull
    @Positive(message = "Salario não pode ser negativo")
    @Range(min = 1, max = 50000)
    private double salario;

    @NotNull
    @Valid
    private Departamento departamento;
    private static Collection<@NotEmpty Funcionario> funcionarioLista = new HashSet<>();

    // Construtores

    /**
     * Instância um novo funcionario.<p>
     * 
     * Por mais que este construtor sirva como forma de registrar um novo funcionário. 
     * <b>Você não deve utilizar ele para instânciar/registrar novos
     * funcionarios diretamente no sistema.</b><br>
     * Este construtor deve ser utilizado exclusivamente pelo FuncionarioBuilder<p>
     * 
     * <i>Exemplo de como instânciar um funcionário de forma correta</i><p>
     * <code>
     * Funcionario funcionarioExemplo = new FuncionarioBuilder()<br>
     *.funInformacoes(nome, cpf, contato)<br>
     *.funEndereco(rua, bairro, numero, cep, cidade, estado)<br>
     *.funEmpresa(numIdentificador, salario, departamento)<br>
     *.constroi();
     * </code> 
     * 
     * <p><i>Exemplo de como instânciar de forma errada<p></i>
     * 
     * <code>Funcionario exemploErrado = new Funcionario(id, nome, cpf, contato, endereco, departamento, salario);</code>
     *
     */
    public Funcionario(long id, String nome, String cpf, String contato, Endereco endereco, Departamento departamento, double salario) {        
        this.idFuncionario = id; 
        this.nome = nome;
        this.cpf = Util.formataCPF(cpf);
        this.endereco = endereco;
        this.contato = Contato.formataTextoContato(contato);
        this.tipoContato = Util.defineTipoContato(this.contato); 
        this.departamento = Departamento.solicitaDep(departamento);                
        this.salario = salario;
        salvaRegistro(this);
    }

    public Funcionario() {

    }

    // Métodos

    public void listaFuncionario() {   
        for(Funcionario funcionario : funcionarioLista) {
            System.out.println(funcionario.toString());
        }        
    }
    
    private static void salvaRegistro(Funcionario funcionario) {
        // Lógica abaixo: Se a lista de funcionários NÃO conter o funcionario (hash:CPF) e o funcionario NÃO estiver com o ID já sendo utilizado, então adicione ele na lista.
        
        //Testar uma verificação de Beans e só cadastrar caso o funcionario não tenha violado nenhuma
        
        checkArgument(!getFuncionarioLista().contains(funcionario) && !isIdUtilizado(funcionario),
         "O ID ou CPF do Funcionario: " + funcionario.getNome() + " de CPF: " + funcionario.getCpf() + " já possui registro"); //não mudar
        getFuncionarioLista().add(funcionario);        
    }

    /**
     * Solicita o funcionario atráves do ID.
     *
     * @param id - O ID do funcionário
     * @return O funcionario registrado com o ID informado
     * @throws IllegalArgumentException Caso o ID inserido não corresponda com nenhum funcionário registrado
     */
    public static Funcionario solicitaFuncionario(long id) {
        Iterator<Funcionario> iterator = getFuncionarioLista().iterator();
        Funcionario obj = iterator.next(); 

        while (obj.getIdFuncionario() != id && iterator.hasNext()) {
            obj = iterator.next();
        }        
            checkArgument(obj.getIdFuncionario() == id, "O ID:" + id + " não corresponde com nenhum funcionario cadastrado\n");
            return obj;       

    }

    /**
     * Está é uma simples função que verifica se o ID inserido já não está sendo utilizado por algum outro funcionario.
     *
     * @param funcionario - Recebe um funcionario e extrai o ID deste objeto.
     * @return <b>Falso</b> - caso o ID do funcionario não esteja sendo utilizado. <br>
     *         <b>True</b> - caso o ID esteja sendo utiliazdo.
     * 
     */
    private static boolean isIdUtilizado(Funcionario funcionario) {
        try {
            verify(getFuncionarioLista().isEmpty()); // Se a collection estiver vazia. 
            return false; //Saia deste método
        } catch (VerifyException v) {
            try {
                Funcionario.solicitaFuncionario(funcionario.getIdFuncionario()); // Se está linha rodar, significa que um Funcionario foi encontrado.                
                return true; // então o ID já está cadastrado.
            } catch (IllegalArgumentException ie) { 
                return false; // Se cairmos aqui significa que nenhum ID foi encontrado, então o ID não está sendo utilizado
            }
        }

    }

    public static void removeFuncionario(long id) {
        Funcionario obj = solicitaFuncionario(id);        
        getFuncionarioLista().remove(obj);

    }

    // Getters and Setters

    public long getIdFuncionario() {
        return idFuncionario;

    }

    public void setIdFuncionario(long idFuncionario) {      
        this.idFuncionario = idFuncionario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {        
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {        
        this.cpf = Util.formataCPF(cpf); // intencional
    }

    public String getContato() {
        return contato;

    }

    public void setContato(String contato) {
        this.contato = Contato.formataTextoContato(contato); // intencional
    }

    public Endereco getEndereco() {
        return endereco;

    }

    public double getSalario() {
        return salario;

    }

    public void setSalario(double salario) {        
        this.salario = salario;
    }

    public Departamento getDepartamento() {
        return departamento;

    }

    public Util getTipoContato() {
        return tipoContato;
    }

    public void setTipoContato(Util tipoContato) {
        this.tipoContato = tipoContato;
    }

    public static Collection<Funcionario> getFuncionarioLista() {
        return funcionarioLista;

    }
    
    /* Troquei a forma antiga de hashcode devido a uma inconsistência gerada nela */
    @Override               
    public int hashCode() { 
        return new HashCodeBuilder().append(cpf.replaceAll("\\D", "")).build();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Funcionario fn = (Funcionario) obj;                 /* Se ou o CPF ou o ID forem iguais, retorne true */
        return (EqualsBuilder.reflectionEquals(this.idFuncionario, fn.idFuncionario) || EqualsBuilder.reflectionEquals(this.cpf, fn.cpf));        
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE).replace("idFuncionario", "ID").replace("endereco=", "").replace("departamento=", "").concat("\n");
    }
}
