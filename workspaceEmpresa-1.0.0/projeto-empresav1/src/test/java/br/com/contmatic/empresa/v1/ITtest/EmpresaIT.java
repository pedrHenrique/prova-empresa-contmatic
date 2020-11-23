package br.com.contmatic.empresa.v1.ITtest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.contmatic.empresa.v1.test.ContatoTest;
import br.com.contmatic.empresa.v1.test.DepartamentoTest;
import br.com.contmatic.empresa.v1.test.EmpresaTest;
import br.com.contmatic.empresa.v1.test.EnderecoTest;
import br.com.contmatic.empresa.v1.test.FuncionarioTest;
import br.com.contmatic.empresa.v1.test.TelefoneTest;

@RunWith(Suite.class)
@SuiteClasses({EmpresaTest.class, DepartamentoTest.class, FuncionarioTest.class, EnderecoTest.class, ContatoTest.class, TelefoneTest.class})
public class EmpresaIT {

}
