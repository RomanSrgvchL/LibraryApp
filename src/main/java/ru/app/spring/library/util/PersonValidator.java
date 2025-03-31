package ru.app.spring.library.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.app.spring.library.dao.PersonDAO;
import ru.app.spring.library.models.Person;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        // Уникальность ФИО
        Person sameFullNamePerson = personDAO.show(person.getFullName());
        if (sameFullNamePerson != null && sameFullNamePerson.getPersonId() != person.getPersonId()) {
            errors.rejectValue("fullName", "",
                    "Человек с таким ФИО уже существует");
        }
    }
}
