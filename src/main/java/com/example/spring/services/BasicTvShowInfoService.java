package com.example.spring.services;

import com.example.spring.exception.ResourceNotFoundException;
import com.example.spring.models.BasicTvShowInfo;
import com.example.spring.repositories.BasicTvShowInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    public BasicTvShowInfo getBasicTvShowInfoById (Integer idTvShow) throws ResourceNotFoundException {
        return basicTvShowInfoRepository.findById(idTvShow).orElseThrow(() -> new ResourceNotFoundException(
                "The tv show with the id : " + idTvShow + " was not found."));
    }

    // Will return a list of results of basic tv show information depending on the name of the show ( we look for matches in a LIKE pattern in our database)
    public List<BasicTvShowInfo> searchBasicTvShowInfo (String originalNameTvShow){
        return basicTvShowInfoRepository.findByOriginalNameStartsWith(PageRequest.of(0,5),originalNameTvShow).getContent();
    }
}
