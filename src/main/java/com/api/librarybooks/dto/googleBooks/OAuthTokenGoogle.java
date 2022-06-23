package com.api.librarybooks.dto.googleBooks;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class OAuthTokenGoogle {
    private String access_token;
    private String token_type;
}
