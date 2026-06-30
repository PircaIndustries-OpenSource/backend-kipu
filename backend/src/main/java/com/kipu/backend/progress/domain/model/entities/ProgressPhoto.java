package com.kipu.backend.progress.domain.model.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgressPhoto {
    private Long id;
    private String projectId;
    private String title;
    private String url;
    private String uploadDate;

    public ProgressPhoto() {
    }

    public ProgressPhoto(String projectId, String title, String url, String uploadDate) {
        this.projectId = projectId;
        this.title = title;
        this.url = url;
        this.uploadDate = uploadDate;
    }
}
