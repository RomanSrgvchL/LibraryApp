package ru.app.spring.library.services;

import org.hibernate.Hibernate;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.app.spring.library.models.Book;
import ru.app.spring.library.models.Person;
import ru.app.spring.library.repositories.BooksRepository;
import ru.app.spring.library.repositories.PeopleRepository;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    private final BooksRepository booksRepository;

    public PeopleService(PeopleRepository peopleRepository, BooksRepository booksRepository) {
        this.peopleRepository = peopleRepository;
        this.booksRepository = booksRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll(Sort.by("personId"));
    }

    @Nullable
    public Person findById(int id) {
        return peopleRepository.findById(id).orElse(null);
    }

    @Nullable
    public Person findByFullName(String fullName) {
        return peopleRepository.findByFullName(fullName).orElse(null);
    }

    public List<Book> getBooks(int id) {
        Person person = peopleRepository.findById(id).orElse(null);
        Hibernate.initialize(Objects.requireNonNull(person).getBooks());
        return person.getBooks();
    }

    @Transactional
    public void update(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void deleteById(int id) {
        peopleRepository.deleteById(id);
    }
}
