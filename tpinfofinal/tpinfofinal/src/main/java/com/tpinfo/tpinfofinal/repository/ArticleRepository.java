package com.tpinfo.tpinfofinal.repository;

import com.tpinfo.tpinfofinal.entities.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
    Page<ArticleEntity> findByTitleContainingOrDescriptionContainingOrContentContainingOrAuthorFullnameContaining(
            String word, String word2, String word3, String word4, Pageable pageable);
}