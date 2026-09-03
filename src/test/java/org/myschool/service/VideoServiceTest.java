package org.myschool.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.myschool.dto.VideoReqDTO;
import org.myschool.entity.Video;
import org.myschool.exception.ResourceNotFoundException;
import org.myschool.repository.VideoRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VideoServiceTest {

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private VideoService videoService;

    @Test
    void updateVideoUpdatesTitleAndDuration() {
        Video video = new Video("Ancien titre", 5, null);
        VideoReqDTO request = new VideoReqDTO();
        request.setTitre("Nouveau titre");
        request.setDureeMinutes(12);
        when(videoRepository.findById(1L)).thenReturn(Optional.of(video));
        when(videoRepository.save(video)).thenReturn(video);

        Video result = videoService.updateVideo(1L, request);

        assertEquals("Nouveau titre", result.getTitre());
        assertEquals(12, result.getDureeMinutes());
        verify(videoRepository).save(video);
    }

    @Test
    void findVideoByIdThrowsWhenVideoDoesNotExist() {
        when(videoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> videoService.findVideoById(99L));
    }

    @Test
    void deleteVideoDeletesExistingVideo() {
        Video video = new Video();
        when(videoRepository.findById(1L)).thenReturn(Optional.of(video));

        videoService.deleteVideo(1L);

        verify(videoRepository).delete(video);
    }
}
