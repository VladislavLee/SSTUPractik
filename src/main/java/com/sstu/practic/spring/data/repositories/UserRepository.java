package com.sstu.practic.spring.data.repositories;

import com.sstu.practic.spring.data.model.TbUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<TbUser, Integer> {
        Optional<TbUser> findByVcLoginAndVcPassword(String login, String password);

}