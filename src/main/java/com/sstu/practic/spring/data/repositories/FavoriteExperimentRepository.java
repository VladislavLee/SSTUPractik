package com.sstu.practic.spring.data.repositories;

import com.sstu.practic.spring.data.model.TbFavoriteExperiment;
import org.springframework.data.repository.CrudRepository;

public interface FavoriteExperimentRepository extends CrudRepository<TbFavoriteExperiment, Integer> {

}
