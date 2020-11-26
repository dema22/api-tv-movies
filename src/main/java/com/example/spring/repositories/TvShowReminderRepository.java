package com.example.spring.repositories;

import com.example.spring.models.TvShowReminder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TvShowReminderRepository extends JpaRepository<TvShowReminder, Integer> {
    Page<TvShowReminder> findByUser_IdUser(Pageable pageable, Integer idUser);
    List<TvShowReminder> findByUser_IdUser(Integer idUser);
}
