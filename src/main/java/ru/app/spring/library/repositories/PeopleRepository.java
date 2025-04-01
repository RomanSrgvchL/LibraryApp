package ru.app.spring.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.app.spring.library.models.Person;

public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Person findByFullName(String fullName);
}
