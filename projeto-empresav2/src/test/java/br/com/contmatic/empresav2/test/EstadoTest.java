package br.com.contmatic.empresav2.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.junit.runners.MethodSorters;
import br.com.contmatic.empresav2.model.Estado;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EstadoTest {

    Estado st;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("--------- Início Testes Classe EstadoTest --------- \n");
    }

    @Before
    public void setUp() throws Exception {
        
    }

    @After
    public void tearDown() throws Exception {
        
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        System.out.println("--------- Fim de Testes Classe EstadoTest --------- \n");
    }
    
    @Test
    public void teste_getDescricaoViaLista_deve_retornar_enum_se_encontrar_ela_na_lista() {
        String estado = Estado.AM.getNomeViaLista();
        assertThat("Uma String contendo o estado solicitado devia ter sido recebida", estado, not(equalTo(null)));
    }

    @Test
    public void procuraUFEnum_deve_retornar_enumCorreta_se_UF_passada_existir() {
        String uf = "BA";
        Estado ufObtida = Estado.procuraUFEnum(uf);
        
        assertThat(uf.equals(ufObtida.getUfViaLista()), equalTo(true));

    }

    @Test
    public void procuraUFEnum_deve_gerar_exception_se_receber_uf_naoExistente() {
        String uf = "CS";
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("A UF " + uf + " não pode ter sido encontrada.");

        Estado.procuraUFEnum(uf);                
    }

}
