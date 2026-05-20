package com.iambiker.webservice.model.dto.personal;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDTO {
    private long id;

    private String authorId;

    private String title;

    private String content;

    private String category;

    private List<String> tags;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public void update(PostDTO other) {
        setAuthorId(other.getAuthorId());
        setCategory(other.getCategory());
        setTags(other.getTags());
        setContent(other.getContent());
        setTitle(other.getTitle());
        setUpdatedAt(LocalDateTime.now());
    }
}
