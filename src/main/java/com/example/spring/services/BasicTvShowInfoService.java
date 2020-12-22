package com.example.spring.services;

import com.example.spring.dto.BasicTvShowInfoFromApiDTO;
import com.example.spring.exception.ResourceNotFoundException;
import com.example.spring.models.BasicTvShowInfo;
import com.example.spring.repositories.BasicTvShowInfoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasicTvShowInfoService {

    private final Integer batch_size = 50;
    private final BasicTvShowInfoRepository basicTvShowInfoRepository;
    private final EntityManager entityManager;

    @Autowired
    public BasicTvShowInfoService(BasicTvShowInfoRepository basicTvShowInfoRepository, EntityManager entityManager) {
        this.basicTvShowInfoRepository = basicTvShowInfoRepository;
        this.entityManager = entityManager;
    }

    // 1 min y 44 seg / 2 min y 07 seg rewriteBatchedStatements=true
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

    @Transactional
    public void saveListOfBasicTvShowsTwo(List<BasicTvShowInfo> listBasicTvShowInfo) {
        entityManager.flush();
        for (int i = 0; i < listBasicTvShowInfo.size(); i++) {
            if (i > 0 && i % batch_size == 0) {
                entityManager.flush();
                entityManager.clear();
            }
            //BasicTvShowInfo basicTvShowInfo = listBasicTvShowInfo.get(i);
            entityManager.persist(listBasicTvShowInfo.get(i));
        }
        entityManager.flush();
        entityManager.clear();
    }

    @Transactional
    public void processTvShowFile(MultipartFile file) throws IOException {
        String jsonString = getJsonString(file);
        List<BasicTvShowInfoFromApiDTO> listBasicTvShowInfoFromApiDTO = getListBasicTvShowInfoFromApiDTO(jsonString);
        List<BasicTvShowInfo> listBasicTvShow = getListBasicTvShowInfo(listBasicTvShowInfoFromApiDTO);
        saveListOfBasicTvShowsTwo(listBasicTvShow);
    }

    public String getJsonString(MultipartFile file) throws IOException {
        // Load json file to string
        InputStream inputStream = file.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String jsonString =  reader.lines().collect(Collectors.joining());
        return jsonString;
    }

    public List<BasicTvShowInfoFromApiDTO> getListBasicTvShowInfoFromApiDTO (String jsonString) throws JsonProcessingException {
        // Convert json string to dto list
        ObjectMapper mapper = new ObjectMapper();
        List<BasicTvShowInfoFromApiDTO> basicTvShowInfoJsonList = mapper.readValue(jsonString, new TypeReference<List<BasicTvShowInfoFromApiDTO>>(){});
        System.out.println(basicTvShowInfoJsonList);
        return basicTvShowInfoJsonList;
    }

    public List<BasicTvShowInfo> getListBasicTvShowInfo(List<BasicTvShowInfoFromApiDTO> listBasicTvShowInfoFromApiDTO){
        // Convert list of dto to a list of entities
        List<BasicTvShowInfo> basicTvShowInfoList = new ArrayList<>();
        for (BasicTvShowInfoFromApiDTO tvShowDTO: listBasicTvShowInfoFromApiDTO) {
            BasicTvShowInfo tvShow = new BasicTvShowInfo();
            tvShow.setId(tvShowDTO.getId());
            tvShow.setOriginalName(tvShowDTO.getOriginalName());
            basicTvShowInfoList.add(tvShow);
        }
        return basicTvShowInfoList;
    }
}
