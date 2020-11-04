package com.example.spring.services;

import com.example.spring.models.TvShowReminder;
import com.example.spring.repositories.TvShowReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TvShowReminderService {


    private final TvShowReminderRepository tvShowReminderRepository;

    @Autowired
    public TvShowReminderService(TvShowReminderRepository tvShowReminderRepository) {
        this.tvShowReminderRepository = tvShowReminderRepository;
    }

    public void addTvShowReminder(TvShowReminder tvShowReminder) {
        tvShowReminderRepository.save(tvShowReminder);
    }

    public Optional<TvShowReminder> getTvShowReminder(Integer idTvShowReminder) {
        return tvShowReminderRepository.findById(idTvShowReminder);
    }

    public List<TvShowReminder> getAllTvShowsReminder() {
        return tvShowReminderRepository.findAll();
    }

    public void deleteTvShowReminder(Integer idTvShowReminder) {
        tvShowReminderRepository.deleteById(idTvShowReminder);
    }
}
