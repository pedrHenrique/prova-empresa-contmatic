package br.com.contmatic.empresav2.util;

import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * @author Pedro
 *
 */
public interface TestesLogger {        

    public default TestWatcher recebeLogs() {
        return observador;
    }

    @Rule
    public TestWatcher observador = new TestWatcher() {

        @Override
        public void failed(Throwable e, Description description) {
            System.out.println("Teste: " + description.getMethodName() + " Falhou...\nClasse: " + description.getClassName() + "\nMotivo: " + e.toString() + "\n");
            // log.error(msg, e);
        }

        @Override
        public void succeeded(Description description) {            
            System.out.println("Teste: " + description.getMethodName() + " finalizou com sucesso!\n");
        }

    };

}
