package com.tpinfo.tpinfofinal.service.impl;

import com.tpinfo.tpinfofinal.controller.util.PageResponse;
import com.tpinfo.tpinfofinal.dto.SourceDTO;
import com.tpinfo.tpinfofinal.entities.SourceEntity;
import com.tpinfo.tpinfofinal.exception.ResourceNotFoundException;
import com.tpinfo.tpinfofinal.mapper.SourceMapper;
import com.tpinfo.tpinfofinal.repository.SourceRepository;
import com.tpinfo.tpinfofinal.service.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import static java.util.stream.Collectors.toList;

@Service
public class SourceServiceImpl implements SourceService {
    @Autowired
    private SourceRepository sourceRepository;
    @Autowired
    private SourceMapper sourceMapper;
    @Override
    public SourceDTO createSource(SourceDTO sourceDTO) {
        SourceEntity sourceEntity = sourceMapper.toEntity(sourceDTO);
        SourceEntity sourceSave = sourceRepository.save(sourceEntity);
        SourceDTO sourceDTO1 = sourceMapper.toDTO(sourceSave);
        return sourceDTO1;
    }
    @Override
    public SourceDTO updateSource(Long id, SourceDTO sourceDTO) throws ResourceNotFoundException {
        SourceEntity source = sourceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found id: " + id)
        );
        SourceEntity sourceEntity = sourceMapper.toSetEntity(source, sourceDTO);
        SourceEntity sourceSave = sourceRepository.save(sourceEntity);
        SourceDTO sourceDTO1 = sourceMapper.toDTO(sourceSave);
        return sourceDTO1;
    }
    @Override
    public void deleteSource(Long id) throws ResourceNotFoundException {
        SourceEntity sourceEntity = sourceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found id: " + id)
        );
        sourceRepository.deleteById(id);
    }
    @Override
    public PageResponse<SourceDTO> findByPage(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<SourceEntity> pageResultSourceEntity = sourceRepository.findAll(pageable);
        if(page >= pageResultSourceEntity.getTotalPages()){
            throw new IllegalArgumentException("Incorrect index");
        }
        String nextPage = pageResultSourceEntity.isLast() ? "" : "/source?page=" + (page + 1);
        String previousPage = pageResultSourceEntity.isFirst() ? "" : "/source?page=" + (page - 1);
        return pageSource(pageResultSourceEntity, page, nextPage, previousPage );
    }
    @Override
    public PageResponse<SourceDTO> findByNameContaining(String word, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<SourceEntity> pageResultSourceEntity = sourceRepository.findByNameContaining(word, pageable);

        if(page >= pageResultSourceEntity.getTotalPages()){
            throw  new IllegalArgumentException("Incorrect index");
        }
        String nextPage = pageResultSourceEntity.isLast() ? "" : "/source/word?page=" + (page + 1) + "&word=" + word;
        String previousPage = pageResultSourceEntity.isFirst() ? "" : "/source/word?page=" + (page - 1) + "&word=" + word;
        return pageSource(pageResultSourceEntity, page, nextPage, previousPage );
    }
    public PageResponse<SourceDTO> pageSource(Page<SourceEntity> pageResultSourceEntity, int page, String nextPage, String previousPage){
        PageResponse response = PageResponse.builder()
                .content(pageResultSourceEntity
                        .getContent()
                        .stream()
                        .map(sourceMapper::toDTO)
                        .collect(toList()))
                .pageNumber(page +1)
                .totalPage(pageResultSourceEntity.getTotalPages())
                .totalElements(pageResultSourceEntity.getTotalElements())
                .nextPage(nextPage)
                .previousPage(previousPage)
                .build();
        return response;
    }
}