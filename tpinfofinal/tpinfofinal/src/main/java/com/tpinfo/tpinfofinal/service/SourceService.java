package com.tpinfo.tpinfofinal.service;

import com.tpinfo.tpinfofinal.controller.util.PageResponse;
import com.tpinfo.tpinfofinal.dto.SourceDTO;
import com.tpinfo.tpinfofinal.exception.ResourceNotFoundException;

import java.util.List;

public interface SourceService {

    SourceDTO createSource(SourceDTO sourceDTO);

    SourceDTO updateSource(Long id, SourceDTO sourceDTO) throws ResourceNotFoundException;

    void deleteSource(Long id) throws ResourceNotFoundException;

    PageResponse<SourceDTO> findByPage(int page);

    PageResponse<SourceDTO> findByNameContaining(String word, int page);
}