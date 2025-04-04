package ru.app.spring.library.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.app.spring.library.models.Book;
import ru.app.spring.library.models.Person;
import ru.app.spring.library.services.BooksService;
import ru.app.spring.library.services.PeopleService;
import ru.app.spring.library.util.PersonValidator;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;
    private final PersonValidator personValidator;
    private final BooksService booksService;

    public PeopleController(PeopleService peopleService, PersonValidator personValidator, BooksService booksService) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
        this.booksService = booksService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findById(id));
        List<Book> books = peopleService.getBooks(id);
        booksService.updateOverdueCheckoutDate(books);
        model.addAttribute("books", books);
        return "people/show";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.deleteById(id);
        return "redirect:/people";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("person") Person person) {
        return "people/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/add";
        }
        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findById(id));
        return "people/edit";
    }

    @PatchMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person person,
                       BindingResult bindingResult) {
        person.setPersonId(id);
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        peopleService.update(person);
        return "redirect:/people";
    }
}
