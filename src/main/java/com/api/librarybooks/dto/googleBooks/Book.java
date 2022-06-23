package com.api.librarybooks.dto.googleBooks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    private String id;
    private VolumeInfo volumeInfo;

}
