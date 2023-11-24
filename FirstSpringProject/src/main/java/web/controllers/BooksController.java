package web.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.models.Book;
import web.models.Person;
import web.services.BooksService;
import web.services.PeopleService;


@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping
    public String showBooks(Model model, @RequestParam(value = "page") Integer page){
        model.addAttribute("page", page);
        model.addAttribute("books", booksService.getBooks(page));
        return "books/books";
    }

    @GetMapping("/{id}")
    public String showBookById(@PathVariable("id") int id,
                               @ModelAttribute("person") Person person,
                               Model model) {
        Person owner = booksService.findOwner(id);
        model.addAttribute("book", booksService.findOne(id));

        if (owner == null) {
            model.addAttribute("people", peopleService.findAll());
        } else model.addAttribute("owner", owner);
        return "books/book";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "books/new";
        booksService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                             @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) return "books/edit";
        booksService.updateBook(id, book);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        booksService.deleteBook(id);
        return "redirect:/books";
    }

    @PatchMapping("/free/{id}")
    public String freeBook(@PathVariable("id") int id) {
        booksService.freeBook(id);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/assign/{id}")
    public String assignBook(@PathVariable("id") int id, @ModelAttribute("person") Person person){
        booksService.assignBook(id, person);
        return "redirect:/books/{id}";
    }

    @GetMapping("/search")
    public String search() {
        return "books/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query) {
        model.addAttribute("books", booksService.searchBooks(query));
        return "books/search";
    }
}
