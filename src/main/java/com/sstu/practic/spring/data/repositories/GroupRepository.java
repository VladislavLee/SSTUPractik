package com.sstu.practic.spring.data.repositories;

import com.sstu.practic.spring.data.model.TbExperiment;
import com.sstu.practic.spring.data.model.TbGroup;
import com.sstu.practic.spring.data.model.TbUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends CrudRepository<TbGroup, Integer> {
    public List<TbGroup> findByIdCreator(Integer id);

    public List<TbGroup> findAllByUserListContains(TbUser tbUser);
}
