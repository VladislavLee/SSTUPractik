package com.sstu.practic.spring.services;

import com.sstu.practic.spring.data.model.TbMood;
import com.sstu.practic.spring.data.model.TbUser;
import com.sstu.practic.spring.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void addUser(TbUser tbUser){
        userRepository.save(tbUser);
    }


    public List<TbUser> getAllUsers() {
        List<TbUser> users = new ArrayList<>();
        userRepository.findAll()
                .forEach(users::add);
        return users;
    }

    public void updateUsers(TbUser tbUser) {
        userRepository.save(tbUser);
    }

    public void deleteUsers(TbUser tbUser){
        userRepository.delete(tbUser);
    }
}
