package com.sstu.practic.spring.services;

import com.sstu.practic.spring.data.model.TbChannels;
import com.sstu.practic.spring.data.repositories.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ChannelService {
    @Autowired
    private ChannelRepository channelRepository;

    public void addChannel(TbChannels tbChannels){
         channelRepository.save(tbChannels);
    }


    public List<TbChannels> getAllChannels() {
        List<TbChannels> channels= new ArrayList<>();
        channelRepository.findAll()
                .forEach(channels::add);
        return channels;
    }


    public void updateChannel( TbChannels tbChannels) {
        channelRepository.save(tbChannels);
    }


    public void deleteChannel(TbChannels tbChannels){
        channelRepository.delete(tbChannels);
    }


    public Optional<TbChannels> getChannel(Integer id) {
        Optional<TbChannels> tbChannels = channelRepository.findById(id);
        return tbChannels;
    }

}
