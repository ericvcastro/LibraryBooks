package com.api.librarybooks.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class BookResponse {
    private String id;
    private String title;
    private ArrayList<String> authors;
}
