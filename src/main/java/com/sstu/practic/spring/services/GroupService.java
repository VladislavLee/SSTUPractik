package com.sstu.practic.spring.services;

import com.sstu.practic.spring.data.model.TbExperiment;
import com.sstu.practic.spring.data.model.TbGroup;
import com.sstu.practic.spring.data.model.TbUser;
import com.sstu.practic.spring.data.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    public List<TbGroup> getAllGroup() {
        List<TbGroup> tbGroups = new ArrayList<>();
        groupRepository.findAll()
                .forEach(tbGroups::add);
        return tbGroups;
    }


    public List<TbGroup> getMyGroup(Integer id) {
        List<TbGroup> tbGroups = groupRepository.findByIdCreator(id);
//        experiments.addAll(experimentRepository.findByUserListContains(securityContext.getUser()));
        return tbGroups;
    }

    public void addGroup(TbGroup tbGroup){
        groupRepository.save(tbGroup);
    }

    public void updateGroup(TbGroup tbGroup) {
        groupRepository.save(tbGroup);
    }

    public void addUserList(TbGroup tbGroup, List<TbUser> newUsers){
        List<TbUser> users = tbGroup.getUserList();

        newUsers.forEach((x)->{
            if(!users.contains(x)) users.add(x);
        });

        groupRepository.save(tbGroup);
    }

    public void deleteGroup(TbGroup tbGroup){
        groupRepository.delete(tbGroup);
    }
}
