package repository;

import model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    private final List<Book> books = new ArrayList<>();

    public void add(Book book) {
        books.add(book);
    }

    public List<Book> findAll() {
        return books;
    }

    public Book findById(int id) {

        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }

        return null;
    }

    public boolean delete(int id) {

        Book book = findById(id);

        if (book == null) {
            return false;
        }

        books.remove(book);
        return true;
    }

    public void setBooks(List<Book> books) {
        this.books.clear();
        this.books.addAll(books);
    }
}

