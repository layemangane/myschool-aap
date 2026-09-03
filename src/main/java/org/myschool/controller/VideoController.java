package org.myschool.controller;

import lombok.AllArgsConstructor;
import jakarta.validation.Valid;
import org.myschool.dto.VideoReqDTO;
import org.myschool.dto.VideoResDTO;
import org.myschool.entity.Chapitre;
import org.myschool.entity.Video;
import org.myschool.service.ChapitreService;
import org.myschool.service.VideoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/api")
public class VideoController {

    private final VideoService videoService;
    private final ChapitreService chapitreService;

    @PostMapping("/chapitres/{chapitreId}/videos")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<VideoResDTO> createVideo(@PathVariable Long chapitreId, @Valid @RequestBody VideoReqDTO dto) {
        Chapitre chapitre = chapitreService.findChapitreById(chapitreId);
        Video video = videoService.createVideo(new Video(dto.getTitre(), dto.getDureeMinutes(), chapitre));
        return ResponseEntity.status(HttpStatus.CREATED).body(new VideoResDTO(video));
    }

    @GetMapping("/chapitres/{chapitreId}/videos")
    public ResponseEntity<List<VideoResDTO>> getAllVideos(@PathVariable Long chapitreId) {
        List<VideoResDTO> videos = videoService.findAllVideos(chapitreId).stream()
                .map(VideoResDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(videos);
    }

    @GetMapping("/videos/{id}")
    public ResponseEntity<VideoResDTO> getVideoById(@PathVariable Long id) {
        Video video = videoService.findVideoById(id);
        return ResponseEntity.ok(new VideoResDTO(video));
    }

    @PutMapping("/videos/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<VideoResDTO> updateVideo(@PathVariable Long id, @Valid @RequestBody VideoReqDTO dto) {
        Video video = videoService.updateVideo(id, dto);
        return ResponseEntity.ok(new VideoResDTO(video));
    }

    @DeleteMapping("/videos/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        videoService.deleteVideo(id);
        return ResponseEntity.noContent().build();
    }
}
