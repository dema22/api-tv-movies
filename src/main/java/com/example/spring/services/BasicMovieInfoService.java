package com.example.spring.services;

import com.example.spring.dto.BasicMovieInfoDTO;
import com.example.spring.models.BasicMovieInfo;
import com.example.spring.repositories.BasicMovieInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BasicMovieInfoService {
    private BasicMovieInfoRepository basicMovieInfoRepository;

    @Autowired
    public BasicMovieInfoService(BasicMovieInfoRepository basicMovieInfoRepository) {
        this.basicMovieInfoRepository = basicMovieInfoRepository;
    }

    public void saveAllMoviesFromAPIFile(List<BasicMovieInfoDTO> listBasicMovieInfoDTO) {
        try {
            List<BasicMovieInfo> basicMovieInfoList = new ArrayList<BasicMovieInfo>();

            for(BasicMovieInfoDTO basicMovieInfoDTO: listBasicMovieInfoDTO){
                BasicMovieInfo basicMovieInfo = new BasicMovieInfo();

                basicMovieInfo.setId(basicMovieInfoDTO.getId());
                basicMovieInfo.setOriginal_title(basicMovieInfoDTO.getOriginal_title());
                basicMovieInfoList.add(basicMovieInfo);
            }

            basicMovieInfoRepository.saveAll(basicMovieInfoList);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}
