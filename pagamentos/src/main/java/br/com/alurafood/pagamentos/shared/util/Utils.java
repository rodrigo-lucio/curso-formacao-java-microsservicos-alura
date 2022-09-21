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
        Set<String> nullPropertyNames = getNullPropertyNames(source);
        copyProperties(source, destination,
                nullPropertyNames.toArray(new String[nullPropertyNames.size()]));
    }

    public void copyNonNullAndIdNotProperties(Object source, Object destination){
        Set<String> nullPropertyNames = getNullPropertyNames(source);
        nullPropertyNames.add("id");
        copyProperties(source, destination,
                nullPropertyNames.toArray(new String[nullPropertyNames.size()]));
    }

    public void copyProperties(Object source, Object destination, String... ignoreProperties){
        BeanUtils.copyProperties(source, destination, ignoreProperties);
    }

    private  Set<String> getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        Set<String> emptyNames = new HashSet<>();
        for(java.beans.PropertyDescriptor descriptor : src.getPropertyDescriptors()) {
            Object srcValue = src.getPropertyValue(descriptor.getName());
            if (srcValue == null) emptyNames.add(descriptor.getName());
        }
        return emptyNames;
    }

    public String capitalize(String field) {
        if(StringUtils.isBlank(field)){
            return field;
        }
        String camelCaseCapitalize = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(field), " ");
        return camelCaseCapitalize.substring(0, 1).toUpperCase().concat(camelCaseCapitalize.substring(1).toLowerCase());
    }
}
