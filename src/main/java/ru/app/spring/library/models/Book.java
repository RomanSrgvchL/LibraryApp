package ru.app.spring.library.models;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private int bookId;
    private int personId;

    @Size(min = 1, max = 100)
    private String title;

    @Size(min = 1, max = 100)
    private String author;

    @Range(min = 1500, max = 2025)
    private int releaseYear;
}
