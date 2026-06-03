package com.dat.library_management.service;

public class BookValidator {

    public static void validateId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be a positive number");
        } // This task is already handled by @Min annotation in BookData
    }

    public static void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        } // This task is already handled by @NotBlank annotation in BookData
    }

    public static void validateAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author cannot be empty");
        } // This task is already handled by @NotBlank annotation in BookData
    }

    public static void validateSearchKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Search keyword cannot be empty");
        } // This validation is specific to search operations and is not covered by annotations in BookData
    }
}

// Note: The validation methods in BookValidator are somewhat redundant due to the use of validation annotations in the BookData class. 
// However, they can still be useful for additional checks or for validating input that doesn't directly map to the BookData class 
// And handle bussiness logic related validation that is not covered by annotations as the web scale.