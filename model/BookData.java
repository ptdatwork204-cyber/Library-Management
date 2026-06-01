package model;

public class BookData {

    private int id;
    private String title;
    private String author;

    public BookData(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Book toBook() {
        return new Book(id, title, author);
    }
}