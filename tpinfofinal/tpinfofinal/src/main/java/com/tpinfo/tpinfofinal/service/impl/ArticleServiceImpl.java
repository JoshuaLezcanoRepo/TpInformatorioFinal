package com.tpinfo.tpinfofinal.service.impl;

import com.tpinfo.tpinfofinal.controller.util.PageResponse;
import com.tpinfo.tpinfofinal.dto.ArticleDTO;
import com.tpinfo.tpinfofinal.entities.ArticleEntity;
import com.tpinfo.tpinfofinal.exception.ResourceNotFoundException;
import com.tpinfo.tpinfofinal.mapper.ArticleMapper;
import com.tpinfo.tpinfofinal.repository.ArticleRepository;
import com.tpinfo.tpinfofinal.repository.AuthorRepository;
import com.tpinfo.tpinfofinal.repository.SourceRepository;
import com.tpinfo.tpinfofinal.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import static java.util.stream.Collectors.toList;
@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleMapper articleMapper;
    private final ArticleRepository articleRepository;
    private final SourceRepository sourceRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public ArticleServiceImpl(ArticleMapper articleMapper,
                              ArticleRepository articleRepository,
                              SourceRepository sourceRepository,
                              AuthorRepository authorRepository){
        this.articleMapper = articleMapper;
        this.articleRepository = articleRepository;
        this.sourceRepository = sourceRepository;
        this.authorRepository = authorRepository;
    }
    @Override
    public ArticleDTO createArticle(ArticleDTO articleDTO) throws ResourceNotFoundException {
        sourceRepository.findById(articleDTO.getSource().getId()).orElseThrow(
                () -> new ResourceNotFoundException("Not found source id: " + articleDTO.getSource().getId())
        );
        ArticleEntity articleEntity = articleMapper.toEntity(articleDTO);
        ArticleEntity articleSave = articleRepository.save(articleEntity);
        return articleMapper.toDTO(articleSave);
    }
    @Override
    public ArticleDTO updateArticle(Long id, ArticleDTO articleDTO) throws ResourceNotFoundException {
        ArticleEntity articleEntity = articleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found source id: " + id)
        );
        ArticleEntity entity = articleMapper.setEntity(articleEntity, articleDTO);
        ArticleEntity articleSave = articleRepository.save(entity);
        return articleMapper.toDTO(articleSave);
    }
    @Override
    public void deleteArticle(Long id) throws ResourceNotFoundException {
        articleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found source id: " + id)
        );
        articleRepository.deleteById(id);
    }
    @Override
    public PageResponse<ArticleDTO> findByPage(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<ArticleEntity> pageResultArticleEntity = articleRepository.findAll(pageable);
        if (page >= pageResultArticleEntity.getTotalPages()) {
            throw new IllegalArgumentException("Incorrect index");
        }
        String nextPage = pageResultArticleEntity.isLast() ? "" : "/article?page=" + (page + 1);
        String previousPage = pageResultArticleEntity.isFirst() ? "" : "/article?page=" + (page - 1);
        return pageArticle(pageResultArticleEntity, page, nextPage, previousPage);
    }
    @Override
    public PageResponse<ArticleDTO> findByTitleContainingAndDescriptionContainingAndAuthorByContentContainingAndFullnameContaining(String word, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<ArticleEntity> pageResultArticleEntity =
                articleRepository.findByTitleContainingOrDescriptionContainingOrContentContainingOrAuthorFullnameContaining(word, word, word, word, pageable);

        if (page >= pageResultArticleEntity.getTotalPages()) {
            throw new IllegalArgumentException("Incorrect index");
        }
        String nextPage = pageResultArticleEntity.isLast() ? "" : "/article/word?page=" + (page + 1) + "&word=" + word;
        String previousPage = pageResultArticleEntity.isFirst() ? "" : "/article/word?page=" + (page - 1) + "&word=" + word;
        return pageArticle(pageResultArticleEntity, page, nextPage, previousPage);
    }

    public PageResponse<ArticleDTO> pageArticle(Page<ArticleEntity> pageResultArticleEntity, int page, String nextPage, String previousPage) {
        PageResponse response = PageResponse.builder()
                .content(pageResultArticleEntity
                        .getContent()
                        .stream()
                        .map(articleMapper::toDTO)
                        .collect(toList()))
                .pageNumber(page + 1)
                .totalPage(pageResultArticleEntity.getTotalPages())
                .totalElements(pageResultArticleEntity.getTotalElements())
                .nextPage(nextPage)
                .previousPage(previousPage)
                .build();
        return response;
    }
}