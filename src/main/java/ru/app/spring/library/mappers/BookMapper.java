package ru.app.spring.library.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.app.spring.library.models.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Book book = new Book();

        book.setBookId(resultSet.getInt("book_id"));
        book.setPersonId(resultSet.getInt("person_id"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setReleaseYear(resultSet.getInt("release_year"));

        return book;
    }
}
