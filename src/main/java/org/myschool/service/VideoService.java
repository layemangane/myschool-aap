package org.myschool.service;

import lombok.AllArgsConstructor;
import org.myschool.dto.VideoReqDTO;
import org.myschool.entity.Video;
import org.myschool.exception.ResourceNotFoundException;
import org.myschool.repository.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;

    public List<Video> findAllVideos(Long chapitreId) {
        return videoRepository.findVideosByChapitreId(chapitreId);
    }

    public Video findVideoById(Long id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vidéo introuvable"));
    }

    public Video createVideo(Video video) {
        return videoRepository.save(video);
    }

    @Transactional
    public Video updateVideo(Long id, VideoReqDTO videoReqDTO) {
        Video videoFoud = findVideoById(id);
        videoFoud.setTitre(videoReqDTO.getTitre());
        videoFoud.setDureeMinutes(videoReqDTO.getDureeMinutes());
        return videoRepository.save(videoFoud);
    }

    @Transactional
    public void deleteVideo(Long id) {
        Video video = findVideoById(id);
        videoRepository.delete(video);
    }

}
