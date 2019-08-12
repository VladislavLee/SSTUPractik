package com.sstu.practic.spring.services;

import com.sstu.practic.spring.data.model.TbChannels;
import com.sstu.practic.spring.data.model.TbEquipment;
import com.sstu.practic.spring.data.model.TbMood;
import com.sstu.practic.spring.data.repositories.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EquipmentService {
    @Autowired
    private EquipmentRepository equipmentRepository;

    public void addEquipment(TbEquipment tbEquipment){
        equipmentRepository.save(tbEquipment);
    }

    public List<TbEquipment> getAllEquipments() {
        List<TbEquipment> equipments = new ArrayList<>();
        equipmentRepository.findAll()
                .forEach(equipments::add);
        return equipments;
    }

    public void updateEquipments( TbEquipment tbEquipment) {
        equipmentRepository.save(tbEquipment);
    }


    public void deleteEquipments(TbEquipment tbEquipment){
        equipmentRepository.delete(tbEquipment);
    }
}
