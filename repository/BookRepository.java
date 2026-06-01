package repository;

import model.Book;

import java.util.List;

public interface BookRepository {

    void add(Book book);

    List<Book> findAll();

    Book findById(int id);

    void delete(int id);

    void update(Book book);

   
}