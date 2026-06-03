package com.dat.library_management.service;

import com.dat.library_management.model.BookData;
import com.dat.library_management.model.PagedResponse;

public interface LibraryService {

    OperationResult addBook(BookData book);

    OperationResult viewAllBooks();

    OperationResult viewAllBooksPaginated(int page, int size);

    OperationResult updateBook(BookData book);

    OperationResult deleteBook(int id);

    OperationResult searchBooks(String keyword);

    OperationResult searchBookById(int id);
}