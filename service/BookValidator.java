package service;

import model.Book;

public class BookValidator {

    public static void validateId(int id) {
        if (id <= 0) {
            throw new ValidationException("ID must be a positive number");
        }
    }

    public static void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new ValidationException("Title cannot be empty");
        }
        if (title.length() > 200) {
            throw new ValidationException("Title cannot exceed 200 characters");
        }
    }

    public static void validateAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new ValidationException("Author cannot be empty");
        }
        if (author.length() > 100) {
            throw new ValidationException("Author cannot exceed 100 characters");
        }
    }

    public static void validateBook(Book book) {
        validateId(book.getId());
        validateTitle(book.getTitle());
        validateAuthor(book.getAuthor());
    }

    public static void validateSearchKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new ValidationException("Search keyword cannot be empty");
        }
    }
}