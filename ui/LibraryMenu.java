package ui;

import model.Book;
import model.BookData;
import repository.BookRepository;
import repository.PostgresBookRepository;
import service.LibraryService;
import service.LibraryServiceImpl;
import service.OperationResult;
import service.ValidationException;

import java.util.List;
import java.util.Scanner;

public class LibraryMenu {

    private final LibraryService service;
    private final Scanner scanner;

    public LibraryMenu() {

        scanner = new Scanner(System.in);

        BookRepository repository = new PostgresBookRepository();

        service = new LibraryServiceImpl(repository);

        System.out.println("===== LIBRARY SYSTEM STARTED (POSTGRESQL MODE) =====");
    }

    public void start() {

        while (true) {

            printMenu();

            int choice = readChoice();

            handleChoice(choice);
        }
    }

    private void printMenu() {
        System.out.println("\n===== LIBRARY MANAGEMENT =====");
        System.out.println("1. Add Book");
        System.out.println("2. View Books");
        System.out.println("3. Update Book");
        System.out.println("4. Delete Book");
        System.out.println("5. Search Book");
        System.out.println("0. Exit");
        System.out.print("Choose: ");
    }

    private int readChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void handleChoice(int choice) {

        switch (choice) {
            case 1:
                addBook();
                break;

            case 2:
                viewBooks();
                break;

            case 3:
                updateBook();
                break;

            case 4:
                deleteBook();
                break;

            case 5:
                searchBooks();
                break;

            case 0:
                System.out.println("Exit program!");
                scanner.close();
                System.exit(0);

            default:
                System.out.println("Invalid choice!");
        }
    }

    private void addBook() {

        try {
            System.out.print("Enter book id: ");
            int id = readPositiveInt();

            System.out.print("Enter title: ");
            String title = scanner.nextLine().trim();

            System.out.print("Enter author: ");
            String author = scanner.nextLine().trim();

            BookData bookData = new BookData(id, title, author);
            OperationResult result = service.addBook(bookData);

            System.out.println(result.getMessage());

        } catch (ValidationException e) {
            System.out.println("Validation error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
        }
    }

    private void viewBooks() {

        OperationResult result = service.viewAllBooks();

        if (!result.isSuccess()) {
            System.out.println(result.getMessage());
            return;
        }

        List<?> books = (List<?>) result.getData();

        if (books.isEmpty()) {
            System.out.println("No books found!");
            return;
        }

        System.out.println("\n=== BOOKS ===");
        for (Object obj : books) {
            System.out.println(obj);
        }
    }

    private void updateBook() {

        try {
            System.out.print("Enter id: ");
            int id = readPositiveInt();

            System.out.print("New title: ");
            String title = scanner.nextLine().trim();

            System.out.print("New author: ");
            String author = scanner.nextLine().trim();

            BookData bookData = new BookData(id, title, author);
            OperationResult result = service.updateBook(bookData);

            System.out.println(result.getMessage());

        } catch (ValidationException e) {
            System.out.println("Validation error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
        }
    }

    private void deleteBook() {

        try {
            System.out.print("Enter id: ");
            int id = readPositiveInt();

            OperationResult result = service.deleteBook(id);

            System.out.println(result.getMessage());

        } catch (ValidationException e) {
            System.out.println("Validation error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
        }
    }

    private void searchBooks() {

        try {
            System.out.print("Enter keyword: ");
            String keyword = scanner.nextLine().trim();

            OperationResult result = service.searchBooks(keyword);

            System.out.println(result.getMessage());

            List<?> books = (List<?>) result.getData();
            if (books != null && !books.isEmpty()) {
                System.out.println("\n=== RESULTS ===");
                for (Object obj : books) {
                    System.out.println(obj);
                }
            }

        } catch (ValidationException e) {
            System.out.println("Validation error: " + e.getMessage());
        }
    }

    private int readPositiveInt() {
        String input = scanner.nextLine().trim();
        int value = Integer.parseInt(input);
        if (value <= 0) {
            throw new NumberFormatException("ID must be positive");
        }
        return value;
    }
}