package br.com.contmatic.empresav2.ITtest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import br.com.contmatic.empresav2.test.DepartamentoTest;
import br.com.contmatic.empresav2.test.EmpresaTest;
import br.com.contmatic.empresav2.test.EnderecoTest;
import br.com.contmatic.empresav2.test.FuncionarioTest;

public class TestRunner {

	public static void main(String[] args) {
		Result resultado = JUnitCore.runClasses(EmpresaTest.class, DepartamentoTest.class,
		 FuncionarioTest.class, FuncionarioIT.class, EnderecoTest.class);

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
