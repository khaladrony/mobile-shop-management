/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rony.erpsoft.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Set;


/**
 *
 * @author juba
 */
@Configuration
public  class ModelValidator {
    @Autowired
    ValidatorFactory factory;
    
    public <T> boolean isValid(T model){
        int siz=factory.getValidator().validate(model).size();
        return siz<1;
    }
    
    public <T> String validationMessage(T model){
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraints = validator.validate(model);
        StringBuilder sb=new  StringBuilder();
        for (ConstraintViolation<T> constraint : constraints) {
	  //sb.append(constraint.getPropertyPath() + "  "+ constraint.getMessage()+";");
          sb.append(constraint.getMessage());
        }
        return sb.toString();
    }
}
