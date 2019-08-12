package com.sstu.practic.spring.services;

import com.sstu.practic.spring.data.model.TbExperimentDesign;
import com.sstu.practic.spring.data.repositories.ExperimentDesignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class ExperimentDesignService {
    @Autowired
    private ExperimentDesignRepository experimentDesignRepository;

    public void addExperimentDesign(TbExperimentDesign tbExperimentDesign){
        experimentDesignRepository.save(tbExperimentDesign);
    }

    public List<TbExperimentDesign> getAllExperimentDesign() {
        List<TbExperimentDesign> experimentDesigns = new ArrayList<>();
        experimentDesignRepository.findAll()
                .forEach(experimentDesigns::add);
        return experimentDesigns;
    }

    public void updateExperimentDesign(TbExperimentDesign tbExperimentDesign) {
        experimentDesignRepository.save(tbExperimentDesign);
    }


    public void deleteExperimentDesign(TbExperimentDesign tbExperimentDesign){
        experimentDesignRepository.delete(tbExperimentDesign);
    }

}
