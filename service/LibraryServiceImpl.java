package service;

import model.Book;
import repository.BookRepository;
import storage.StorageStrategy;
import storage.TextFileStorage;

import java.util.List;
import java.util.Scanner;

public class LibraryServiceImpl implements LibraryService {

    private final BookRepository repository;
    private final Scanner scanner;
    private final StorageStrategy storage;

    public LibraryServiceImpl() {
        repository = new BookRepository();
        scanner = new Scanner(System.in);
        storage = new TextFileStorage(); // change strategy here
    }

    @Override
    public void addBook() {

        System.out.print("Enter book id: ");
        int id = Integer.parseInt(scanner.nextLine());

        if (repository.findById(id) != null) {
            System.out.println("Book id already exists!");
            return;
        }

        System.out.print("Enter title: ");
        String title = scanner.nextLine();

        System.out.print("Enter author: ");
        String author = scanner.nextLine();

        repository.add(new Book(id, title, author));

        System.out.println("Added successfully!");
    }

    @Override
    public void viewBooks() {

        List<Book> books = repository.findAll();

        if (books.isEmpty()) {
            System.out.println("No books found!");
            return;
        }

        for (Book b : books) {
            System.out.println(b);
        }
    }

    @Override
    public void updateBook() {

        System.out.print("Enter id: ");
        int id = Integer.parseInt(scanner.nextLine());

        Book book = repository.findById(id);

        if (book == null) {
            System.out.println("Not found!");
            return;
        }

        System.out.print("New title: ");
        book.setTitle(scanner.nextLine());

        System.out.print("New author: ");
        book.setAuthor(scanner.nextLine());

        System.out.println("Updated!");
    }

    @Override
    public void deleteBook() {

        System.out.print("Enter id: ");
        int id = Integer.parseInt(scanner.nextLine());

        repository.delete(id);

        System.out.println("Deleted!");
    }

    @Override
    public void searchBook() {

        System.out.print("Enter keyword: ");
        String keyword = scanner.nextLine().toLowerCase();

        for (Book b : repository.findAll()) {
            if (b.getTitle().toLowerCase().contains(keyword)) {
                System.out.println(b);
            }
        }
    }

    @Override
    public void saveBooks() {
        storage.save(repository.findAll());
        System.out.println("Saved!");
    }

    @Override
    public void loadBooks() {
        List<Book> books = storage.load();
        repository.setBooks(books);
        System.out.println("Loaded!");
    }
}