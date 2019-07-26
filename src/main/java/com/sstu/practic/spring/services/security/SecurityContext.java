package com.sstu.practic.spring.services.security;

import com.sstu.practic.spring.data.model.TbUser;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Data
public class SecurityContext {
    private TbUser user;

    public boolean isAuthenticated(){
        return !Objects.isNull(user);
    }
}
