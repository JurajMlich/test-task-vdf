package cz.artin.vodafone.logprocessorservice.service.log.analyzer;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;

public class EmptyConstrainViolation<T> implements ConstraintViolation<T> {

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public String getMessageTemplate() {
        return null;
    }

    @Override
    public T getRootBean() {
        return null;
    }

    @Override
    public Class<T> getRootBeanClass() {
        return null;
    }

    @Override
    public Object getLeafBean() {
        return null;
    }

    @Override
    public Object[] getExecutableParameters() {
        return new Object[0];
    }

    @Override
    public Object getExecutableReturnValue() {
        return null;
    }

    @Override
    public Path getPropertyPath() {
        return null;
    }

    @Override
    public Object getInvalidValue() {
        return null;
    }

    @Override
    public ConstraintDescriptor<?> getConstraintDescriptor() {
        return null;
    }

    @Override
    public <U> U unwrap(Class<U> type) {
        return null;
    }
}
