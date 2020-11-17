package com.example.spring.services;

import com.example.spring.models.BasicTvShowInfo;
import com.example.spring.repositories.BasicTvShowInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class BasicTvShowInfoService {
    private final BasicTvShowInfoRepository basicTvShowInfoRepository;
    @PersistenceContext
    private EntityManager em;

    @Autowired
    public BasicTvShowInfoService(BasicTvShowInfoRepository basicTvShowInfoRepository, EntityManager em) {
        this.basicTvShowInfoRepository = basicTvShowInfoRepository;
        this.em = em;
    }

    public void saveListOfBasicTvShows(List<BasicTvShowInfo> listBasicTvShowInfo) {
        basicTvShowInfoRepository.saveAll(listBasicTvShowInfo);
    }
}
