package service;

import model.Book;
import repository.BookRepository;

import java.util.List;
import java.util.Scanner;

public class LibraryServiceImpl implements LibraryService {

    private final BookRepository repository;
    private final Scanner scanner;

    public LibraryServiceImpl() {
        repository = new BookRepository();
        scanner = new Scanner(System.in);
    }

    @Override
    public void addBook() {

        System.out.print("Enter book id: ");
        int id = Integer.parseInt(scanner.nextLine());

        // check duplicate id
        if (repository.findById(id) != null) {
            System.out.println("Book id already exists!");
            return;
        }

        System.out.print("Enter title: ");
        String title = scanner.nextLine();

        System.out.print("Enter author: ");
        String author = scanner.nextLine();

        Book book = new Book(id, title, author);

        repository.add(book);

        System.out.println("Add book successfully!");
    }

    @Override
    public void viewBooks() {

        List<Book> books = repository.findAll();

        if (books.isEmpty()) {
            System.out.println("No books found!");
            return;
        }

        System.out.println("\n===== BOOK LIST =====");

        for (Book book : books) {
            System.out.println(book);
        }
    }

    @Override
    public void updateBook() {

        System.out.print("Enter book id to update: ");
        int id = Integer.parseInt(scanner.nextLine());

        Book existingBook = repository.findById(id);

        if (existingBook == null) {
            System.out.println("Book not found!");
            return;
        }

        System.out.print("Enter new title: ");
        String newTitle = scanner.nextLine();

        System.out.print("Enter new author: ");
        String newAuthor = scanner.nextLine();

        existingBook.setTitle(newTitle);
        existingBook.setAuthor(newAuthor);

        System.out.println("Update successfully!");
    }

    @Override
    public void deleteBook() {

        System.out.print("Enter id to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        boolean deleted = repository.delete(id);

        if (deleted) {
            System.out.println("Delete successfully!");
        } else {
            System.out.println("Book not found!");
        }
    }

    @Override
    public void searchBook() {

        System.out.print("Enter id to search: ");
        int id = Integer.parseInt(scanner.nextLine());

        Book book = repository.findById(id);

        if (book == null) {
            System.out.println("Book not found!");
            return;
        }

        System.out.println(book);
    }
}