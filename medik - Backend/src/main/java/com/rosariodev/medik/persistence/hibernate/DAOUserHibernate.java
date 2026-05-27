package com.rosariodev.medik.persistence.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.rosariodev.medik.enums.ERuolo;
import com.rosariodev.medik.model.User;
import com.rosariodev.medik.persistence.IDAOUser;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;

@Repository
@Profile("hibernate")
@Slf4j
public class DAOUserHibernate extends DAOGenericHibernate<User> implements IDAOUser {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public User findByEmail(String email) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        Predicate emailPredicate = builder.equal(root.get("email"), email);
        query.where(emailPredicate);
        List<User> results = entityManager.createQuery(query).getResultList();
        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

    @Override
    public List<User> findByRole(ERuolo ruolo) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("role"), ruolo));
        query.where(predicates.toArray(new Predicate[] {}));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<User> findMediciDisponibiliAlCambio(Long idPaziente) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("role"), ERuolo.MEDICO));
        query.where(predicates.toArray(new Predicate[] {}));
        List<User> medici = entityManager.createQuery(query).getResultList();
        List<User> listaFiltrata = new ArrayList<>();
        for (User medico : medici) {
            log.info("Medico scansionato: {} {}", medico.getNome(), medico.getCognome());
            boolean pazienteTrovato = false;
            if (medico.getRole() == ERuolo.MEDICO) {
                for (User paziente : medico.getPazienti()) {
                    if (paziente.getId().equals(idPaziente)) {
                        log.info("Paziente scansionato: {} {}", paziente.getNome(), paziente.getCognome());
                        pazienteTrovato = true;
                        break;
                    }
                }
                if (!pazienteTrovato) {
                    listaFiltrata.add(medico);
                }
            }
        }

        return listaFiltrata;
    }

    @Override
    public List<User> findPazientiDalCognomeMedico(Long idMedico, String cognomePaziente) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("role"), ERuolo.MEDICO));
        predicates.add(builder.equal(root.get("id"), idMedico));
        query.where(predicates.toArray(new Predicate[] {}));
        List<User> medici = entityManager.createQuery(query).getResultList();
        List<User> listaFiltrata = new ArrayList<>();
        for (User medico : medici) {
            if (medico.getRole() == ERuolo.MEDICO && medico.getId().equals(idMedico)) {
                for (User paziente : medico.getPazienti()) {
                    if (paziente.getCognome().equalsIgnoreCase(cognomePaziente)) {
                        listaFiltrata.add(paziente);
                    }
                }
                
            }
        }
        return listaFiltrata;
    }

}
