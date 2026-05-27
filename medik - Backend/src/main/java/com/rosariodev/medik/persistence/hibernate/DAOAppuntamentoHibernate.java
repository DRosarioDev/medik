package com.rosariodev.medik.persistence.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.rosariodev.medik.model.Appuntamento;
import com.rosariodev.medik.persistence.IDAOAppuntamento;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
@Profile("hibernate")
public class DAOAppuntamentoHibernate extends DAOGenericHibernate<Appuntamento> implements IDAOAppuntamento {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Appuntamento> findByMedicoId(Long medicoId) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Appuntamento> query = builder.createQuery(Appuntamento.class);
        Root<Appuntamento> root = query.from(Appuntamento.class);
        query.select(root);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("medico").get("id"), medicoId));
        query.where(predicates.toArray(new Predicate[] {}));
        return entityManager.createQuery(query).getResultList();

        // List<Appuntamento> appuntamenti = new ArrayList<>();
        // for (Appuntamento appuntamento : findAll()) {
        //     if (appuntamento.getMedico().getId().equals(medicoId)) {
        //         appuntamenti.add(appuntamento);
        //     }
        // }
        // return appuntamenti;
    }

    @Override
    public List<Appuntamento> findByPazienteId(Long pazienteId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Appuntamento> query = builder.createQuery(Appuntamento.class);
        Root<Appuntamento> root = query.from(Appuntamento.class);
        query.select(root);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("paziente").get("id"), pazienteId));
        query.where(predicates.toArray(new Predicate[] {}));
        return entityManager.createQuery(query).getResultList();
    }

}


