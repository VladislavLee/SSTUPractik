package com.sstu.practic.spring.data.repositories;

import com.sstu.practic.spring.data.model.TbArrangements;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArrangementRepository extends CrudRepository<TbArrangements, Integer> {
}
