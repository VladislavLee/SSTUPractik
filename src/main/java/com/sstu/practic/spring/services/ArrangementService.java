package com.sstu.practic.spring.services;

import com.sstu.practic.spring.data.model.TbArrangements;
import com.sstu.practic.spring.data.model.TbChannels;
import com.sstu.practic.spring.data.repositories.ArrangementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArrangementService {
    @Autowired
    private ArrangementRepository arrangementRepository;

    public void addArrangement(TbArrangements tbArrangements){
        arrangementRepository.save(tbArrangements);
    }

    public List<TbArrangements> getAllArrangements() {
        List<TbArrangements> arrangements= new ArrayList<>();
        arrangementRepository.findAll()
                .forEach(arrangements::add);
        return arrangements;
    }

    public void updateArrangement(TbArrangements tbArrangements) {
        arrangementRepository.save(tbArrangements);
    }

    public void deleteArrangement(TbArrangements tbArrangements){
        arrangementRepository.delete(tbArrangements);
    }
}
