package com.tpinfo.tpinfofinal.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArticleDTO {

    private Long id;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String content;
    private SourceDTO source;
    private AuthorDTO author;
}