package org.youcode.citronix.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.youcode.citronix.domain.Farm;
import org.youcode.citronix.service.dto.FarmSearchDto;

import java.util.ArrayList;
import java.util.List;
@Repository
public class FarmRepositoryImpl {
    @PersistenceContext
    private final EntityManager em;

    public FarmRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    public List<Farm> findByCriteria(FarmSearchDto searchDto) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Farm> cq = cb.createQuery(Farm.class);
        Root<Farm> farmRoot = cq.from(Farm.class);

        List<Predicate> predicates = new ArrayList<>();

        if (searchDto.getName() != null && !searchDto.getName().isEmpty()) {
            predicates.add(cb.like(
                    cb.lower(farmRoot.get("name")),
                    "%" + searchDto.getName().toLowerCase() + "%"
            ));
        }


        if (searchDto.getLocation() != null && !searchDto.getLocation().isEmpty()) {
            predicates.add(cb.like(
                    cb.lower(farmRoot.get("location")),
                    "%" + searchDto.getLocation().toLowerCase() + "%"
            ));
        }


        if (searchDto.getCreated_at() != null) {
            predicates.add(cb.equal(farmRoot.get("created_at"), searchDto.getCreated_at()
            ));
        }

        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[0])));
        }

       return em.createQuery(cq).getResultList();
    }
}
