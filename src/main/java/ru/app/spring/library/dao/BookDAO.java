package ru.app.spring.library.dao;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class BookDAO {
    private final EntityManager entityManager;

    public BookDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
