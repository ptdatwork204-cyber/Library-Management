package com.dat.library_management.service;

import com.dat.library_management.model.BookData;

public interface LibraryService {

    OperationResult addBook(BookData book);

    OperationResult viewAllBooks();

    OperationResult updateBook(BookData book);

    OperationResult deleteBook(int id);

    OperationResult searchBooks(String keyword);

    OperationResult searchBookById(int id);
}