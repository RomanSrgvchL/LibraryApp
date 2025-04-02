package ru.app.spring.library.services;

import org.springframework.data.domain.PageRequest;
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
public class BooksService {
    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    public BooksService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll(Sort.by("bookId"));
    }

    @Nullable
    public Book findById(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    public List<Book> findAllSortedByReleaseYear() {
        return booksRepository.findAll(Sort.by("releaseYear"));
    }

    public List<Book> findPage(int page, int booksPerPage) {
        return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    @Nullable
    public Book findByTitle(String title) {
        List<Book> books = booksRepository.findByTitleStartingWith(title);
        if (!books.isEmpty()) {
            return books.getFirst();
        } else {
            return null;
        }
    }

    @Nullable
    public Person getOwner(int id) {
        Book book = findById(id);
        return Objects.requireNonNull(book).getOwner();
    }

    @Transactional
    public void update(Book bookToBeUpdated, int id) {
        bookToBeUpdated.setOwner(Objects.requireNonNull(findById(id)).getOwner());
        bookToBeUpdated.setBookId(id);
        booksRepository.save(bookToBeUpdated);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void assignBook(int personId, int bookId) {
        Person person = peopleRepository.findById(personId).orElse(null);
        Book book = booksRepository.findById(bookId).orElse(null);
        Objects.requireNonNull(book).setOwner(person);
    }

    @Transactional
    public void returnBook(int bookId) {
        Book book = booksRepository.findById(bookId).orElse(null);
        Objects.requireNonNull(book).setOwner(null);
    }
}
