package br.com.contmatic.ITtest.v1.empresa;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.contmatic.model.v1.empresa.ContatoTest;
import br.com.contmatic.model.v1.empresa.DepartamentoTest;
import br.com.contmatic.model.v1.empresa.EmpresaTest;
import br.com.contmatic.model.v1.empresa.FuncionarioTest;
import br.com.contmatic.model.v1.endereco.CidadeTest;
import br.com.contmatic.model.v1.endereco.EnderecoTest;
import br.com.contmatic.model.v1.endereco.EstadoTest;
import br.com.contmatic.model.v1.telefone.TelefoneTest;
import br.com.contmatic.util.v1.DataFormatterTest;
import br.com.contmatic.util.v1.validator.ValidatorIT;

@RunWith(Suite.class)
@SuiteClasses({EmpresaTest.class, DepartamentoTest.class, FuncionarioTest.class, EnderecoTest.class, ContatoTest.class, TelefoneTest.class,
			   ValidatorIT.class, CidadeTest.class, EstadoTest.class, DataFormatterTest.class})
public class EmpresaIT {

}
