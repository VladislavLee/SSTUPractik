package com.sstu.practic.spring.services;

import com.sstu.practic.spring.data.model.TbExperiment;
import com.sstu.practic.spring.data.model.TbExperimentType;
import com.sstu.practic.spring.data.model.TbFavoriteExperiment;
import com.sstu.practic.spring.data.repositories.ExperimentTypeRepository;
import com.sstu.practic.spring.data.repositories.FavoriteExperimentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class FavoriteExperimentService {
    @Autowired
    private FavoriteExperimentRepository favoriteExperimentRepository;

    public void addFavoriteExperiment(TbExperiment tbExperiment){
        favoriteExperimentRepository.save(convert(tbExperiment));
    }

    private TbFavoriteExperiment convert(TbExperiment tbExperiment){
        TbFavoriteExperiment tbFavoriteExperiment = new TbFavoriteExperiment();
        tbFavoriteExperiment.setTbExperiment(tbExperiment);
        return tbFavoriteExperiment;
    }

    public  List<TbFavoriteExperiment> getFavoriteExperiment() {
        return  favoriteExperimentRepository.findAll();
    }

    public void deleteFavoriteExperiment(Integer idFavoriteExperiment){
        favoriteExperimentRepository.deleteById(idFavoriteExperiment);
    }

    public void deleteFavoriteByExperiment(TbExperiment tbExperiment){
        favoriteExperimentRepository.deleteByTbExperiment(tbExperiment);
    }
}
