package com.sstu.practic.spring.data.repositories;

import com.sstu.practic.spring.data.model.TbExperimentSubject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperimentSubjectRepository extends CrudRepository<TbExperimentSubject, Integer> {
}
