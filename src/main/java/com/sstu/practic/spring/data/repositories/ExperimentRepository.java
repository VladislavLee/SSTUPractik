package com.sstu.practic.spring.data.repositories;

import com.sstu.practic.spring.data.model.TbExperiment;
import com.sstu.practic.spring.data.model.TbGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface ExperimentRepository extends CrudRepository<TbExperiment, Integer> {
    public List<TbExperiment> findByIdUser(Integer id);

    public List<TbExperiment> findByGroupList(TbGroup tbGroup);

    public List<TbExperiment> findAllByGroupListContains(TbGroup tbGroup);

    public default List<TbExperiment> findAllByGroupListContainsList(List<TbGroup> groups){
        List<TbExperiment> experiments = new ArrayList<>();
        groups.forEach((x)->experiments.addAll(findAllByGroupListContains(x)));
        return new HashSet<TbExperiment>(experiments).stream().collect(Collectors.toList());
    }

}
