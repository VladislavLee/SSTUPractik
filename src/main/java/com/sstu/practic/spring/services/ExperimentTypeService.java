package com.sstu.practic.spring.services;


import com.sstu.practic.spring.data.model.TbExperimentType;
import com.sstu.practic.spring.data.model.TbMood;
import com.sstu.practic.spring.data.repositories.ExperimentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExperimentTypeService {
    @Autowired
    private ExperimentTypeRepository experimentTypeRepository;

    public void addExperimentType(TbExperimentType tbExperimentType){
        experimentTypeRepository.save(tbExperimentType);
    }

    public List<TbExperimentType> getExperimentType() {
        List<TbExperimentType> experimentTypes = new ArrayList<>();
        experimentTypeRepository.findAll()
                .forEach(experimentTypes::add);
        return experimentTypes;
    }

    public void updateExperimentType(TbExperimentType tbExperimentType) {
        experimentTypeRepository.save(tbExperimentType);
    }


    public void deleteExperimentType(TbExperimentType tbExperimentType){
        experimentTypeRepository.delete(tbExperimentType);
    }
}
