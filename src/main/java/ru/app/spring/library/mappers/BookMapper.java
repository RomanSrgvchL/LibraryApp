package ru.app.spring.library.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.app.spring.library.models.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setBookId(rs.getInt("book_id"));
        book.setPersonId(rs.getInt("person_id"));
        book.setAuthor(rs.getString("author"));
        book.setTitle(rs.getString("title"));
        book.setReleaseYear(rs.getInt("release_year"));
        return book;
    }
}
