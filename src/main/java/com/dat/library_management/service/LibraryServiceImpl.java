package com.dat.library_management.service;

import com.dat.library_management.model.Book;
import com.dat.library_management.model.BookData;
import com.dat.library_management.model.BookResponse;
import com.dat.library_management.model.PagedResponse;
import com.dat.library_management.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepository;

    public LibraryServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public OperationResult addBook(BookData bookData) {

        // Validation is handled by Spring annotations (@Valid + @NotBlank in BookData)

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
    @Transactional(readOnly = true)
    public OperationResult viewAllBooksPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookRepository.findAll(pageable);

        List<BookResponse> bookResponses = bookPage.getContent().stream()
                .map(BookResponse::fromBook)
                .collect(Collectors.toList());

        PagedResponse<BookResponse> pagedResponse = PagedResponse.fromPage(bookPage, bookResponses);

        return OperationResult.ok(
                "Page " + (page + 1) + " retrieved successfully",
                pagedResponse
        );
    }

    @Override
    @Transactional
    public OperationResult updateBook(BookData bookData) {

        // Validation is handled by Spring annotations (@Valid + @NotBlank in BookData)

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

        // Validation is handled by Spring annotations (@Valid in controller)

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

        // Validation is handled by Spring annotations (@Valid in controller)

        // Business logic
        return bookRepository.findById(id)
                .map(book -> OperationResult.ok("Book found", book))
                .orElseGet(() -> OperationResult.error("Book not found with ID: " + id));
    }
}