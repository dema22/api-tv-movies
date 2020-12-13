package com.example.spring.repositories;

import com.example.spring.models.TvShowReminder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TvShowReminderRepository extends JpaRepository<TvShowReminder, Integer> {
    Page<TvShowReminder> findByUser_IdUser(Pageable pageable, Integer idUser);
    List<TvShowReminder> findByUser_IdUser(Integer idUser);

    @Query(value = "SELECT * FROM tv_show_reminder WHERE id_user = ?1 and id_basic_tv_show_info = ?2", nativeQuery = true)
    Optional<TvShowReminder> findByUserIdAndTvShowId(Integer idUser, Integer idBasicTvShowInfo);

    @Query(value = "SELECT * FROM tv_show_reminder WHERE id_user = ?1 and id_tv_show_created_by_user = ?2", nativeQuery = true)
    Optional<TvShowReminder> findByUserIdAndTvShowCreatedByUserId(Integer idUser, Integer idTvShowCreatedByUser);

    @Query(value = "SELECT * FROM tv_show_reminder WHERE id_user = ?1 and id_tv_show_reminder = ?2", nativeQuery = true)
    Optional<TvShowReminder> findByIdTvShowReminderAndUserId(Integer idUser, Integer idTvShowReminder);
}
