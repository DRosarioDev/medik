package com.rosariodev.medik.persistence.mock;

import com.rosariodev.medik.persistence.DAOException;
import com.rosariodev.medik.persistence.IDAOGeneric;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class DAOGenericMock<T> implements IDAOGeneric<T> {

    private final Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public DAOGenericMock() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    protected Class<T> getPersistentClass() {
        return persistentClass;
    }

    @Override
    public T makePersistent(T entity) throws DAOException {
        RepositoryMock repositoryMock = RepositoryMock.getInstance();
        repositoryMock.saveOrUpdate(entity);
        return entity;
    }

    @Override
    public void makeTransient(T entity) throws DAOException {
        RepositoryMock repositoryMock = RepositoryMock.getInstance();
        repositoryMock.delete(entity);
    }

    @Override
    public T findById(Long id) throws DAOException {
        RepositoryMock repositoryMock = RepositoryMock.getInstance();
        return repositoryMock.findById(id, persistentClass);
    }

    @Override
    public List<T> findAll() throws DAOException {
        RepositoryMock repositoryMock = RepositoryMock.getInstance();
        return repositoryMock.findAll(persistentClass);
    }

}

