package com.sstu.practic.spring.services;

import com.sstu.practic.spring.data.model.TbExperiment;
import com.sstu.practic.spring.data.model.TbProcessingMethod;
import com.sstu.practic.spring.data.repositories.ExperimentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyExperimentService {
    @Autowired
    private ExperimentRepository experimentRepository;

    public List<TbExperiment> getMyExperiment(Integer id) {
        return experimentRepository.findByIdUser(id);
    }
}
