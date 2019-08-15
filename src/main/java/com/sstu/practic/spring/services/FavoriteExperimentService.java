package com.sstu.practic.spring.services;

import com.sstu.practic.spring.data.model.TbExperimentType;
import com.sstu.practic.spring.data.model.TbFavoriteExperiment;
import com.sstu.practic.spring.data.repositories.ExperimentTypeRepository;
import com.sstu.practic.spring.data.repositories.FavoriteExperimentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FavoriteExperimentService {
    @Autowired
    private FavoriteExperimentRepository favoriteExperimentRepository;

    public void addFavoriteExperiment(TbFavoriteExperiment tbFavoriteExperiment){
        favoriteExperimentRepository.save(tbFavoriteExperiment);
    }


    public List<TbFavoriteExperiment> getFavoriteExperiment() {
        List<TbFavoriteExperiment> favoriteExperiments = new ArrayList<>();
        favoriteExperimentRepository.findAll()
                .forEach(favoriteExperiments::add);
        return favoriteExperiments;
    }


    public void deleteFavoriteExperiment(TbFavoriteExperiment tbFavoriteExperiment){
        favoriteExperimentRepository.delete(tbFavoriteExperiment);
    }
}
