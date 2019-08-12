package com.sstu.practic.spring.data.repositories;

import com.sstu.practic.spring.data.model.TbEquipment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends CrudRepository<TbEquipment, Integer> {

}
