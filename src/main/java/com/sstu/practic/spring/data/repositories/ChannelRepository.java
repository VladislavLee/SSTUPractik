package com.sstu.practic.spring.data.repositories;

import com.sstu.practic.spring.data.model.TbChannels;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChannelRepository extends CrudRepository<TbChannels, Integer> {

}
