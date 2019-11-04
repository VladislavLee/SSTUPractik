package com.sstu.practic.spring.services;

import com.sstu.practic.spring.data.model.TbExperiment;
import com.sstu.practic.spring.data.model.TbUser;
import com.sstu.practic.spring.data.repositories.ExperimentRepository;
import com.sstu.practic.spring.data.repositories.FavoriteExperimentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Component
public class ExperimentService {
    @Autowired
    private ExperimentRepository experimentRepository;
    @Autowired
    private FavoriteExperimentRepository favoriteExperimentRepository;
    @Autowired
    private FavoriteExperimentService favoriteExperimentService;


//    public void addUserList(TbExperiment tbExperiment, List<TbUser> newUsers){
//        List<TbUser> users = tbExperiment.getUserList();
//
//        newUsers.forEach((x)->{
//            if(!users.contains(x)) users.add(x);
//        });
//
//        experimentRepository.save(tbExperiment);
//    }
//
//    public void removeUsers(TbExperiment tbExperiment, List<TbUser> newUsers){
//        List<TbUser> users = tbExperiment.getUserList();
//
//        newUsers.forEach((x)->{
//            if(!users.contains(x)) users.remove(x);
//        });
//
//        experimentRepository.save(tbExperiment);
//    }

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
