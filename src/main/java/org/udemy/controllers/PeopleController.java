package org.udemy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.udemy.models.Person;
import org.udemy.services.PeopleService;
import org.udemy.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

   private final PeopleService peopleService;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PeopleService peopleService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String printIndexPage(Model model){
        model.addAttribute("people", peopleService.list());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String printPersonalPage(@PathVariable("id") int id,
                       Model model){
        model.addAttribute("person", peopleService.findById(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String printNewPage(@ModelAttribute("person") Person person){
        return "people/new";
    }

    @GetMapping("/{id}/edit")
    public String printEditPage(Model model, @PathVariable("id") int id){
        model.addAttribute("person", peopleService.findById(id));
        return "people/edit";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult){
        personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors()){
            return "people/new";
        }
        peopleService.save(person);
        return "redirect:/people";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("id") int id){

        personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors()){
            return "people/edit";
        }
        peopleService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        peopleService.deleteById(id);
        return "redirect:/people";
    }
}
