package org.myschool.dto;

import lombok.Getter;
import lombok.Setter;
import org.myschool.entity.Video;

@Setter
@Getter
public class VideoResDTO {
    private Long id;
    private String titre;
    private int dureeMinutes;
    private Long chapitreId;

    public VideoResDTO(Video video) {
        this.id = video.getId();
        this.titre = video.getTitre();
        this.dureeMinutes = video.getDureeMinutes();
        this.chapitreId = video.getChapitre().getId();
    }
}
