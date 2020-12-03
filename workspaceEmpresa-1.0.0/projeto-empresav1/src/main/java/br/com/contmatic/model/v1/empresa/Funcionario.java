package br.com.contmatic.model.v1.empresa;

import java.util.Date;

import br.com.contmatic.model.v1.empresa.endereco.Endereco;

import static br.com.contmatic.util.AtributoValidator.validaNomeSimples;
import static br.com.contmatic.util.AtributoValidator.validaNulo;
import static br.com.contmatic.util.AtributoValidator.validaTamanho;
import static br.com.contmatic.util.documentos.CpfValidator.validarCpf;
import static br.com.contmatic.util.documentos.CpfValidator.formataCpf;
import static br.com.contmatic.util.DataFormatter.getFormatterInstance;
import static br.com.contmatic.util.DataFormatter.verificaSeDataEstaNoPassado;
import static br.com.contmatic.util.CamposTypes.FUNCIONARIO_NOME_TAMANHO_MAX;
import static br.com.contmatic.util.CamposTypes.FUNCIONARIO_NOME_TAMANHO_MIN;
import static br.com.contmatic.util.CamposTypes.FUNCIONARIO_SALARIO_VALOR_MAX;
import static br.com.contmatic.util.CamposTypes.FUNCIONARIO_SALARIO_VALOR_MIN;

public class Funcionario {

	private String nome;

	private String cpf;

	private Endereco endereco;

	private Contato contato;

	private Date dtAdimissao;

	private Double salario;

	private Departamento departamento;

	public Funcionario(String nome, String cpf, Endereco endereco, Contato contato, String dtAdimissao, double salario,
			Departamento departamento) {
		this.setNome(nome);
		this.setCpf(cpf);
		this.setEndereco(endereco);
		this.setContato(contato);
		this.setDtAdimissao(getFormatterInstance().formataData(dtAdimissao));
		this.setSalario(salario);
		this.setDepartamento(departamento);
	}

	public Funcionario(String nome, String cpf, Endereco endereco, Contato contato, Date dtAdimissao, double salario,
			Departamento departamento) {
		this.setNome(nome);
		this.setCpf(cpf);
		this.setEndereco(endereco);
		this.setContato(contato);
		this.setDtAdimissao(getFormatterInstance().formataData(dtAdimissao));
		this.setSalario(salario);
		this.setDepartamento(departamento);
	}

	public Funcionario(String nome, String cpf, Endereco endereco, Contato contato) {
		this.setNome(nome);
		this.setCpf(cpf);
		this.setEndereco(endereco);
		this.setContato(contato);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		validaNulo(getClass(), "nome", nome);
		validaTamanho(getClass(), "nome", nome.length(), FUNCIONARIO_NOME_TAMANHO_MIN, FUNCIONARIO_NOME_TAMANHO_MAX);
		validaNomeSimples(getClass(), "nome", nome);
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		validarCpf(cpf);
		this.cpf = formataCpf(cpf);
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		validaNulo(getClass(), "endereco", endereco);
		this.endereco = endereco;
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		validaNulo(getClass(), "contato", contato);
		this.contato = contato;
	}

	public Date getDtAdimissao() {
		return dtAdimissao;
	}

	public void setDtAdimissao(Date dtAdimissao) {
		validaNulo(getClass(), "dtAdimissao", dtAdimissao);
		verificaSeDataEstaNoPassado(dtAdimissao);
		this.dtAdimissao = dtAdimissao;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.validaSalario(salario);
		this.salario = salario;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		validaNulo(getClass(), "departamento", departamento);
		this.departamento = departamento;
	}

	private void validaSalario(double salario) {
		if (salario <= FUNCIONARIO_SALARIO_VALOR_MIN || salario >= FUNCIONARIO_SALARIO_VALOR_MAX) {
			throw new IllegalArgumentException("Este salário não pode ser aceito...");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Funcionario other = (Funcionario) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ": Nome=" + nome + ", CPF=" + cpf + ", Data Admissão= " + getFormatterInstance().getSdf().format(dtAdimissao) + "\nDepartamento=" + departamento
				+ "\nEndereco=" + endereco + "\nContato=" + contato + "\nSalario=" + salario;
	}
}
