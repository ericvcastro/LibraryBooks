package com.api.librarybooks.dto.googleBooks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OAuthTokenGoogle  {
    private String access_token;
    private String token_type;
}
