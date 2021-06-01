package br.com.contmatic.model.v2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.google.common.base.VerifyException;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Verify.verify;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import br.com.contmatic.empresav2.validator.Validacao;
import br.com.contmatic.empresav2.validator.ModelAnotacao;

/**
 * The Class Contato.
 */
public class Contato implements Validacao {

    // TODO telefones
    
    public enum ContatoTipo {

                             NAOINFORMADO("Não informado", 0),
                             TELEFONE("Fixo", 13),
                             CELULAR("Celular", 14),
                             EMAIL("Email", 50);

        private String tipoContato;
        private int tamanhoTipoContato;

        private ContatoTipo(String tipoContato, int tamanhoTipoContato) {
            this.tipoContato = tipoContato;
            this.tamanhoTipoContato = tamanhoTipoContato;
        }

        // Metodos
        public String getTipoContato() {
            return tipoContato;
        }

        public int getTamanhoTipoContato() {
            return tamanhoTipoContato;
        }

        public static List<ContatoTipo> defineTipoContato(List<String> contatoLista) {
            List<ContatoTipo> lista = new ArrayList<>();
            //System.out.println("Contatos Passados: " + contatoLista);

            // Se fornecerem o DDD com zero, vão acabar violando uma restrição
            for(String contato : contatoLista) {
                try {
                    verify(contato.contains("@"));

                    // Se continuarmos aqui, validaremos se o contato inserido é um email válido
                    //System.out.println("Email passado: " + contato);
                    lista.add(EMAIL);
                } catch (VerifyException e) {

                    contato = contato.replaceAll("\\D", "");
                    //System.out.println(contato + " tamanho contato" + contato.length());

                    switch (contato.length()) {
                        case 10:
                            lista.add(TELEFONE);
                            break;
                        case 11:
                            lista.add(CELULAR);
                            break;
                        case 0:
                            lista.add(NAOINFORMADO);
                            break;
                        default:
                            continue; // testar
                    }
                }
            }
            return lista;
        }
    }

    private List<ContatoTipo> tipoContatos; //Set<List<ContatoTipo>>

    private static Set<@NotEmpty Contato> contatoLista = new HashSet<>();

    /** Destinado a Telefone ou Celular */ // (11) 4564-9304 / 1145643904 | 11941063792
    @NotBlank(message = ModelAnotacao.CONTATO)
    @Size(min = ModelAnotacao.CONTATOMIN, max = ModelAnotacao.CONTATOMAX, message = ModelAnotacao.TAMANHOCONTATO) // Contando a formatação feita
    @Pattern(regexp = ModelAnotacao.REGEXCONTATO, message = ModelAnotacao.REGEXCONTATOMSG)
    private String contato1;

    /** Destinado a Telefone ou Celular alternativo */
    @NotBlank(message = ModelAnotacao.CONTATO)
    @Size(min = ModelAnotacao.CONTATOMIN, max = ModelAnotacao.CONTATOMAX, message = ModelAnotacao.TAMANHOCONTATO) // Contando a formatação feita
    @Pattern(regexp = ModelAnotacao.REGEXCONTATO2, message = ModelAnotacao.REGEXCONTATOMSG)
    private String contato2;

    /** Destinado apenas a email. */
    @NotBlank(message = ModelAnotacao.EMAIL)
    @Size(min = ModelAnotacao.EMAILMIN, max = ModelAnotacao.EMAILMAX, message = ModelAnotacao.TAMANHOEMAIL)
    @Email
    private String email;

    public Contato(String contato1, String contato2, String email) {
        this.tipoContatos = ContatoTipo.defineTipoContato(Arrays.asList(contato1, contato2, email));
        this.contato1 = formataTextoContato(contato1);
        this.contato2 = formataTextoContato(contato2);
        this.email = email;
        registraContato((Contato) verificaConstrain((Object) (this)));
        // - Faltam os testes dos getters and setters / e testes de Equals/HashCode com a funcionalidade de não permitir contatos iguais inseridos

    }

    public Contato(String contato1, String email) {
        this.tipoContatos = ContatoTipo.defineTipoContato(Arrays.asList(contato1, "", email));
        this.contato1 = formataTextoContato(contato1);
        this.contato2 = ContatoTipo.NAOINFORMADO.getTipoContato();
        this.email = email;
        registraContato((Contato) verificaConstrain((Object) (this)));
        // - Faltam os testes dos getters and setters / e testes de Equals/HashCode com a funcionalidade de não permitir contatos iguais inseridos
    }

    public Contato() {

    }

    private void registraContato(Contato con) {
        checkArgument(!isContatoRegistrado(con) && !getContatoLista().contains(con), "Este contato " + con.toString() + " já está sendo usado por alguma outra empresa ou funcionario.");
        getContatoLista().add(con);
        System.out.println(con.toString());
    }

    /**
     * Está é uma simples função que verifica se o ID inserido já não está sendo utilizado por algum outro contato.
     *
     * @param contato - Recebe um contato e extrai o ID deste objeto.
     * @return <b>Falso</b> - caso o ID do contato não esteja sendo utilizado. <br>
     *         <b>True</b> - caso o ID esteja sendo utiliazdo.
     * 
     */
    private static boolean isContatoRegistrado(Contato contato) {
        return contato.getContato2().equals("Não informado") ? verificacaoSimples(contato):verificacao(contato);
    }

    private static boolean verificacao(Contato contato) {
        try {
            Iterator<Contato> iterator = getContatoLista().iterator();
            Contato con;

            do {
                con = iterator.next(); // Irá lançar um NoSuchElementException caso não encontre o contato

            } while (!(con.getContato1().equals(contato.getContato1()) || con.getContato2().equals(contato.getContato1())) &&
                     !(con.getContato1().equals(contato.getContato2()) || con.getContato2().equals(contato.getContato2())));

            return true; // então o ID já está cadastrado.
        } catch (NoSuchElementException nosu) {
            return false; // Se cairmos aqui significa que nenhum ID foi encontrado, então o ID não está sendo utilizado
        }
    } 
    
    private static boolean verificacaoSimples(Contato contato) {
        try {
            Iterator<Contato> iterator = getContatoLista().iterator();
            Contato con;

            do {
                con = iterator.next(); // Irá lançar um NoSuchElementException caso não encontre o contato

            } while (!con.getContato1().equals(contato.getContato1()));

            return true; // então o ID já está cadastrado.
        } catch (NoSuchElementException nosu) {
            return false; // Se cairmos aqui significa que nenhum ID foi encontrado, então o ID não está sendo utilizado
        }
    }

    public static Contato cadastraContato(String contato1, String contato2, String email) {
        return new Contato(contato1, contato2, email);
    }

    public static Contato cadastraContato(String contato1, String email) {
        return new Contato(contato1, email);
    }

    /**
     * Busca o contato que possui o email ou telefone celular principal, cadastrado.
     * 
     *
     * @param infoContato - O email/telefone celular do contato.
     * @return O Contato cadastrado com o email/telefone celular informado.
     * @throws NoSuchElementException Caso não seja possível encontrar nenhum contato com as informações passadas
     */
    public static Contato solicitaContato(String infoContato) { // -> Preciso criar esse cara na versão de contato1
        infoContato = formataTextoContato(infoContato);
        Iterator<Contato> iterator = getContatoLista().iterator();
        Contato con;

        try {
            do {
                con = iterator.next(); // Irá lançar um NoSuchElementException caso não encontre o contato

            } while (!(con.getEmail().equalsIgnoreCase(infoContato) || con.getContato1().equalsIgnoreCase(infoContato)));

        } catch (NoSuchElementException nosu) {
            throw new NoSuchElementException("Não foi possível encontrar o contato na lista de contatos.\n" + nosu.getMessage());
        }
        return con;
    }

    /**
     * Método alternativo para solicitar um contato. Método deve ser sempre passado para confirmar que o contato solicitado é um contato existente na lista.
     *
     * @param contato - O Contato que você deseja receber
     * @return o contato
     * @throws IllegalArgumentException Caso o contato informado não exista
     */
    public static Contato solicitaContato(Contato contato) {
        checkArgument(Contato.getContatoLista().contains(contato), "O contato inserido não existe na lista de contatos");
        return contato;
    }

    /**
     * Remove o contato passando o email ou telefone celular principal cadastrado.
     *
     * @param infoContato - A informação email ou telefone principal do contato
     * @throws IllegalArgumentException Caso o contato que tentou remover não possa ter sido encontrado
     */
    public void removeContato(String infoContato) {
        Contato obj = solicitaContato(infoContato);
        getContatoLista().remove(obj);
    }

    /**
     * Método alternativo para remoção de contato.
     *
     * @param contato - você pode fornecer o próprio contato que deseja remover
     * @throws IllegalArgumentException Caso o Contato que você informou não esteja cadastrado na lista de contatos
     */
    public static void removeContato(Contato contato) {
        checkArgument(getContatoLista().contains(contato), "O Contato que tentou remover não está cadastrado");
        getContatoLista().remove(contato);
    }

    public static String formataTextoContato(String contato) {
        contato = contato.replaceAll("\\s", "");

        switch (contato.length()) { // Verifica se o contato já está com a sua formatação certa. Se não estiver, formata e devolve o contato, se estiver, só retorna o contato formatado
            case 10:
                return "(" + contato.substring(0, 2) + ")" + contato.substring(2, 6) + "-" + contato.substring(6);// telefone
            case 11:
                return "(" + contato.substring(0, 2) + ")" + contato.substring(2, 7) + "-" + contato.substring(7); // celular
            default:
                return contato; // Se cair aqui. O contato passado já está formatado.
        }
    }

    public String getContato1() {
        return contato1;
    }

    public void setContato1(String contato1) {
        this.contato1 = contato1;
    }

    public String getContato2() {
        return contato2;
    }

    public void setContato2(String contato2) {
        this.contato2 = contato2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static Set<Contato> getContatoLista() {
        return contatoLista;
    }

    public List<ContatoTipo> getTipoContatos() {
        return tipoContatos;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.email).hashCode();
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
        Contato cn = (Contato) obj; /* Se ou o contato2, contato1 ou o email forem iguais, retorne true -> Precisa ser testado */
        return ((EqualsBuilder.reflectionEquals(this.contato2, cn.contato2) || EqualsBuilder.reflectionEquals(this.contato1, cn.contato1)) || EqualsBuilder.reflectionEquals(this.email, cn.email));
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
