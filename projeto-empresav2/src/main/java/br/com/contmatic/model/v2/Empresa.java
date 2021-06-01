package br.com.contmatic.model.v2;

import static br.com.contmatic.util.v1.CamposRegexTypes.NOMEFANTASIA;
import static br.com.contmatic.util.v1.CamposRegexTypes.NOMESIMPLES;
import static br.com.contmatic.util.v1.CamposTypes.EMPRESA_NOME_FANTASIA_TAMANHO_MAX;
import static br.com.contmatic.util.v1.CamposTypes.EMPRESA_NOME_FANTASIA_TAMANHO_MIN;
import static br.com.contmatic.util.v1.CamposTypes.EMPRESA_RAZAO_SOCIAL_TAMANHO_MAX;
import static br.com.contmatic.util.v1.CamposTypes.EMPRESA_RAZAO_SOCIAL_TAMANHO_MIN;
import static com.google.common.base.Preconditions.checkArgument;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.joda.time.DateTime;

import br.com.contmatic.util.v2.DataJoda;

//TODO JAutoDoc Empresa
public class Empresa {
    
    @NotNull(message = "A razão social não pode estar nula.")
    @Length(min = EMPRESA_RAZAO_SOCIAL_TAMANHO_MIN, max = EMPRESA_RAZAO_SOCIAL_TAMANHO_MAX, message = "A razão social precisa ter entre {min} a {max} caracteres.")
    @Pattern(regexp = NOMESIMPLES, message = "Razão social não pode conter números ou símbolos.")
    private String razaoSocial;
    
    @NotNull(message = "O nome fantasia não pode estar nulo.")
    @Length(min = EMPRESA_NOME_FANTASIA_TAMANHO_MIN, max = EMPRESA_NOME_FANTASIA_TAMANHO_MAX, message = "O nome fantasia precisa ter entre {min} a {max} caracteres.")
    @Pattern(regexp = NOMEFANTASIA, message = "Nome fantasia não pode começar com símbolos ou espaços em branco. Mas pode conter letras e dígitos.")
    private String nomeFantasia;

    @NotNull(message = "O CNPJ não pode estar nulo.")
    @NotEmpty(message = "O CNPJ precisa ser informado.")
    @CNPJ(message = "O CNPJ que você informou não é válido. Por favor, verifique novamente o mesmo.")
    private String cnpj;
    
    @Valid
    @NotNull(message = "O endereço não pode estar nulo.")
    private Endereco endereco;
    
    @Valid
    @NotNull(message = "O contato não pode estar nulo.")
    private Contato contato;

    @NotNull(message = "A data de fundação não deveria estar nula.")
    @Valid
    private DateTime dtFundacao; //TODO -> Ver necessidade de intregar com a classe @DataFormatter

    public Empresa(String razaoSocial, String nomeFantasia, String cnpj, Endereco endereco, Contato contato, DateTime dtFundacao) {
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.contato = contato;
        this.dtFundacao = dtFundacao;
    }
    
    public Empresa(String razaoSocial, String cnpj) {
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
    }
    
    public void setRazaoSocial(@Valid String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }
    
    public String getRazaoSocial() {
        return razaoSocial;
    }
    
    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }
    
    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;

    }

    public DateTime getDtFundacao() {
        return dtFundacao;
    }

    public void setDtFundacao(String dtFundacao) { //TODO USAR COMO COMPARATIVO
        checkArgument(dtFundacao.matches("[\\d/]+") && (dtFundacao.length() == 10), "Data deveria ser em número e estar no formato dd/mm/yyyy");
        DataJoda dt = new DataJoda(dtFundacao);
        this.dtFundacao = dt.getData();
    }

//    // Pensar em alguma forma de transformar estes if's para guava
//    public void setContato(String contato) {
//        // Selo Gambiarra Removido :D
//        checkNotNull(contato, "Contato não pode estar vazio");
//        int aux = (contato.replaceAll("\\D", "").length());
//        
//        switch (aux) {
//            case 10:
//                this.contato = "(" + contato.substring(0, 2) + ") " + contato.substring(2, 6) + "-" + contato.substring(6);
//                tipoContato = Util.FIXO; break;
//                
//            case 11:
//                this.contato = "(" + contato.substring(0, 2) + ") " + contato.substring(2, 7) + "-" + contato.substring(7);
//                tipoContato = Util.CELULAR; break;
//                
//            default:
//                checkArgument(contato.contains("@") && contato.contains(".com") && !(contato.length() < 7 || contato.length() > 50),
//                    "O contato inserido não corresponde a nenhum email ou telefone/celular." + "\n Digite apenas os números do telefone. ");
//                this.contato = contato;
//                tipoContato = Util.EMAIL;
//        }
//    }
//
//    public Util getTipoContato() {
//        return tipoContato;
//    }

    @Override
    public int hashCode() { 
        return new HashCodeBuilder().append(this.cnpj).hashCode();
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
        Empresa emp = (Empresa) obj; // TODO Need Testing
        return new EqualsBuilder().append(this.cnpj, emp.cnpj).isEquals() || new EqualsBuilder().append(this.razaoSocial, emp.razaoSocial).isEquals();
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
      }

}
