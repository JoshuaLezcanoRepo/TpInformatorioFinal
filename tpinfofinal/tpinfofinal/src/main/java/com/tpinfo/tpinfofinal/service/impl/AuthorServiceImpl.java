package com.tpinfo.tpinfofinal.service.impl;

import com.tpinfo.tpinfofinal.controller.util.PageResponse;
import com.tpinfo.tpinfofinal.dto.AuthorDTO;
import com.tpinfo.tpinfofinal.entities.AuthorEntity;
import com.tpinfo.tpinfofinal.exception.ResourceNotFoundException;
import com.tpinfo.tpinfofinal.mapper.AuthorMapper;
import com.tpinfo.tpinfofinal.repository.AuthorRepository;
import com.tpinfo.tpinfofinal.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDate;


import static java.util.stream.Collectors.toList;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorMapper authorMapper;
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorMapper authorMapper, AuthorRepository authorRepository){
        this.authorMapper = authorMapper;
        this.authorRepository = authorRepository;
    }
    @Override
    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        AuthorEntity authorEntity = authorMapper.toEntity(authorDTO);
        AuthorEntity authorSave =  authorRepository.save(authorEntity);

        return authorMapper.toDTO(authorSave);
    }

    @Override
    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) throws ResourceNotFoundException {
        AuthorEntity authorEntity = authorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found id: " + id)
        );
        AuthorEntity authorSet = authorMapper.toSetEntity(authorEntity, authorDTO);
        AuthorEntity authorSave = authorRepository.save(authorSet);

        return authorMapper.toDTO(authorSave);
    }

    @Override
    public void deleteAuthor(Long id) throws ResourceNotFoundException {
        authorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found id: " + id)
        );
        authorRepository.deleteById(id);
    }

    @Override
    public PageResponse<AuthorDTO> findByPage(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<AuthorEntity> pageResultAuthorEntity = authorRepository.findAll(pageable);
        if(page >= pageResultAuthorEntity.getTotalPages()){
            throw new IllegalArgumentException("Incorrect index");
        }

        String nextPage = pageResultAuthorEntity.isLast() ? "" : "/author?page=" + (page + 1);
        String previousPage = pageResultAuthorEntity.isFirst() ? "" : "/author?page=" + (page - 1);

        return pageAuthor(pageResultAuthorEntity, page, nextPage, previousPage);
    }

    @Override
    public PageResponse<AuthorDTO> findByCreatedAtIsAfter(String date, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<AuthorEntity> pageResultAuthorEntity = authorRepository.findAllByCreatedAtIsAfter(LocalDate.parse(date), pageable);

        if(page >= pageResultAuthorEntity.getTotalPages()){
            throw new IllegalArgumentException("Incorrect index");
        }

        String nextPage = pageResultAuthorEntity.isLast() ? "" : "/author/date?page=" + (page + 1) + "&date=" + date;
        String previousPage = pageResultAuthorEntity.isFirst() ? "" : "/author/date?page=" + (page - 1) + "&date=" + date;

        return pageAuthor(pageResultAuthorEntity, page, nextPage, previousPage );
    }

    @Override
    public PageResponse<AuthorDTO> findByFullnameContaining(String word, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<AuthorEntity> pageResultAuthorEntity = authorRepository.findByFullnameContaining(word, pageable);

        if(page >= pageResultAuthorEntity.getTotalPages()){
            throw  new IllegalArgumentException("Incorrect index");
        }

        String nextPage = pageResultAuthorEntity.isLast() ? "" : "/author/word?page=" + (page + 1) + "&word=" + word;
        String previousPage = pageResultAuthorEntity.isFirst() ? "" : "/author/word?page=" + (page - 1) + "&word=" + word;

        return pageAuthor(pageResultAuthorEntity, page, nextPage, previousPage );
    }

    public PageResponse<AuthorDTO> pageAuthor(Page<AuthorEntity> pageResultAuthorEntity, int page, String nextPage, String previousPage){

        PageResponse response = PageResponse.builder()
                .content(pageResultAuthorEntity
                        .getContent()
                        .stream()
                        .map(authorMapper::toDTO)
                        .collect(toList()))
                .pageNumber(page +1)
                .totalPage(pageResultAuthorEntity.getTotalPages())
                .totalElements(pageResultAuthorEntity.getTotalElements())
                .nextPage(nextPage)
                .previousPage(previousPage)
                .build();
        return response;
    }
}