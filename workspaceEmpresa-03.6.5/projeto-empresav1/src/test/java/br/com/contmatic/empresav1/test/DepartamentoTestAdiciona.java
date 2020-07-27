package br.com.contmatic.empresav1.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeNoException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.contmatic.empresav1.model.Departamento;

public class DepartamentoTestAdiciona {

	Departamento departamento;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		new Departamento(10, "Junior", 155);
		new Departamento(11, "Roberto", 285);
		new Departamento(12, "Ana", 405);
	}

	/*
	 * Está seção de testes tem o intuito de testar os métodos de criação dos
	 * objetos da classe
	 */

	@Test // Testa criando o obj pelo construtor
	public void teste_objeto_criado_por_construtor() {
		long passaValor = 1;
		String passaNome = "Carlos";
		int passaRamal = 145;

		Departamento test1 = new Departamento(passaValor, passaNome, passaRamal);
		assertEquals("O Obj esperado era: ", test1, test1.solicitarDep(passaValor));
	}

	@Test
	public void teste_objeto_criado_por_metodo_com_parametros() {
		long passaValor = 2;
		String passaNome = "Rogerio";
		int passaRamal = 100;

		Departamento test2 = new Departamento();
		test2.registrarDep(passaValor, passaNome, passaRamal);
		assertEquals("O Obj esperado era:", test2, test2.solicitarDep(passaValor));
		assertNotNull(test2.solicitarDep(passaValor));

	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_objeto_criado_ja_existente_() {
		new Departamento(3, "Rogerio", 145);
		new Departamento(3, "Claudio", 025); // deve falhar

	}

	/*
	 * Está seção de testes tem o intuito de testar os métodos de remoção dos
	 * objetos da classe
	 */

	@Test
	public void teste_remocao_objeto_existente() {
		long id = 250;
		Departamento dep = new Departamento(id, "Rogerio", 145);
		assertEquals(dep, dep.removerDep(id));

	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_remocao_objeto_nao_existente() {
		long id = 179;
		Departamento dep = new Departamento();
		dep.removerDep(id);
	}

	/*
	 * Está seção de testes tem o intuito de testar métodos de busca de objetos da
	 * classe
	 */

	@Test // Testando a busca por objetos num HashSet
	public void teste_busca_departamento_existente() {
		Departamento dep = new Departamento();
		assertNotNull("Esperava receber uma lista de objetos", dep.solicitarDep(10));
		assertNotNull("Esperava receber uma lista de objetos", dep.solicitarDep(11));
		assertNotNull("Esperava receber uma lista de objetos", dep.solicitarDep(12));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_busca_departamento_nao_existente() {
		Departamento dep = new Departamento();
		dep.solicitarDep(50); // deve falhar

	}

	/*
	 * Está seção de testes tem o intuito de testar os getters/setters da classe
	 */

	@Test
	public void teste_setNome_e_getNome_nome_correto() {
		String name = new String("Ana");
		Departamento dep = new Departamento();
		dep.setNome(name);
		assertEquals(name, dep.getNome());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void teste_setNome_valor_nulo() {
		String name = null;
		Departamento dep = new Departamento();
		dep.setNome(name);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_setNome_valor_vazio() {
		String name = "";
		Departamento dep = new Departamento();
		dep.setNome(name);
	}

	@Test
	public void teste_setId_e_getId_correto() {
		long id = 25;
		Departamento dep = new Departamento();
		dep.setIdDepartamento(id);
		assertEquals(id, dep.getIdDepartamento());
	}

	@SuppressWarnings("null")
	@Test(expected = java.lang.NullPointerException.class)
	public void teste_setId_valor_nulo() {
		Departamento dep = new Departamento();
		long id = (Long) null;
		dep.setIdDepartamento(id);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_setId_valor_vazio() {
		Departamento dep = new Departamento();
		long id = 0;
		dep.setIdDepartamento(id);
	}

	@Test
	public void teste_setRamal_e_getId_correto() {
		int num = 456;
		Departamento dep = new Departamento();
		dep.setRamal(num);
		assertEquals(num, dep.getRamal());
	}

	@SuppressWarnings("null")
	@Test(expected = java.lang.NullPointerException.class)
	public void teste_setRamal_valor_nulo() {
		Departamento dep = new Departamento();
		int num = (Integer) null;
		dep.setIdDepartamento(num);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void teste_setRamal_valor_vazio() {
		Departamento dep = new Departamento();
		int num = 0;
		dep.setRamal(num);
	}

	/*
	 * Está seção de testes tem o intuito de testar os métodos de listagem
	 */

	@Test
	public void teste_listar_departamentos() {
		Departamento dep = new Departamento();
		assertNotNull(dep.listarDepartamentos());
	}

	@Test
	public void teste_toString() {
		Departamento dep = new Departamento();
		assertNotNull(dep.toString());
	}
}
