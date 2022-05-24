package com.chatbot.chatbot.service;

import com.chatbot.chatbot.entity.Track;
import com.chatbot.chatbot.repository.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrackServiceImpl implements TrackService {

    final TrackRepository trackRepository;

    @Override
    public Track getStartTrack() {
        return trackRepository.getStartTrack();
    }

    @Override
    public Optional<Track> getTrackById(Long trackId) {
        return trackRepository.findById(trackId);
    }

    @Override
    public Optional<List<Track>> getAllTracks() {
        return Optional.of(trackRepository.findAll());
    }
}
