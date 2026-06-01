package repository;

import model.Book;

import java.util.ArrayList;
import java.util.List;

public class InMemoryBookRepository
        implements BookRepository {

    private final List<Book> books =
            new ArrayList<>();

    @Override
    public void add(Book book) {
        books.add(book);
    }

    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public Book findById(int id) {

        for (Book b : books) {

            if (b.getId() == id) {
                return b;
            }
        }

        return null;
    }

    @Override
    public void delete(int id) {

        books.removeIf(b -> b.getId() == id);
    }

    @Override
    public void setBooks(List<Book> books) {

        this.books.clear();
        this.books.addAll(books);
    }
    
    @Override
    public void update(Book book) {

    for (int i = 0; i < books.size(); i++) {

        if (books.get(i).getId() == book.getId()) {

            books.set(i, book);

            return;
        }
    }
}

}