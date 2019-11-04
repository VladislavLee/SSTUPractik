package com.sstu.practic.spring.services;

import com.sstu.practic.spring.data.model.TbExperiment;
import com.sstu.practic.spring.data.model.TbGroup;
import com.sstu.practic.spring.data.model.TbUser;
import com.sstu.practic.spring.data.repositories.ExperimentRepository;
import com.sstu.practic.spring.data.repositories.GroupRepository;
import com.sstu.practic.spring.services.security.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MyExperimentService {
    @Autowired
    private ExperimentRepository experimentRepository;
    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private GroupRepository groupRepository;


    public List<TbExperiment> getMyExperiment() {
        List<TbGroup> groupList = groupRepository.findAllByUserListContains(securityContext.getUser());
        return experimentRepository.findAllByGroupListContainsList(groupList);
    }
}
