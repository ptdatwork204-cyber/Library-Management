package storage;

import java.util.List;
import model.Book;

public interface StorageStrategy {

    void save(List<Book> books);

    List<Book> load();
}