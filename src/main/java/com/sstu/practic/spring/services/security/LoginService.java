package com.sstu.practic.spring.services.security;

import com.sstu.practic.spring.data.model.TbUser;
import com.sstu.practic.spring.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class LoginService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SecurityContext securityContext;


    public boolean login(String login, String password){
        TbUser user = userRepository.findByVcLoginAndVcPassword(login,password).orElse(null);
        if(Objects.isNull(user)) return false;


        securityContext.setUser(user);
        return true;
    }



    public void logout(){
        securityContext.setUser(null);
    }
}
