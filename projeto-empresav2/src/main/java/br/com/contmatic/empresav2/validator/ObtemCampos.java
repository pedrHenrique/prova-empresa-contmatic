/**
 * 
 */
package br.com.contmatic.empresav2.validator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

/**
 * @author Pedro
 *
 */
public interface ObtemCampos {               
    
   
    /**
     * Método auxiliar que retorna todos os campos de uma classe.<br>
     * Pode ser iniciado passando passando a classe a qual deseja se obter os campos. Excluindo os campos da super classe Object<p>
     * O método também pode receber os campos de classes extendidas aparentemente.     
     *
     *
     * @param startClass the start class
     * @param exclusiveParent the exclusive parent
     * @return the fields up to
     * 
     * <p><p>
     * <pre>
     * Retirado de: https://stackoverflow.com/questions/16966629/what-is-the-difference-between-getfields-and-getdeclaredfields-in-java-reflectio
     * </pre>
     * 
     */
    public static Iterable<Field> getFieldsUpTo(@Nonnull Class<?> startClass, 
                                                @Nullable Class<?> exclusiveParent) {

                List<Field> currentClassFields = Lists.newArrayList(startClass.getDeclaredFields());
                Class<?> parentClass = startClass.getSuperclass();

                if (parentClass != null && 
                       (exclusiveParent == null || !(parentClass.equals(exclusiveParent)))) {
                  List<Field> parentClassFields = 
                      (List<Field>) getFieldsUpTo(parentClass, exclusiveParent);
                  currentClassFields.addAll(parentClassFields);
                }

                return currentClassFields;
             }

}
