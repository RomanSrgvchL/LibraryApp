package ru.app.spring.library.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.app.spring.library.models.Book;
import ru.app.spring.library.services.BooksService;
import ru.app.spring.library.services.PeopleService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final PeopleService peopleService;

    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping
    public String index(Model model, @RequestParam(value = "page", defaultValue = "-1") int page,
                        @RequestParam(value = "books_per_page", defaultValue = "-1") int booksPerPage,
                        @RequestParam(value = "sort_by_year", defaultValue = "false") boolean sortByYear) {
        List<Book> books;

        if (page >= 0 && booksPerPage > 0) {
            books = new ArrayList<>(booksService.findPage(page, booksPerPage));
            if (sortByYear) {
                books.sort(Comparator.comparingInt(Book::getReleaseYear));
            }
        } else {
            if (sortByYear) {
                books = booksService.findAllSortedByReleaseYear();
            } else {
                books = booksService.findAll();
            }
        }
        model.addAttribute("books", books);
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksService.findById(id));
        model.addAttribute("person", booksService.getOwner(id));
        model.addAttribute("people", peopleService.findAll());
        return "books/show";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("book") Book book) {
        return "books/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/add";
        }
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksService.findById(id));
        return "books/edit";
    }

    @PatchMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        booksService.update(book, id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assignBook(@PathVariable("id") int bookId, @RequestParam("owner") int personId) {
        booksService.assignBook(personId, bookId);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/return")
    public String returnBook(@PathVariable("id") int bookId) {
        booksService.returnBook(bookId);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(value = "title", required = false) String title) {
        boolean searchOn = false;
        if (title != null && !title.isEmpty()) {
            searchOn = true;
            Book book = booksService.findByTitle(title);
            model.addAttribute("book", book);
            if (book != null) {
                model.addAttribute("person", book.getOwner());
            }
        }
        model.addAttribute("searchOn", searchOn);
        return "books/search";
    }
}
