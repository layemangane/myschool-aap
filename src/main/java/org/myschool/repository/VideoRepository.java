package org.myschool.repository;

import org.myschool.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    Optional<Video> findByTitre(String titre);

    List<Video> findByDureeMinutesGreaterThan(int dureeMinutes);

    List<Video> findByDureeMinutesLessThan(int dureeMinutes);

    List<Video> findVideosByChapitreId(Long chapitreId);
}
