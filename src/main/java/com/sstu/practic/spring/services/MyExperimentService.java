package com.sstu.practic.spring.services;

import com.sstu.practic.spring.data.model.TbExperiment;
import com.sstu.practic.spring.data.model.TbProcessingMethod;
import com.sstu.practic.spring.data.repositories.ExperimentRepository;
import com.sstu.practic.spring.services.security.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyExperimentService {
    @Autowired
    private ExperimentRepository experimentRepository;
    @Autowired
    private SecurityContext securityContext;

    public List<TbExperiment> getMyExperiment(Integer id) {
        List<TbExperiment> experiments = experimentRepository.findByIdUser(id);
        experiments.addAll(experimentRepository.findByUserListContains(securityContext.getUser()));
        return experiments;
    }
}
