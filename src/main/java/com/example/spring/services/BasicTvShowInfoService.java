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
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Done
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

    public BasicTvShowInfo getBasicTvShowInfoById (Integer idTvShow) throws ResourceNotFoundException {
        return basicTvShowInfoRepository.findById(idTvShow).orElseThrow(() -> new ResourceNotFoundException(
                "The tv show with the id : " + idTvShow + " was not found."));
    }

    // Will return a list of results of basic tv show information that starts with the name of the show.
    // We will return the first 5 results that match the name.
    public List<BasicTvShowInfo> searchBasicTvShowInfo (String originalNameTvShow){
        return basicTvShowInfoRepository.findByOriginalNameStartsWith(PageRequest.of(0,5),originalNameTvShow).getContent();
    }

    // Using Entity Manager we are going to flush memory and we are going to insert in batches of 50 so we dont make so much roundtrips to the DB.
    @Transactional
    public void saveListOfBasicTvShows(List<BasicTvShowInfo> listBasicTvShowInfo) {
        entityManager.flush();
        for (int i = 0; i < listBasicTvShowInfo.size(); i++) {
            if (i > 0 && i % batch_size == 0) {
                entityManager.flush();
                entityManager.clear();
            }
            entityManager.persist(listBasicTvShowInfo.get(i));
        }
        entityManager.flush();
        entityManager.clear();
    }

    // This method will receive the json file, convert it to a string in json format, then we are going to
    // map this json string in a list of DTO. Finally, we are going to map this list of DTO to a list of Entities,
    //, so we can save it in our DB.
    @Transactional
    public void processTvShowFile(MultipartFile file) throws IOException {
        String jsonString = getJsonString(file);
        List<BasicTvShowInfoFromApiDTO> listBasicTvShowInfoFromApiDTO = getListBasicTvShowInfoFromApiDTO(jsonString);
        List<BasicTvShowInfo> listBasicTvShow = getListBasicTvShowInfo(listBasicTvShowInfoFromApiDTO);
        saveListOfBasicTvShows(listBasicTvShow);
    }

    public String getJsonString(MultipartFile file) throws IOException {
        // Load json file to string
        InputStream inputStream = file.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String jsonString =  reader.lines().collect(Collectors.joining());
        return jsonString;
    }

    public List<BasicTvShowInfoFromApiDTO> getListBasicTvShowInfoFromApiDTO (String jsonString) throws JsonProcessingException {
        // Convert json string to a dto list.
        ObjectMapper mapper = new ObjectMapper();
        List<BasicTvShowInfoFromApiDTO> basicTvShowInfoJsonList = mapper.readValue(jsonString, new TypeReference<List<BasicTvShowInfoFromApiDTO>>(){});
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
