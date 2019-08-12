package com.sstu.practic.spring.services;

import com.sstu.practic.spring.data.model.TbExperiment;
import com.sstu.practic.spring.data.repositories.ExperimentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExperimentService {
    @Autowired
    private ExperimentRepository experimentRepository;

    public void addExperiment(TbExperiment tbExperiment){
        experimentRepository.save(tbExperiment);
    }

    public List<TbExperiment> getAllExperiment() {
        List<TbExperiment> experiments = new ArrayList<>();
        experimentRepository.findAll()
                .forEach(experiments::add);
        return experiments;
    }

    public void updateExperiment(TbExperiment tbExperiment) {
        experimentRepository.save(tbExperiment);
    }


    public void deleteExperiment(TbExperiment tbExperiment){
        experimentRepository.delete(tbExperiment);
    }
}
