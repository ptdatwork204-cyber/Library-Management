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

        String insertBook =
                "INSERT INTO books\n"
                + "(\n"
                + "    id,\n"
                + "    title,\n"
                + "    author,\n"
                + "    search_vector\n"
                + ")\n"
                + "VALUES\n"
                + "(\n"
                + "    ?,\n"
                + "    ?,\n"
                + "    ?,\n"
                + "    to_tsvector(\n"
                + "        'simple',\n"
                + "        unaccent(?)\n"
                + "    )\n"
                + ")";

        String insertLog =
                "INSERT INTO audit_logs(action)\n"
                + "VALUES (?)";

        try (Connection conn = DBConnection.getConnection()) {

                conn.setAutoCommit(false);

                try {

                System.out.println(
                        "AUTOCOMMIT = "
                        + conn.getAutoCommit()
                );

                int affectedRows;

                try (
                        PreparedStatement bookStmt =
                                conn.prepareStatement(insertBook)
                ) {

                        bookStmt.setInt(1, book.getId());
                        bookStmt.setString(2, book.getTitle());
                        bookStmt.setString(3, book.getAuthor());
                        bookStmt.setString(
                                4,
                                book.getTitle() + " " + book.getAuthor()
                        );

                        affectedRows =
                                bookStmt.executeUpdate();

                        System.out.println(
                                "BOOK INSERTED"
                        );
                }

                if (affectedRows == 0) {
                        throw new RuntimeException(
                                "Failed to insert book"
                        );
                }

                try (
                        PreparedStatement logStmt =
                                conn.prepareStatement(insertLog)
                ) {

                        logStmt.setString(
                                1,
                                "ADD_BOOK_" + book.getId()
                        );

                        logStmt.executeUpdate();

                        System.out.println(
                                "LOG INSERTED"
                        );
                }

                conn.commit();

                System.out.println(
                        "COMMIT SUCCESS"
                );

                } catch (Exception e) {

                System.out.println(
                        "ROLLBACK EXECUTED"
                );

                conn.rollback();

                throw e;
                }

        } catch (Exception e) {

                throw new RuntimeException(
                        "Transaction failed while adding book",
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

        String updateBook =
                "UPDATE books " +
                "SET " +
                "title = ?, " +
                "author = ?, " +
                "search_vector = " +
                "to_tsvector('simple', unaccent(?)) " +
                "WHERE id = ?";

        String insertLog =
                "INSERT INTO audit_logs(action) " +
                "VALUES (?)";

        try (
                Connection conn =
                        DBConnection.getConnection()
        ) {

                conn.setAutoCommit(false);

                try {

                int affectedRows;

                try (
                        PreparedStatement bookStmt =
                                conn.prepareStatement(updateBook)
                ) {

                        bookStmt.setString(1, book.getTitle());
                        bookStmt.setString(2, book.getAuthor());
                        bookStmt.setString(
                                3,
                                book.getTitle() + " " + book.getAuthor()
                        );
                        bookStmt.setInt(4, book.getId());

                        affectedRows =
                                bookStmt.executeUpdate();
                }

                if (affectedRows == 0) {
                        throw new RuntimeException(
                                "Book not found"
                        );
                }

                try (
                        PreparedStatement logStmt =
                                conn.prepareStatement(insertLog)
                ) {

                        logStmt.setString(
                                1,
                                "UPDATE_BOOK_" + book.getId()
                        );

                        logStmt.executeUpdate();
                }

                conn.commit();

                } catch (Exception e) {

                try {
                        conn.rollback();
                } catch (SQLException rollbackEx) {
                        rollbackEx.printStackTrace();
                }

                throw e;
                }

        } catch (Exception e) {

                throw new RuntimeException(
                        "Transaction failed while updating book",
                        e
                );
        }
        }

        @Override
        public void delete(int id) {

        String deleteBook =
                "DELETE FROM books " +
                "WHERE id = ?";

        String insertLog =
                "INSERT INTO audit_logs(action) " +
                "VALUES (?)";

        try (
                Connection conn =
                        DBConnection.getConnection()
        ) {

                conn.setAutoCommit(false);

                try {

                int affectedRows;

                try (
                        PreparedStatement deleteStmt =
                                conn.prepareStatement(deleteBook)
                ) {

                        deleteStmt.setInt(1, id);

                        affectedRows =
                                deleteStmt.executeUpdate();
                }

                if (affectedRows == 0) {
                        throw new RuntimeException(
                                "Book not found"
                        );
                }

                try (
                        PreparedStatement logStmt =
                                conn.prepareStatement(insertLog)
                ) {

                        logStmt.setString(
                                1,
                                "DELETE_BOOK_" + id
                        );

                        logStmt.executeUpdate();
                }

                conn.commit();

                } catch (Exception e) {

                try {
                        conn.rollback();
                } catch (SQLException rollbackEx) {
                        rollbackEx.printStackTrace();
                }

                throw e;
                }

        } catch (Exception e) {

                throw new RuntimeException(
                        "Transaction failed while deleting book",
                        e
                );
        }
        }

        @Override
        public List<Book> searchByKeyword(String keyword) {

                String sql =
                        "SELECT *\n"
                                + "FROM books\n"
                                + "WHERE search_vector @@\n"
                                + "      plainto_tsquery(\n"
                                + "            'simple',\n"
                                + "            unaccent(?)\n"
                                + "      )";

                List<Book> result = new ArrayList<>();

                try (Connection conn = DBConnection.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, keyword);

                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                        result.add(new Book(
                                rs.getInt("id"),
                                rs.getString("title"),
                                rs.getString("author")
                        ));
                }

                } catch (Exception e) {
                throw new RuntimeException(e);
                }

                return result;
        }

}