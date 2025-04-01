package ru.app.spring.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.app.spring.library.models.Book;

public interface BooksRepository extends JpaRepository<Book, Integer> {
}
