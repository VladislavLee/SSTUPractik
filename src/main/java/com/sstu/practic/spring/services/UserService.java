package com.sstu.practic.spring.services;

import com.sstu.practic.spring.data.model.TbUser;
import com.sstu.practic.spring.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void addUser(TbUser tbUser){
        userRepository.save(tbUser);
    }

//    public removeUserById(userId){
//        userRepository.deleteById(userId);
//    }
}
