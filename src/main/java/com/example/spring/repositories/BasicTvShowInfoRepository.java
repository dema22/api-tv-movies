package com.example.spring.repositories;

import com.example.spring.models.BasicTvShowInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicTvShowInfoRepository extends JpaRepository<BasicTvShowInfo,Integer> {
}
