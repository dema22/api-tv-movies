package com.example.spring.repositories;

import com.example.spring.models.UserTvShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTvShowRepository extends JpaRepository<UserTvShow,Integer> {
    @Query(value = "SELECT * FROM tv_show_created_by_user WHERE id_user = ?1 and id_tv_show_created_by_user = ?2", nativeQuery = true)
    Optional<UserTvShow> findByIdTvShowAndUserId(Integer idUser, Integer idTvShow);
    Optional<UserTvShow> findByNameTvShow(String nameTvShow);
    List<UserTvShow> findByUser_IdUser(Integer idUser);
}
