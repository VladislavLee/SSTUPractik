package com.sstu.practic.spring.services;

import com.sstu.practic.spring.data.model.TbMood;
import com.sstu.practic.spring.data.repositories.MoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MoodService {
    @Autowired
    private MoodRepository moodRepository;

    public void addMood(TbMood tbMood){
        moodRepository.save(tbMood);
    }

    public List<TbMood> getAllMoods() {
        List<TbMood> moods = new ArrayList<>();
        moodRepository.findAll()
                .forEach(moods::add);
        return moods;
    }

    public Optional<TbMood> getMood(Integer id) {
        return moodRepository.findById(id);
    }

    public void updateMoods( TbMood tbMood) {
        moodRepository.save(tbMood);
    }


    public void deleteMood(TbMood tbMood){
        moodRepository.delete(tbMood);
    }
}
