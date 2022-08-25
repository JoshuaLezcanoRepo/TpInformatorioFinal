package com.tpinfo.tpinfofinal.mapper;

import com.tpinfo.tpinfofinal.dto.ArticleDTO;
import com.tpinfo.tpinfofinal.dto.AuthorDTO;
import com.tpinfo.tpinfofinal.dto.SourceDTO;
import com.tpinfo.tpinfofinal.entities.ArticleEntity;
import com.tpinfo.tpinfofinal.entities.AuthorEntity;
import com.tpinfo.tpinfofinal.entities.SourceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class ArticleMapper {

    private final SourceMapper sourceMapper;
    private final AuthorMapper authorMapper;

    @Autowired
    public ArticleMapper(SourceMapper sourceMapper, AuthorMapper authorMapper) {
        this.sourceMapper = sourceMapper;
        this.authorMapper = authorMapper;
    }

    public ArticleEntity toEntity(ArticleDTO articleDTO) {
        return ArticleEntity.builder()
                .title(articleDTO.getTitle())
                .description(articleDTO.getDescription())
                .url(articleDTO.getUrl())
                .urlToImage(articleDTO.getUrlToImage())
                .publishedAt(LocalDate.parse(articleDTO.getPublishedAt()))
                .content(articleDTO.getContent())
                .source(getSourceEntity(articleDTO))
                .author(getAuthorEntity(articleDTO))
                .build();
    }

    public SourceEntity getSourceEntity(ArticleDTO articleDTO) {
        return sourceMapper.toEntity(articleDTO.getSource());
    }

    public AuthorEntity getAuthorEntity(ArticleDTO articleDTO) {
        return authorMapper.toEntity(articleDTO.getAuthor());
    }

    public ArticleDTO toDTO(ArticleEntity articleEntity) {
        return ArticleDTO.builder()
                .id(articleEntity.getId())
                .title(articleEntity.getTitle())
                .description(articleEntity.getDescription())
                .url(articleEntity.getUrl())
                .urlToImage(articleEntity.getUrlToImage())
                .publishedAt(articleEntity.getPublishedAt().toString())
                .content(articleEntity.getContent())
                .source(getSourceDTO(articleEntity))
                .author(getAuthorDTO(articleEntity))
                .build();
    }

    public SourceDTO getSourceDTO(ArticleEntity articleEntity){
        return sourceMapper.toDTO(articleEntity.getSource());
    }

    public AuthorDTO getAuthorDTO(ArticleEntity articleEntity){
        return authorMapper.toDTO(articleEntity.getAuthor());
    }

    public List<ArticleDTO> toListDTO(List<ArticleEntity> listArticleEntity) {
        return listArticleEntity.stream()
                .map(this::toDTO)
                .collect(toList());
    }

    public ArticleEntity setEntity(ArticleEntity articleEntity, ArticleDTO articleDTO){
        articleEntity.setId(articleDTO.getId());
        articleEntity.setTitle(articleDTO.getTitle());
        articleEntity.setDescription(articleDTO.getDescription());
        articleEntity.setUrl(articleDTO.getUrl());
        articleEntity.setUrlToImage(articleDTO.getUrlToImage());
        articleEntity.setPublishedAt(LocalDate.parse(articleDTO.getPublishedAt()));
        articleEntity.setContent(articleDTO.getContent());
        return  articleEntity;
    }
}