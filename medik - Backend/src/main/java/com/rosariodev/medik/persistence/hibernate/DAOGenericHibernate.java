package com.rosariodev.medik.persistence.hibernate;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.rosariodev.medik.persistence.DAOException;
import com.rosariodev.medik.persistence.IDAOGeneric;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DAOGenericHibernate<T> implements IDAOGeneric<T> {
    
    @PersistenceContext
    private EntityManager entityManager;

    private final Class<T> persistentClass;



    @SuppressWarnings("unchecked")
    public DAOGenericHibernate() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    protected Class<T> getPersistentClass() {
        return persistentClass;
    }

    @Override
    public T makePersistent(T entity) throws DAOException {
        try {
            entityManager.merge(entity);
        } catch (HibernateException ex) {
            log.error("Error while making persistent object {}", entity, ex);
            throw new DAOException(ex);
        }
        return entity;
    }

    @Override
    public void makeTransient(T entity) throws DAOException {
        try {
            entityManager.remove(entity);
        } catch (HibernateException ex) {
            log.error("Error while making transient object {}", entity, ex);
            throw new DAOException(ex);
        }
    }

    @Override
    public T findById(Long id) throws DAOException {
        List<T> lista = findByEqual("id", id);
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

    @Override
    public List<T> findAll() throws DAOException {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(persistentClass);
        Root<T> root = criteria.from(persistentClass);
        criteria.select(root);
        return entityManager.createQuery(criteria).getResultList();
    }

    public List<T> findByEqual(String attributeName, Object attributeValue) throws DAOException {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(persistentClass);
        Root<T> root = criteria.from(persistentClass);
        criteria.select(root);
        criteria.where(builder.equal(root.get(attributeName), attributeValue));
        return entityManager.createQuery(criteria).getResultList();
    }

    public List<T> findByEqual(Map<String, Object> andSelections) throws DAOException {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(persistentClass);
        Root<T> root = criteria.from(persistentClass);
        criteria.select(root);
        List<Predicate> predicates = new ArrayList<Predicate>();
        for (String attributeName : andSelections.keySet()) {
            Object attributeValue = andSelections.get(attributeName);
            predicates.add(builder.equal(root.get(attributeName), attributeValue));
        }
        Predicate andPredicate = builder.and(predicates.toArray(new Predicate[]{}));
        criteria.where(andPredicate);
        return entityManager.createQuery(criteria).getResultList();
    }

    public List<T> findByEqualIgnoreCase(String attributeName, String attributeValue) throws DAOException {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(persistentClass);
        Root<T> root = criteria.from(persistentClass);
        criteria.select(root);
        Expression<String> path = root.get(attributeName);
        Expression<String> upper = builder.upper(path);
        Predicate ctfPredicate = builder.equal(upper, attributeValue.toUpperCase());
        criteria.where(builder.and(ctfPredicate));
        return entityManager.createQuery(criteria).getResultList();
    }

    public T saveOrMerge(T obj, Long id) throws DAOException {
        try {
            T persistentObject = entityManager.find(persistentClass, id);
            if (persistentObject != null) {
                if (log.isDebugEnabled()) log.debug("Get ha trovato l'oggetto con id " + id);
                return persistentObject;
            } else {
                makePersistent(obj);
                return obj;
            }
        } catch (Exception ex) {
            log.error("Error while saving object {}", obj, ex);
            throw new DAOException(ex);
        }
    }

    public T merge(T obj) throws DAOException {
        try {
            T persistentObject = (T) entityManager.merge(obj);
            return persistentObject;
        } catch (Exception ex) {
            log.error("Error while merging object {}", obj, ex);
            throw new DAOException(ex);
        }
    }
}
