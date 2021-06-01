package br.com.contmatic.model.v2;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import com.google.common.base.VerifyException;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Verify.verify;
import static com.google.common.base.Preconditions.checkArgument;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//TODO JAutoDoc Departamento
public class Departamento {

    // Variáveis
    @NotNull(message = "Departamento não pode possuir um valor vazio")
    @Max(value = 300, message = "O número máximo de departamentos que podem ser cadastrados é 300")
    @Positive(message = "Id não pode ser um valor negativo ou zero")
    private long idDepartamento;

    @NotBlank(message = "Campo nome não pode ficar vázio")
    @Length(max = 40, message = "Nome de departamento não deve ser tão grande.")
    @Pattern(regexp = "[a-zA-ZáéíóúâêîôûãõÁÉÍÓÚÂÊÎÔÛÃÕçÇ ]+", message = "Este nome para o departamento é inapropriado. Recomenda-se mudar") // Nome não deveria ser registrado fora de um Padrão de A-z
    private String nome;

    @NotNull(message = "O Ramal não pode ficar vazio")
    @Range(min = 1, max = 999, message = "Ramal só pode conter até 3 dígitos.")
    @Positive(message = "Ramal não pode ser um valor negativo nem zero.")
    private int ramal; // Adicionaro forma de contato recebendo Ramal e Email futuramente (se possível)

    // NotEmpty -> nesta posição serve para ensinuar que a Collection Departamento não pode estar vazia.
    private static Collection<@NotEmpty Departamento> departamentoLista = new HashSet<>();

    private static final Logger logger = LogManager.getLogger(Departamento.class);

    // Construtores
    public Departamento(long idDepartamento, String nome, int ramal) {
        this.idDepartamento = idDepartamento;
        this.nome = nome;
        this.ramal = ramal;
        salvaRegistro(this);
    }

    public Departamento() {

    }

    // Métodos

    public Collection<Departamento> listarDepartamentos() {
        // TODO Me lembrar de remover esse método
        for(Departamento departamento : getDepartamentoLista()) {
            // logger.fine("An exception occurred with message: {}", message); right way to do it
            logger.info(departamento);

        }
        return departamentoLista;
    }

    /**
     * Registra o Departamento informado.
     *
     * @param id - O ID do departamento
     * @param nome - O nome do departamento
     * @param ramal - O Ramal do departamento, <b>máx de 3 dígitos</b>
     * @return O Departamento já cadastrado
     * @throws IllegalArgumentException Caso o departamento já esteja cadastrado, esté método pode gerar uma exception
     */
    public Departamento registraDep(long id, String nome, int ramal) {
        return new Departamento(id, nome, ramal);
    }

    private void salvaRegistro(Departamento departamento) {
        checkArgument(!getDepartamentoLista().contains(departamento) && !isRamalUtilizado(departamento),
            getIdDepartamento() + " já possui registro\n");
        getDepartamentoLista().add(departamento);        
    }

    /**
     * Está é uma simples função que verifica se o ramal inserido já não está sendo utilizado por algum outro departamento.
     *
     * @param departamento - Recebe um departamento e extrai o ID deste objeto.
     * @return <b>Falso</b> - caso o ramal do departamento não esteja sendo utilizado. <br>
     *         <b>True</b> - caso o ramal esteja sendo utiliazdo.
     * 
     */
    private static boolean isRamalUtilizado(Departamento departamento) {
        try {
            verify(getDepartamentoLista().isEmpty()); // Se a collection estiver vazia. 
            return false; //Saia deste método
        } catch (VerifyException v) {
            try {
                Iterator<Departamento> iterator = getDepartamentoLista().iterator(); 
                Departamento obj = iterator.next();

                while (obj.getRamal() != departamento.ramal && iterator.hasNext()) {
                    obj = iterator.next();
                }     
                checkArgument(obj.getRamal() == departamento.getRamal()); // verifica se o ramal já está sendo utilizado
                return true; // se estiver, retorne true
            } catch (IllegalArgumentException ie) { 
                return false; // Se cairmos aqui significa que nenhum ramal foi encontrado, então o ramal não está sendo utilizado
            }
        }

    }
    
    /**
     * Busca o departamento informado pelo ID.<br>
     * Caso o departamento não for encontrado. 
     *
     * @param id - O ID do departamento.
     * @return O Departamento cadastrado com o ID informado.
     * @throws IllegalArgumentException Caso o departamento com o ID informado não tenha sido encontrado.
     */
    public static Departamento solicitaDep(long id) {
        Iterator<Departamento> iterator = getDepartamentoLista().iterator();
        Departamento obj = iterator.next();

        while (obj.getIdDepartamento() != id && iterator.hasNext()) {
            obj = iterator.next();
        }
        checkArgument(obj.getIdDepartamento() == id, "O Departamento " + id + " não foi encontrado\n");
        return obj;
    }
    
    /**
     * Método alternativo para solicitar um departamento.
     * Método deve ser sempre passado para confirmar que o departamento solicitado é um departamento existente na lista.
     *
     * @param departamento - O Departamento que você deseja receber
     * @return o departamento
     * @throws IllegalArgumentException Caso o departamento informado não exista
     */
    public static Departamento solicitaDep(Departamento departamento) {
        checkArgument(Departamento.getDepartamentoLista().contains(departamento),
            "O departamento inserido não existe na lista de departamentos");
        return departamento;              
    }

    /**
     * Remove o departamento solicitado pelo ID.
     *
     * @param id - o ID do departamento.
     * @throws IllegalArgumentException Caso o departamento que tentou remover não possa ter sido encontrado
     */
    public void removeDep(long id) {
        Departamento obj = solicitaDep(id);        
        getDepartamentoLista().remove(obj);        
    }

    /**
     * Método alternativo para remoção de departamento.
     *
     * @param departamento - você pode fornecer o próprio departamento que deseja remover
     * @throws IllegalArgumentException Caso o Departamento que você informou não esteja cadastrado na lista de departamentos
     */
    public static void removeDep(Departamento departamento) {
        checkArgument(getDepartamentoLista().contains(departamento),
            "O Departamento que tentou remover não está cadastrado");
        getDepartamentoLista().remove(departamento);        
    }

    // Getters And Setters

    public long getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(long idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;

    }

    public int getRamal() {
        return ramal;
    }

    public void setRamal(int ramal) {
        this.ramal = ramal;
    }

    public static Collection<Departamento> getDepartamentoLista() {
        return departamentoLista;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.idDepartamento).hashCode();
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
        Departamento dp = (Departamento) obj; /* Não permite que um ID ou um Ramal repetido seja informado. É acionado caso um ID repetido seja informado */
        return (EqualsBuilder.reflectionEquals(this.idDepartamento, dp.idDepartamento) || EqualsBuilder.reflectionEquals(this.ramal, dp.ramal));
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
