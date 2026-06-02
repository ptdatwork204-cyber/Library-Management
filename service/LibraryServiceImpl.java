package service;

import model.Book;
import model.BookData;
import repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

public class LibraryServiceImpl implements LibraryService {

    private final BookRepository repository;

    public LibraryServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public OperationResult addBook(BookData bookData) {

        // Validate input
        BookValidator.validateId(bookData.getId());
        BookValidator.validateTitle(bookData.getTitle());
        BookValidator.validateAuthor(bookData.getAuthor());

        // Business logic
        if (repository.findById(bookData.getId()) != null) {
            return OperationResult.error("Book with ID " + bookData.getId() + " already exists");
        }

        Book book = bookData.toBook();
        repository.add(book);

        return OperationResult.ok("Book added successfully");
    }

    @Override
    public OperationResult viewAllBooks() {

        List<Book> books = repository.findAll();

        if (books.isEmpty()) {
            return OperationResult.ok("No books found", List.of());
        }

        return OperationResult.ok("Books retrieved successfully", books);
    }

    @Override
    public OperationResult updateBook(BookData bookData) {

        // Validate input
        BookValidator.validateId(bookData.getId());
        BookValidator.validateTitle(bookData.getTitle());
        BookValidator.validateAuthor(bookData.getAuthor());

        // Business logic
        Book existingBook = repository.findById(bookData.getId());

        if (existingBook == null) {
            return OperationResult.error("Book not found with ID: " + bookData.getId());
        }

        Book book = bookData.toBook();
        repository.update(book);

        return OperationResult.ok("Book updated successfully");
    }

    @Override
    public OperationResult deleteBook(int id) {

        // Validate input
        BookValidator.validateId(id);

        // Business logic
        Book existingBook = repository.findById(id);

        if (existingBook == null) {
            return OperationResult.error("Book not found with ID: " + id);
        }

        repository.delete(id);

        return OperationResult.ok("Book deleted successfully");
    }

    @Override
    public OperationResult searchBooks(String keyword) {

        // Validate input
        BookValidator.validateSearchKeyword(keyword);

        // Business logic - use PostgreSQL Full Text Search
        List<Book> results = repository.searchByKeyword(keyword);

        if (results.isEmpty()) {
            return OperationResult.ok("No books found matching: " + keyword, List.of());
        }

        return OperationResult.ok("Found " + results.size() + " book(s)", results);
    }

    @Override
    public OperationResult searchBookById(int id) {

        // Validate input
        BookValidator.validateId(id);

        // Business logic
        Book book = repository.findById(id);

        if (book == null) {
            return OperationResult.error("Book not found with ID: " + id);
        }

        return OperationResult.ok("Book found", book);
    }
}