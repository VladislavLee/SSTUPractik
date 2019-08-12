package com.sstu.practic.spring.data.repositories;

import com.sstu.practic.spring.data.model.TbProcessingMethod;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessingMethodRepository extends CrudRepository<TbProcessingMethod, Integer> {

}
