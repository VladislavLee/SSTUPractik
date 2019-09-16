package com.sstu.practic.spring.data.repositories;

import com.sstu.practic.spring.data.model.TbExperiment;
import com.sstu.practic.spring.data.model.TbFavoriteExperiment;
import com.sstu.practic.spring.data.model.TbUser;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.management.Query;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExperimentRepository extends CrudRepository<TbExperiment, Integer> {
    public List<TbExperiment> findByIdUser(Integer id);

    public List<TbExperiment> findByUserListContains(TbUser user);

    @Override
    @Transactional
    <S extends TbExperiment> S save(S s);
}
