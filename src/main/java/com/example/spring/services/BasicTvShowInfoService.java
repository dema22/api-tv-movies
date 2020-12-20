package com.example.spring.services;

import com.example.spring.dto.BasicTvShowInfoFromApiDTO;
import com.example.spring.exception.ResourceNotFoundException;
import com.example.spring.models.BasicTvShowInfo;
import com.example.spring.repositories.BasicTvShowInfoRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public void loadTvShowsToSystem(MultipartFile file) throws IOException {
        List<BasicTvShowInfo> listBasicTvShowInfo = new ArrayList<>();
        BasicTvShowInfo tvShow = new BasicTvShowInfo();

        InputStream inputStream = file.getInputStream();
        Stream<String> stream = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines();//.forEach(this::readJsonFile);

    }

    private void readJsonFile(String s) {
        System.out.println(s);
    }

    public void loadTvShowsToSystemTwo(MultipartFile file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // Load json file to string
        InputStream inputStream = file.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String jsonString =  reader.lines().collect(Collectors.joining());
        System.out.println(jsonString);

        // Convert string to json array
        List<BasicTvShowInfoFromApiDTO> basicTvShowInfoJsonList = mapper.readValue(jsonString, new TypeReference<List<BasicTvShowInfoFromApiDTO>>(){});
        System.out.println(basicTvShowInfoJsonList);

    }

}
