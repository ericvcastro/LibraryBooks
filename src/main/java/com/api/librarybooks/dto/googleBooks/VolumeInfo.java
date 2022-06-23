package com.api.librarybooks.dto.googleBooks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class VolumeInfo {
    private String title;
    private ArrayList<String> authors;
}
