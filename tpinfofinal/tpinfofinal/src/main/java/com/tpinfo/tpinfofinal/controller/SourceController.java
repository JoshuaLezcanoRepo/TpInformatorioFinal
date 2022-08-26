package com.tpinfo.tpinfofinal.controller;

import com.tpinfo.tpinfofinal.dto.SourceDTO;
import com.tpinfo.tpinfofinal.exception.ResourceNotFoundException;
import com.tpinfo.tpinfofinal.service.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
@RestController
@RequestMapping("/source")

public class SourceController {
    @Autowired
    private SourceService sourceService;

    @PostMapping
    public ResponseEntity<?> createSource(@Valid @RequestBody SourceDTO sourceDTO){
        return new ResponseEntity<>(sourceService.createSource(sourceDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSource(@PathVariable Long id, @RequestBody SourceDTO sourceDTO) throws ResourceNotFoundException {
        return new ResponseEntity<>(sourceService.updateSource(id, sourceDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSource(@PathVariable Long id) throws ResourceNotFoundException {
        sourceService.deleteSource(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> findByPage(@RequestParam int page){
        return new ResponseEntity<>(sourceService.findByPage(page), HttpStatus.OK);
    }

    @GetMapping("/word")
    public ResponseEntity<?> findByNameContaining(@RequestParam String word, @RequestParam int page){
        return new ResponseEntity<>(sourceService.findByNameContaining(word, page), HttpStatus.OK);
    }
}