package storage;
import model.Book;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class TextFileStorage implements StorageStrategy {

    private final String FILE_PATH = "books.txt";

    @Override
    public void save(List<Book> books) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {

            for (Book b : books) {
                pw.println(b.getId() + "|" + b.getTitle() + "|" + b.getAuthor());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> load() {
        List<Book> books = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {

            String line;
            while ((line = br.readLine()) != null) {

                String[] data = line.split("\\|");

                Book book = new Book(
                        Integer.parseInt(data[0]),
                        data[1],
                        data[2]
                );

                books.add(book);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }
}