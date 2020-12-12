package com.example.spring.repositories;

import com.example.spring.models.TvShowReminder;
import com.example.spring.models.UserTvShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTvShowRepository extends JpaRepository<UserTvShow,Integer> {
    Optional<UserTvShow> findByNameTvShow(String nameTvShow);
    List<UserTvShow> findByUser_IdUser(Integer idUser);
}
