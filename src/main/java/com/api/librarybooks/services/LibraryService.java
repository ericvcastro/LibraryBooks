package com.api.librarybooks.services;

import com.api.librarybooks.dto.googleBooks.Book;
import com.api.librarybooks.dto.googleBooks.OAuthTokenGoogle;
import com.api.librarybooks.dto.googleBooks.Search;
import com.api.librarybooks.dto.response.BookFavouriteBookResponse;
import com.api.librarybooks.dto.response.BooksResponse;
import com.api.librarybooks.dto.response.BookResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import  com.google.api.client.json.JsonFactory;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class LibraryService {
    @Value("${google.apiKey}")
    private String apiKey;

    @Value("${google.clientId}")
    private String clientId;

    @Value("${google.UUID}")
    private String uuid;


    public ResponseEntity<String> favouriteBooks( String startIndex, String maxResults) throws IOException, InterruptedException {
        try{
            OAuthTokenGoogle tokenToApi = this.getAccessTokenUser();
            var client = HttpClient.newHttpClient();
            String url = "https://www.googleapis.com/books/v1/mylibrary/bookshelves/0/?key="+apiKey+"&startIndex="+startIndex+"&maxResults="+maxResults;
            var request = HttpRequest.newBuilder(URI.create(url))
                    .header("Authorization", tokenToApi.getToken_type() + ' ' + tokenToApi.getAccess_token())
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return ResponseEntity.status(response.statusCode()).body(response.body());
        } catch(Exception err) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server Error");
        }

    }
    public BooksResponse searchBooks(String q, String startIndex, String maxResults) throws IOException, InterruptedException {
        var client = HttpClient.newHttpClient();
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + URLEncoder.encode(q, StandardCharsets.UTF_8) + "&startIndex="+startIndex+"&maxResults="+maxResults;
        var request = HttpRequest.newBuilder(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        Search booksResponse = mapper.readValue(response.body(), Search.class);

        BooksResponse books = searchToLibraryBookResponse(booksResponse);

        return books;
    };

    private BooksResponse searchToLibraryBookResponse(Search search){
        BooksResponse response = new BooksResponse();
        ArrayList<BookResponse> list = new ArrayList<>();

        for (Book item : search.getItems()) {
            BookResponse bookResponse = new BookResponse();
            bookResponse.setId(item.getId());
            bookResponse.setTitle(item.getVolumeInfo().getTitle());
            bookResponse.setAuthors(item.getVolumeInfo().getAuthors());
            list.add(bookResponse);
        }

        response.setItems(list);

        return response;
    };

    public BookFavouriteBookResponse postFavouriteBook(String idBook) throws IOException, InterruptedException {
        OAuthTokenGoogle tokenToApi = this.getAccessTokenUser();
        var client = HttpClient.newHttpClient();
        String url = "https://www.googleapis.com/books/v1/mylibrary/bookshelves/0/addVolume?volumeId="+ idBook +"&key=" + apiKey;
        var request = HttpRequest.newBuilder(URI.create(url))
                .header("Authorization", tokenToApi.getToken_type() + ' ' + tokenToApi.getAccess_token())
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        BookFavouriteBookResponse addBook = new BookFavouriteBookResponse();
        addBook.setMessage(response.body());
        addBook.setStatus(response.body());

        return addBook;
    };

    private OAuthTokenGoogle getAccessTokenUser() throws IOException, InterruptedException {
        String code = "4/0AX4XfWh3jfbeEes2hdNB1-tWQYuF5MxXlD9AVh-hJhZpPt8Wbkk-DSx98O-ZpothglLspw";
        var values = new HashMap<String, String>() {{
            put("grant_type", "authorization_code");
            put("code", code);
            put("client_id", "636991331177-gjn3iu7qth5shfm649ji58efvespvl2u.apps.googleusercontent.com");
            put("client_secret", "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");
            put("redirect_uri", "https://localhost:8080/books/");
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(values);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://accounts.google.com/o/oauth2/token"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(response.body(), OAuthTokenGoogle.class);
    }
}
