package com.example.spring.services;

import com.example.spring.models.BasicTvShowInfo;
import com.example.spring.repositories.BasicTvShowInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;

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

    // Will return a list of limited basic tv show information depending of the name of the show ( we look for matches in a LIKE pattern sql)
    public List<BasicTvShowInfo> searchBasicTvShowInfo (String originalNameTvShow){
        return basicTvShowInfoRepository.findByOriginalNameStartsWith(PageRequest.of(0,5),originalNameTvShow).getContent();
    }
}
