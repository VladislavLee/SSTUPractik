package com.sstu.practic.spring.services.security;

import com.sstu.practic.spring.data.model.TbUser;
import com.sstu.practic.spring.data.repositories.UserRepository;
import com.sstu.practic.spring.services.security.entites.Role;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
@Data
public class SecurityContext {
    private TbUser user;

    public boolean isAuthenticated(){
        return !Objects.isNull(user);
    }

    @Autowired
    public void init(UserRepository userRepository) {
        if (!userRepository.findByVcLoginAndVcPassword("user", "111").isPresent()) {
            userRepository.save(TbUser.builder().vcLogin("user").vcPassword("111").vcRole(Role.ADMIN).build());
        }
    }
}
