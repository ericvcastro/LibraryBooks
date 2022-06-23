package com.api.librarybooks.repositories;

import com.api.librarybooks.models.LibraryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LibraryRepository extends JpaRepository<LibraryModel, UUID> {
}
