package com.example.spring.repositories;

import com.example.spring.models.TvShowReminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TvShowReminderRepository extends JpaRepository<TvShowReminder, Integer> {
}
