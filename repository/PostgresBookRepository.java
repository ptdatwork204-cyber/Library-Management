package repository;

import database.DBConnection;
import model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgresBookRepository
        implements BookRepository {

    @Override
    public void add(Book book) {

        String sql =
                "INSERT INTO books(id, title, author) VALUES (?, ?, ?)";

        try (
                Connection conn =
                        DBConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, book.getId());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to add book",
                    e
            );
        }
    }

    @Override
    public List<Book> findAll() {

        List<Book> books =
                new ArrayList<>();

        String sql =
                "SELECT * FROM books ORDER BY id";

        try (
                Connection conn =
                        DBConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            while (rs.next()) {

                books.add(
                        new Book(
                                rs.getInt("id"),
                                rs.getString("title"),
                                rs.getString("author")
                        )
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to get books",
                    e
            );
        }

        return books;
    }

    @Override
    public Book findById(int id) {

        String sql =
                "SELECT * FROM books WHERE id = ?";

        try (
                Connection conn =
                        DBConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            try (
                    ResultSet rs =
                            stmt.executeQuery()
            ) {

                if (rs.next()) {

                    return new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to find book",
                    e
            );
        }

        return null;
    }

    @Override
    public void update(Book book) {

        String sql =
                "UPDATE books\n" +
                "SET title = ?,\n" +
                "    author = ?\n" +
                "WHERE id = ?";

        try (
                Connection conn =
                        DBConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to update book",
                    e
            );
        }
    }

    @Override
    public void delete(int id) {

        String sql =
                "DELETE FROM books WHERE id = ?";

        try (
                Connection conn =
                        DBConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to delete book",
                    e
            );
        }
    }

   
}