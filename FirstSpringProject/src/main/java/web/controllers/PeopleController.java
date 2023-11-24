package web.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.models.Person;
import web.services.PeopleService;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String showAllPeople(Model model) {
        model.addAttribute("allPeople", peopleService.findAll());
        return "people/people";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findOne(id));
        model.addAttribute("books", peopleService.getBooksByPersonId(id));
        return "people/person";
    }

    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return "people/new";
    }
    @PostMapping
    public String saveNewPerson(@ModelAttribute("person") @Valid Person person,
                                BindingResult bindingResult){
        if (bindingResult.hasErrors()) return "people/new";
        peopleService.savePerson(person);
        return "redirect:/people";
    }
    @GetMapping("/edit/{id}")
    public String editPerson(Model model, @PathVariable("id") int id){
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }
    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult,
                               @PathVariable("id") int id){
        if (bindingResult.hasErrors()) return "people/edit";
        peopleService.updatePerson(id, person);
        return "redirect:/people/{id}";
    }
    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id){
        peopleService.deletePerson(id);
        return "redirect:/people";
    }
}
