package com.sstu.practic.spring.services;

import com.sstu.practic.spring.data.model.TbChannels;
import com.sstu.practic.spring.data.repositories.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChannelService {
    @Autowired
    private ChannelRepository channelRepository;

    public void addChannel(TbChannels tbChannels){
         channelRepository.save(tbChannels);
    }
}
