package com.chatbot.chatbot.service;

import com.chatbot.chatbot.entity.Track;

import java.util.List;
import java.util.Optional;


public interface TrackService {

    Track getStartTrack();

    Optional<Track> getTrackById(Long trackId);

    Optional<List<Track>> getAllTracks();
}
