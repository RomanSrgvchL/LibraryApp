package ru.app.spring.library.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.app.spring.library.mappers.BookMapper;
import ru.app.spring.library.models.Book;
import ru.app.spring.library.models.Person;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book ORDER BY book_id", new BookMapper());
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE book_id = ?", new BookMapper(), id)
                .stream().findFirst().orElse(null);
    }

    public Person getOwner(int id) {
        return jdbcTemplate.query("""
                SELECT * FROM person p LEFT JOIN book b ON
                        p.person_id = b.person_id WHERE b.book_id = ?
                """, new BeanPropertyRowMapper<>(Person.class), id).stream().findFirst().orElse(null);
    }

    public void add(Book book) {
        jdbcTemplate.update("INSERT INTO book(title, author, release_year) VALUES(?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getReleaseYear());
    }

    public void edit(int id, Book book) {
        jdbcTemplate.update("UPDATE book SET title = ?, author = ?, release_year = ? WHERE book_id = ?",
                book.getTitle(), book.getAuthor(), book.getReleaseYear(), id);
    }

    public void assignBook(int bookId, int personId) {
        jdbcTemplate.update("UPDATE book SET person_id = ? WHERE book_id = ?", personId, bookId);
    }

    public void returnBook(int bookId) {
        jdbcTemplate.update("UPDATE book SET person_id = null WHERE book_id = ?", bookId);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE book_id = ?", id);
    }
}
