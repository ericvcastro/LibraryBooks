package com.api.librarybooks.controllers;

import com.api.librarybooks.dto.response.BookFavouriteBookResponse;
import com.api.librarybooks.dto.response.BooksResponse;
import com.api.librarybooks.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
public class LibraryController {
    @Autowired(required = true)
    private LibraryService service;

    @GetMapping("/books")
    public BooksResponse searchBooks(@RequestParam String q, @RequestParam String startIndex, @RequestParam String maxResults) throws IOException, InterruptedException {
        return service.searchBooks(q, startIndex, maxResults);
    }

    @PostMapping("/books")
    public BookFavouriteBookResponse searchBooks(@RequestParam String id) throws IOException, InterruptedException {
        return service.postFavouriteBook(id);
    }
}
