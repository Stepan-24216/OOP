package org.example.dsl;

import groovy.lang.MetaClass;
import groovy.lang.MissingMethodException;
import groovy.lang.MissingPropertyException;
import org.codehaus.groovy.runtime.InvokerHelper;

/**
 * Перенаправляет неизвестные методы и свойства скрипта в DSL-билдер.
 */
public class DelegatingMetaClass extends groovy.lang.DelegatingMetaClass {

    private final Object delegate;

    public DelegatingMetaClass(MetaClass adaptee, Object delegate) {
        super(adaptee);
        this.delegate = delegate;
        initialize();
    }

    @Override
    public Object invokeMethod(Object object, String methodName, Object[] arguments) {
        try {
            return super.invokeMethod(object, methodName, arguments);
        } catch (MissingMethodException e) {
            return InvokerHelper.invokeMethod(delegate, methodName, arguments);
        }
    }

    @Override
    public Object getProperty(Object object, String property) {
        try {
            return super.getProperty(object, property);
        } catch (MissingPropertyException e) {
            return InvokerHelper.getProperty(delegate, property);
        }
    }

    @Override
    public void setProperty(Object object, String property, Object newValue) {
        try {
            super.setProperty(object, property, newValue);
        } catch (MissingPropertyException e) {
            InvokerHelper.setProperty(delegate, property, newValue);
        }
    }
}

