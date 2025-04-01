package ru.app.spring.library.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.app.spring.library.models.Person;
import ru.app.spring.library.services.PeopleService;

@Component
public class PersonValidator implements Validator {
    private final PeopleService peopleService;

    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        // Уникальность ФИО
        Person sameFullNamePerson = peopleService.findByFullName(person.getFullName());
        if (sameFullNamePerson != null && sameFullNamePerson.getPersonId() != person.getPersonId()) {
            errors.rejectValue("fullName", "",
                    "Человек с таким ФИО уже существует");
        }
    }
}
