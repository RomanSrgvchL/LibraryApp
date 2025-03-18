package ru.app.spring.library.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.app.spring.library.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Person person = new Person();

        person.setPersonId(resultSet.getInt("person_id"));
        person.setFullName(resultSet.getString("full_name"));
        person.setBirthYear(resultSet.getInt("birth_year"));

        return person;
    }
}
