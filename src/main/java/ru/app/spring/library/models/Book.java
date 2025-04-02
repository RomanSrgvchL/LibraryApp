package ru.app.spring.library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.time.ZonedDateTime;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner;

    @Size(min = 1, max = 100)
    @Column(name = "title")
    private String title;

    @Size(min = 1, max = 100)
    @Column(name = "author")
    private String author;

    @Range(min = 1500, max = 2025)
    @Column(name =  "release_year")
    private int releaseYear;

    @Column(name = "checkout_date")
    private ZonedDateTime checkoutDate;

    @Transient
    private boolean isOverdueCheckoutDate;

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", releaseYear=" + releaseYear +
                '}';
    }
}
