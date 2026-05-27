package com.rosariodev.medik.persistence.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.rosariodev.medik.model.Ricetta;
import com.rosariodev.medik.persistence.IDAORicetta;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
@Profile("hibernate")
public class DAORicettaHibernate extends DAOGenericHibernate<Ricetta> implements IDAORicetta {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Ricetta> findByIdPaziente(Long idPaziente) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ricetta> query = builder.createQuery(Ricetta.class);
        Root<Ricetta> root = query.from(Ricetta.class);
        query.select(root);
        List<Predicate> predicate = new ArrayList<>();
        predicate.add(builder.equal(root.get("paziente").get("id"), idPaziente));
        query.where(predicate.toArray(new Predicate[] {}));
        return entityManager.createQuery(query).getResultList();
    }
    
    
}
