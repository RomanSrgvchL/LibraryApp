package ru.app.spring.library.models;

import jakarta.validation.constraints.Pattern;
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
public class Person {
    private int personId;

    @Size(min = 1, max = 100)
    @Pattern(regexp = "([А-ЯA-Z][а-яa-z]+)( [А-ЯA-Z][а-яa-z]+){2}",
            message = "ФИО должно быть в следующем формате: Фамилия Имя Отчество")
    private String fullName;

    @Range(min = 1900, max = 2025)
    private int birthYear;
}
