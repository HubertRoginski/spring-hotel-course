package org.springproject.springproject.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springproject.springproject.model.Personnel;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@Deprecated
public class OldPersonnelRepositoryImpl implements OldPersonnelRepository {

    private final EntityManager entityManager;

    public OldPersonnelRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Personnel create(Personnel personnel) {
        entityManager.persist(personnel);
        return personnel;
    }

    @Override
    public Optional<Personnel> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Personnel> findAll() {
        return null;
    }

    @Override
    public Personnel update(Personnel personnel) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
