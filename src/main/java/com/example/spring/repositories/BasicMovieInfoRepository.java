package com.example.spring.repositories;

import com.example.spring.models.BasicMovieInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicMovieInfoRepository extends JpaRepository<BasicMovieInfo,Integer> {
}
