package com.chatbot.chatbot.repository;

import com.chatbot.chatbot.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {

    @Query("select t from Track t where t.start=true")
    Track getStartTrack();
}
