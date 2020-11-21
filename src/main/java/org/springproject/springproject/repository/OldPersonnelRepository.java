package org.springproject.springproject.repository;

import org.springproject.springproject.model.Personnel;

import java.util.List;
import java.util.Optional;

@Deprecated
public interface OldPersonnelRepository {

    Personnel create(Personnel personnel);

    Optional<Personnel> findById(Long id);

    List<Personnel> findAll();

    Personnel update(Personnel personnel);

    void delete(Long id);

}
