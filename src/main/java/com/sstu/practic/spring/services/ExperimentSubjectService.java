package com.sstu.practic.spring.services;

import com.sstu.practic.spring.data.model.TbExperimentSubject;
import com.sstu.practic.spring.data.model.TbExperimentType;
import com.sstu.practic.spring.data.model.TbUser;
import com.sstu.practic.spring.data.repositories.ExperimentSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExperimentSubjectService {
    @Autowired
    private ExperimentSubjectRepository experimentSubjectRepository;

    public void addExperimentSubject(TbExperimentSubject tbExperimentSubject){
        experimentSubjectRepository.save(tbExperimentSubject);
    }

    public List<TbExperimentSubject> getAllUSubject() {
        List<TbExperimentSubject> subjects = new ArrayList<>();
        experimentSubjectRepository.findAll()
                .forEach(subjects::add);
        return subjects;
    }

    public void updateSubject(TbExperimentSubject tbExperimentSubject) {
        experimentSubjectRepository.save(tbExperimentSubject);
    }


    public void deleteSubject(TbExperimentSubject tbExperimentSubject){
        experimentSubjectRepository.delete(tbExperimentSubject);
    }
}
