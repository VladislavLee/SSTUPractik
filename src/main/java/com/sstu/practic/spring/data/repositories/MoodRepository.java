package com.sstu.practic.spring.data.repositories;

import com.sstu.practic.spring.data.model.TbMood;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoodRepository extends CrudRepository<TbMood, Integer> {

}
