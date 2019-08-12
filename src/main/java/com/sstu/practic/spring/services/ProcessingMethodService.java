package com.sstu.practic.spring.services;

import com.sstu.practic.spring.data.model.TbMood;
import com.sstu.practic.spring.data.model.TbProcessingMethod;
import com.sstu.practic.spring.data.repositories.ProcessingMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProcessingMethodService {
    @Autowired
    private ProcessingMethodRepository processingMethodRepository;

    public void addProcessingMethod(TbProcessingMethod tbProcessingMethod){
        processingMethodRepository.save(tbProcessingMethod);
    }

    public List<TbProcessingMethod> getAllProcessingMethods() {
        List<TbProcessingMethod> processingMethods = new ArrayList<>();
        processingMethodRepository.findAll()
                .forEach(processingMethods::add);
        return processingMethods;
    }

    public void updateProcessingMethod( TbProcessingMethod tbProcessingMethod) {
        processingMethodRepository.save(tbProcessingMethod);
    }


    public void deleteProcessingMethod(TbProcessingMethod tbProcessingMethod){
        processingMethodRepository.delete(tbProcessingMethod);
    }
}
