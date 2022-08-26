package com.tpinfo.tpinfofinal.controller;

import com.tpinfo.tpinfofinal.dto.AuthorDTO;
import com.tpinfo.tpinfofinal.exception.ResourceNotFoundException;
import com.tpinfo.tpinfofinal.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
@RestController
@RequestMapping("/author")

public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<?> createAuthor(@Valid @RequestBody AuthorDTO authorDTO){
        return new ResponseEntity<>(authorService.createAuthor(authorDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable Long id, @Valid @RequestBody AuthorDTO authorDTO) throws ResourceNotFoundException {
        return new ResponseEntity<>(authorService.updateAuthor(id, authorDTO), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id) throws ResourceNotFoundException {
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> findByPage(@RequestParam int page){
        return new ResponseEntity<>(authorService.findByPage(page), HttpStatus.OK);
    }

    @GetMapping("/date")
    public ResponseEntity<?> findByCreationDateIsAfter(@RequestParam String date, @RequestParam int page){
        return new ResponseEntity<>(authorService.findByCreatedAtIsAfter(date, page), HttpStatus.OK);
    }

    @GetMapping("/word")
    public ResponseEntity<?> findByFullnameContaining(@RequestParam String word, @RequestParam int page){
        return new ResponseEntity<>(authorService.findByFullnameContaining(word, page), HttpStatus.OK);
    }
}