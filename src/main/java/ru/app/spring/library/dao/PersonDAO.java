package ru.app.spring.library.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.app.spring.library.mappers.BookMapper;
import ru.app.spring.library.mappers.PersonMapper;
import ru.app.spring.library.models.Book;
import ru.app.spring.library.models.Person;

import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person ORDER BY person_id", new PersonMapper());
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE person_id = ?", new PersonMapper(), id)
                .stream().findFirst().orElse(null);
    }

    public Person show(String fullName) {
        return jdbcTemplate.query("SELECT * FROM person WHERE full_name = ?", new PersonMapper(), fullName)
                .stream().findFirst().orElse(null);
    }

    public List<Book> getBooks(int id) {
        return jdbcTemplate.query("""
                        SELECT * FROM person p JOIN book b ON
                                p.person_id = b.person_id WHERE p.person_id = ?
                        """, new BookMapper(), id);
    }

    public void add(Person person) {
        jdbcTemplate.update("INSERT INTO person(full_name, birth_year) VALUES(?, ?)",
                person.getFullName(), person.getBirthYear());
    }

    public void edit(int id, Person person) {
        jdbcTemplate.update("UPDATE person SET full_name = ?, birth_year = ? WHERE person_id = ?",
                person.getFullName(), person.getBirthYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE person_id = ?", id);
    }
}
