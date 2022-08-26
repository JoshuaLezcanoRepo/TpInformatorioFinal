package com.tpinfo.tpinfofinal.service;

import com.tpinfo.tpinfofinal.controller.util.PageResponse;
import com.tpinfo.tpinfofinal.dto.AuthorDTO;
import com.tpinfo.tpinfofinal.exception.ResourceNotFoundException;

public interface AuthorService {
    AuthorDTO createAuthor(AuthorDTO authorDTO);
    AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) throws ResourceNotFoundException;
    void deleteAuthor(Long id) throws ResourceNotFoundException;
    PageResponse<AuthorDTO> findByPage(int page);
    PageResponse<AuthorDTO> findByCreatedAtIsAfter(String date, int page);
    PageResponse<AuthorDTO> findByFullnameContaining(String word, int page);
}