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
                break;
            default:
                System.out.println("Invalid choice!");
                break;
        }
    }


    private void addBook() {
        try {
            System.out.print("Enter book id: ");
            int id = readPositiveInt();

            System.out.print("Enter title: ");
            String title = scanner.nextLine().trim();

            debugString("TITLE", title);

            System.out.print("Enter author: ");
            String author = scanner.nextLine().trim();

            debugString("AUTHOR", author);

            BookData bookData = new BookData(id, title, author);
            OperationResult result = service.addBook(bookData);

            System.out.println(result.getMessage());

        } catch (ValidationException e) {
            System.out.println("Validation error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format!");
        }
    }

    private void debugString(String label, String value) {
        System.out.println("\n===== DEBUG " + label + " =====");
        System.out.println("Value: " + value);

        System.out.print("HEX: ");
        for (char c : value.toCharArray()) {
            System.out.print(Integer.toHexString(c) + " ");
        }
        System.out.println();
    }

   

    private void viewBooks() {
        OperationResult result = service.viewAllBooks();

        if (!result.isSuccess()) {
            System.out.println(result.getMessage());
            return;
        }

        List<Book> books = (List<Book>) result.getData();

        if (books == null || books.isEmpty()) {
            System.out.println("No books found!");
            return;
        }

        printTable(books);
    }

    private void printTable(List<Book> books) {

        String format = "| %-5s | %-30s | %-20s |%n";

        System.out.println("+-------+--------------------------------+----------------------+");
        System.out.printf("| %-5s | %-30s | %-20s |%n", "ID", "TITLE", "AUTHOR");
        System.out.println("+-------+--------------------------------+----------------------+");

        for (Book b : books) {
            System.out.printf(
                    format,
                    b.getId(),
                    truncate(b.getTitle(), 30),
                    truncate(b.getAuthor(), 20)
            );
        }

        System.out.println("+-------+--------------------------------+----------------------+");
    }

    private String truncate(String value, int max) {
        if (value == null) return "";
        return value.length() <= max
                ? value
                : value.substring(0, max - 3) + "...";
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

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

  

    private void deleteBook() {
        try {
            System.out.print("Enter id: ");
            int id = readPositiveInt();

            OperationResult result = service.deleteBook(id);
            System.out.println(result.getMessage());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private void searchBooks() {
        System.out.println("\n===== SEARCH BOOKS =====");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by keyword");
        System.out.println("0. Back");
        System.out.print("Choose: ");

        int choice = readChoice();

        switch (choice) {
            case 1:
                searchById();
                break;
            case 2:
                searchByKeyword();
                break;
            default:
                // do nothing (back or invalid)
                break;
        }
    }

    private void searchById() {
        try {
            System.out.print("Enter id: ");
            int id = readPositiveInt();

            OperationResult result = service.searchBookById(id);

            System.out.println(result.getMessage());
            System.out.println(result.getData());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void searchByKeyword() {
        try {
            System.out.print("Enter keyword: ");
            String keyword = scanner.nextLine().trim();

            OperationResult result = service.searchBooks(keyword);

            System.out.println(result.getMessage());

            List<Book> books = (List<Book>) result.getData();

            if (books != null && !books.isEmpty()) {
                printTable(books);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

   

    private int readPositiveInt() {
        int value = Integer.parseInt(scanner.nextLine());
        if (value <= 0) throw new NumberFormatException();
        return value;
    }
}