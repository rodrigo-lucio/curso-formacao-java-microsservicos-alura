package br.com.alurafood.pagamentos.shared.util;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Utils {

    public void copyNonNullProperties(Object source, Object destination){
        BeanUtils.copyProperties(source, destination,
                getNullPropertyNames(source));
    }

    public void copyProperties(Object source, Object destination){
        BeanUtils.copyProperties(source, destination, (String[]) null);
    }

    public void copyProperties(Object source, Object destination, String... ignoreProperties){
        BeanUtils.copyProperties(source, destination, ignoreProperties);
    }

    private String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        Set<String> emptyNames = new HashSet<>();
        for(java.beans.PropertyDescriptor descriptor : src.getPropertyDescriptors()) {
            Object srcValue = src.getPropertyValue(descriptor.getName());
            if (srcValue == null) emptyNames.add(descriptor.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public String capitalize(String field) {
        if(StringUtils.isBlank(field)){
            return field;
        }
        String camelCaseCapitalize = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(field), " ");
        return camelCaseCapitalize.substring(0, 1).toUpperCase().concat(camelCaseCapitalize.substring(1).toLowerCase());
    }
}
