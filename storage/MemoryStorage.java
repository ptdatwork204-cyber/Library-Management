package storage;

import model.Book;

import java.util.ArrayList;
import java.util.List;

public class MemoryStorage implements StorageStrategy {

    private List<Book> memory = new ArrayList<>();

    @Override
    public void save(List<Book> books) {
        memory = books;
    }

    @Override
    public List<Book> load() {
        return memory;
    }
}