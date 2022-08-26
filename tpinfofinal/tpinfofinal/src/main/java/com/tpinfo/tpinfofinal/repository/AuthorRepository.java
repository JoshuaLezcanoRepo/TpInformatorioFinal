package com.tpinfo.tpinfofinal.repository;

import com.tpinfo.tpinfofinal.entities.AuthorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
    Page<AuthorEntity> findAllByCreatedAtIsAfter(LocalDate date, Pageable pageable);
    Page<AuthorEntity> findByFullnameContaining(String word, Pageable page);
}