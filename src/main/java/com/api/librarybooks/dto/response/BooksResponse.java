package com.api.librarybooks.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class BooksResponse {
    private ArrayList<BookResponse> items;
}
