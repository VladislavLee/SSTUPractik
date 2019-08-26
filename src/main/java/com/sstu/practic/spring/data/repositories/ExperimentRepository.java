package com.sstu.practic.spring.data.repositories;

import com.sstu.practic.spring.data.model.TbExperiment;
import com.sstu.practic.spring.data.model.TbFavoriteExperiment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExperimentRepository extends CrudRepository<TbExperiment, Integer> {
    public List<TbExperiment> findByIdUser(Integer id);

}
