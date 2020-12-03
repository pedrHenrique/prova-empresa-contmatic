package br.com.contmatic.ITtest.v1.empresa;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import br.com.contmatic.model.util.AtributoValidatorTest;
import br.com.contmatic.model.v1.empresa.ContatoTest;
import br.com.contmatic.model.v1.empresa.DepartamentoTest;
import br.com.contmatic.model.v1.empresa.EmpresaTest;
import br.com.contmatic.model.v1.empresa.FuncionarioTest;
import br.com.contmatic.model.v1.endereco.CidadeTest;
import br.com.contmatic.model.v1.endereco.EnderecoTest;
import br.com.contmatic.model.v1.endereco.EstadoTest;
import br.com.contmatic.model.v1.telefone.TelefoneTest;

public class TestRunner {

	public static void main(String[] args) {
		Result resultado = JUnitCore.runClasses(EmpresaTest.class, DepartamentoTest.class, FuncionarioTest.class,
												EnderecoTest.class, ContatoTest.class, TelefoneTest.class,
												AtributoValidatorTest.class, CidadeTest.class, EstadoTest.class);

		for (Failure falha : resultado.getFailures()) {
			System.out.println(falha.toString());
		}
		
		if(resultado.wasSuccessful()) {
			System.out.println("\n -> Testes Finalizados com Sucesso :) <- ");
		} else {
			System.out.println("\n -> Testes Finalizados com erros <- ");
		}
		System.out.println("\nQuantidade de testes rodados: " + resultado.getRunCount());
		System.out.println("Quantidade de testes com falhas: " + resultado.getFailureCount());
		System.out.println("Quantidade de testes ignorados: " + resultado.getIgnoreCount());
		System.out.println("\nTempo de execução total em milissegundos: " + resultado.getRunTime());		
	}
}
