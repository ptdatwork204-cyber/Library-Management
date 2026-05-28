package ui;
import repository.BookRepository;
import repository.InMemoryBookRepository;
import service.LibraryService;
import service.LibraryServiceImpl;
import storage.MemoryStorage;
import storage.StorageStrategy;
import storage.TextFileStorage;
import storage.ExcelFileStorage;

import java.util.Scanner;

public class LibraryMenu {

    private final LibraryService service;
    private final Scanner scanner;

    public LibraryMenu() {

    scanner = new Scanner(System.in);

    System.out.println("===== STORAGE OPTIONS =====");
    System.out.println("1. Memory");
    System.out.println("2. Text File");
    System.out.println("3. Excel File");

    System.out.print("Choose storage: ");

    int choice = Integer.parseInt(scanner.nextLine());

    StorageStrategy storage;

    switch (choice) {

    case 1:
        storage = new MemoryStorage();
        break;

    case 2:
        storage = new TextFileStorage();
        break;

    case 3:
        storage = new ExcelFileStorage();
        break;

    default:
        System.out.println("Invalid choice!");
        storage = new MemoryStorage();
}

    BookRepository repository = new InMemoryBookRepository();
    service = new LibraryServiceImpl(
        repository,
        storage
);

    service.loadBooks();
}
    public void start() {

        while (true) {

            System.out.println("\n===== LIBRARY MANAGEMENT =====");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Update Book");
            System.out.println("4. Delete Book");
            System.out.println("5. Search Book");
            System.out.println("6. Save Book");   
            System.out.println("0. Exit");

            System.out.print("Choose: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {

                case 1:
                    service.addBook();
                    break;

                case 2:
                    service.viewBooks();
                    break;

                case 3:
                    service.updateBook();
                    break;

                case 4:
                    service.deleteBook();
                    break;

                case 5:
                    service.searchBook();
                    break;
                
                case 6:
                    service.saveBooks();
                    break;

                case 0:
                    System.out.println("Exit program!");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}