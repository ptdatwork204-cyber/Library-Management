package com.dat.library_management.controller;

import com.dat.library_management.model.BookData;
import com.dat.library_management.service.LibraryService;
import com.dat.library_management.service.OperationResult;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final LibraryService libraryService;

    public BookController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    public ResponseEntity<OperationResult> viewAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        OperationResult result = libraryService.viewAllBooksPaginated(page, size);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperationResult> getBookById(@PathVariable int id) {
        OperationResult result = libraryService.searchBookById(id);
        return result.isSuccess()
                ? ResponseEntity.ok(result)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/search")
    public ResponseEntity<OperationResult> searchBooks(@RequestParam String keyword) {
        OperationResult result = libraryService.searchBooks(keyword);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<OperationResult> addBook(@Valid @RequestBody BookData bookData) {
        OperationResult result = libraryService.addBook(bookData);
        return result.isSuccess()
                ? ResponseEntity.status(HttpStatus.CREATED).body(result)
                : ResponseEntity.badRequest().body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OperationResult> updateBook(
            @PathVariable int id,
            @Valid @RequestBody BookData bookData) {
        bookData.setId(id);
        OperationResult result = libraryService.updateBook(bookData);
        return result.isSuccess()
                ? ResponseEntity.ok(result)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OperationResult> deleteBook(@PathVariable int id) {
        OperationResult result = libraryService.deleteBook(id);
        return result.isSuccess()
                ? ResponseEntity.ok(result)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }
}