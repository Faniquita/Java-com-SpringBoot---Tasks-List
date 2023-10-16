package br.com.todolistberock.todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.apache.catalina.authenticator.jaspic.PersistentProviderRegistrations.Property;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {

    


    /*
     * COPIA O OBJETO PARA VERIFICAÇÃO DOS DADOS ALTERADOS
     * 
     * Sendo alterado passa o valor
     * Não sendo alterado, vem null, e o valor nulo é sustituido pelo valor que foi copiado
     * 
    */

    
    //Copindo os valores
    public static void copyNonNullProperties(Object source, Object target){
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }


    // Fazendo a verificação dos dados da Task que estiverem nulos
    public static String[] getNullPropertyNames(Object source){
        final BeanWrapper src = new BeanWrapperImpl(source);

        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();

        for(PropertyDescriptor pd:pds){
            Object srcValue = src.getPropertyValue(pd.getName());
            if(srcValue == null){
                emptyNames.add(pd.getName());
            }
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);

    }

}
