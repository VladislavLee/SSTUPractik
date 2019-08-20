package com.sstu.practic.spring.data.repositories;

import com.sstu.practic.spring.data.model.TbExperiment;
import com.sstu.practic.spring.data.model.TbFavoriteExperiment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteExperimentRepository extends CrudRepository<TbFavoriteExperiment, Integer> {
    public List<TbFavoriteExperiment> findAll();

    Optional<TbExperiment> findByTbExperiment(TbExperiment tbExperiment);

 }
