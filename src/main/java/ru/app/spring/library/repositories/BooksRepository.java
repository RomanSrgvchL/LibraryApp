package ru.app.spring.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.app.spring.library.models.Book;

import java.util.List;

public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByTitleStartingWith(String title);
}
