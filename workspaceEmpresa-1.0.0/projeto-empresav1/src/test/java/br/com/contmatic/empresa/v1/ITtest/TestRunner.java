package br.com.contmatic.empresa.v1.ITtest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import br.com.contmatic.empresa.v1.test.ContatoTest;
import br.com.contmatic.empresa.v1.test.DepartamentoTest;
import br.com.contmatic.empresa.v1.test.EmpresaTest;
import br.com.contmatic.empresa.v1.test.EnderecoTest;
import br.com.contmatic.empresa.v1.test.FuncionarioTest;
import br.com.contmatic.empresa.v1.test.TelefoneTest;

public class TestRunner {

	public static void main(String[] args) {
		Result resultado = JUnitCore.runClasses(EmpresaTest.class, DepartamentoTest.class, FuncionarioTest.class,
												EnderecoTest.class, ContatoTest.class, TelefoneTest.class);

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
		System.out.println("\nTempo de execução total: " + resultado.getRunTime());
	}

}
