package ru.app.spring.library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Entity
@Table(name = "person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private int personId;

    @Size(min = 1, max = 100)
    @Pattern(regexp = "([А-ЯA-Z][а-яa-z]+)( [А-ЯA-Z][а-яa-z]+){2}",
            message = "ФИО должно быть в следующем формате: Фамилия Имя Отчество")
    @Column(name = "full_name")
    private String fullName;

    @Range(min = 1900, max = 2025)
    @Column(name = "birth_year")
    private int birthYear;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", fullName='" + fullName + '\'' +
                ", birthYear=" + birthYear +
                '}';
    }
}
