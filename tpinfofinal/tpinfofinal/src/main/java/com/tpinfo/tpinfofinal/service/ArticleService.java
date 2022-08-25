package com.tpinfo.tpinfofinal.service;

import com.tpinfo.tpinfofinal.controller.util.PageResponse;
import com.tpinfo.tpinfofinal.dto.ArticleDTO;
import com.tpinfo.tpinfofinal.exception.ResourceNotFoundException;

public interface ArticleService {

    ArticleDTO createArticle(ArticleDTO articleDTO) throws ResourceNotFoundException;

    ArticleDTO updateArticle(Long id, ArticleDTO articleDTO) throws ResourceNotFoundException;

    void deleteArticle(Long id) throws ResourceNotFoundException;

    PageResponse<ArticleDTO> findByPage(int page);

    PageResponse<ArticleDTO> findByTitleContainingAndDescriptionContainingAndAuthorByContentContainingAndFullnameContaining(String word, int page);

}