package br.com.contmatic.testes.v1.util;

import static br.com.contmatic.model.v1.telefone.DDIType.DDI55;

import java.util.Random;

import br.com.contmatic.model.v1.empresa.Contato;
import br.com.contmatic.model.v1.empresa.Departamento;
import br.com.contmatic.model.v1.empresa.DepartamentoTest;
import br.com.contmatic.model.v1.endereco.Cidade;
import br.com.contmatic.model.v1.endereco.Endereco;
import br.com.contmatic.model.v1.endereco.EstadoType;
import br.com.contmatic.model.v1.telefone.Telefone;

public final class TestesUtils {
	
	public static Random random = new Random();

	public static final String NULLSTR = null;

	public static final String EMPTYSTR = "";

	public static final Double EMPTYDOUBLE = 0.0;

	public static final int EMPTYINTEGER = 0;

	public static Endereco retornaEndereco() {
		return new Endereco("Rua Dos Testes", "Setor Marista Sul",
				retornaNumeroAleatorio(), "72265515", new Cidade("Cidade Teste", retornaEstadoAleatorio()));
	}
	
	public static EstadoType retornaEstadoAleatorio() {		
		return EstadoType.values()[random.nextInt(EstadoType.values().length)];
	}
	
	public static Contato retornaContato() {
		return new Contato("testematic@contmatic.com", new Telefone(DDI55, "11", "25068922", null));
	}
	
	public static Departamento retornaDepartamento() {
		return new Departamento(DepartamentoTest.retornaIdAleatorio(), "DepartamentoTeste", "30" + String.valueOf(retornaNumeroAleatorio()));
	}
	
	public static int retornaNumeroAleatorio() {
		return random.nextInt(9999);
	}

	private TestesUtils() {

	}
}
