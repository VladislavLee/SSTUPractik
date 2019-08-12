package com.sstu.practic.spring.data.repositories;

import com.sstu.practic.spring.data.model.TbExperimentType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperimentTypeRepository extends CrudRepository<TbExperimentType, Integer> {
}
