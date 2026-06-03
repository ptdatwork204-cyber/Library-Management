package com.dat.library_management.service;

import com.dat.library_management.model.Book;
import com.dat.library_management.model.BookData;
import com.dat.library_management.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepository;

    public LibraryServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public OperationResult addBook(BookData bookData) {

        // Validate input
        BookValidator.validateTitle(bookData.getTitle());
        BookValidator.validateAuthor(bookData.getAuthor());

        // Business logic
        if (bookData.getId() != null && bookRepository.existsById(bookData.getId())) {
            return OperationResult.error("Book with ID " + bookData.getId() + " already exists");
        }

        Book book = bookData.toBook();
        bookRepository.save(book);

        return OperationResult.ok("Book added successfully");
    }

    @Override
    @Transactional(readOnly = true)
    public OperationResult viewAllBooks() {

        List<Book> books = bookRepository.findAll();

        if (books.isEmpty()) {
            return OperationResult.ok("No books found", List.of());
        }

        return OperationResult.ok("Books retrieved successfully", books);
    }

    @Override
    @Transactional
    public OperationResult updateBook(BookData bookData) {

        // Validate input
        BookValidator.validateTitle(bookData.getTitle());
        BookValidator.validateAuthor(bookData.getAuthor());

        // Business logic
        if (!bookRepository.existsById(bookData.getId())) {
            return OperationResult.error("Book not found with ID: " + bookData.getId());
        }

        Book book = bookData.toBook();
        bookRepository.save(book);

        return OperationResult.ok("Book updated successfully");
    }

    @Override
    @Transactional
    public OperationResult deleteBook(int id) {

        // Validate input
        BookValidator.validateId(id);

        // Business logic
        if (!bookRepository.existsById(id)) {
            return OperationResult.error("Book not found with ID: " + id);
        }

        bookRepository.deleteById(id);

        return OperationResult.ok("Book deleted successfully");
    }

    @Override
    @Transactional(readOnly = true)
    public OperationResult searchBooks(String keyword) {

        // Validate input
        BookValidator.validateSearchKeyword(keyword);

        // Business logic
        List<Book> results = bookRepository.searchByKeyword(keyword);

        if (results.isEmpty()) {
            return OperationResult.ok("No books found matching: " + keyword, List.of());
        }

        return OperationResult.ok("Found " + results.size() + " book(s)", results);
    }

    @Override
    @Transactional(readOnly = true)
    public OperationResult searchBookById(int id) {

        // Validate input
        BookValidator.validateId(id);

        // Business logic
        return bookRepository.findById(id)
                .map(book -> OperationResult.ok("Book found", book))
                .orElseGet(() -> OperationResult.error("Book not found with ID: " + id));
    }
}