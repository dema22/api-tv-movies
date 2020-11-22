package com.example.spring.services;

import com.example.spring.models.BasicTvShowInfo;
import com.example.spring.repositories.BasicTvShowInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BasicTvShowInfoService {

    private final BasicTvShowInfoRepository basicTvShowInfoRepository;

    @Autowired
    public BasicTvShowInfoService(BasicTvShowInfoRepository basicTvShowInfoRepository) {
        this.basicTvShowInfoRepository = basicTvShowInfoRepository;
    }

    public void saveListOfBasicTvShows(List<BasicTvShowInfo> listBasicTvShowInfo) {
        basicTvShowInfoRepository.saveAll(listBasicTvShowInfo);
    }

    public Optional<BasicTvShowInfo> getBasicTvShowInfo (Integer idBasicTvShowInfo){
        return basicTvShowInfoRepository.findById(idBasicTvShowInfo);
    }
}
