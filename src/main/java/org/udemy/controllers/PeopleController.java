//package org.udemy.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import org.udemy.dao.BookDAO;
//import org.udemy.dao.PersonDAO;
//import org.udemy.models.Person;
//import org.udemy.util.PersonValidator;
//
//import javax.validation.Valid;
//
//@Controller
//@RequestMapping("/people")
//public class PeopleController {
//
//    private final PersonDAO personDAO;
//    private final BookDAO bookDAO;
//    private final PersonValidator personValidator;
//
//    @Autowired
//    public PeopleController(PersonDAO personDAO, BookDAO bookDAO, PersonValidator personValidator) {
//        this.personDAO = personDAO;
//        this.bookDAO = bookDAO;
//        this.personValidator = personValidator;
//    }
//
//    @GetMapping()
//    public String printIndexPage(Model model){
//        model.addAttribute("people", personDAO.list());
//        return "people/index";
//    }
//
//    @GetMapping("/{id}")
//    public String printPersonalPage(@PathVariable("id") int id,
//                       Model model){
//        model.addAttribute("person", personDAO.getById(id));
//        model.addAttribute("bookList", bookDAO.listByPersonId(id));
//        return "people/show";
//    }
//
//    @GetMapping("/new")
//    public String printNewPage(@ModelAttribute("person") Person person){
//        return "people/new";
//    }
//
//    @GetMapping("/{id}/edit")
//    public String printEditPage(Model model, @PathVariable("id") int id){
//        model.addAttribute("person", personDAO.getById(id));
//        return "people/edit";
//    }
//
//    @PostMapping()
//    public String create(@ModelAttribute("person") @Valid Person person,
//                         BindingResult bindingResult){
//        personValidator.validate(person, bindingResult);
//
//        if(bindingResult.hasErrors()){
//            return "people/new";
//        }
//        personDAO.save(person);
//        return "redirect:/people";
//    }
//
//    @PatchMapping("/{id}")
//    public String update(@ModelAttribute("person") @Valid Person person,
//                         BindingResult bindingResult,
//                         @PathVariable("id") int id){
//
//        personValidator.validate(person, bindingResult);
//
//        if(bindingResult.hasErrors()){
//            return "people/edit";
//        }
//        personDAO.update(id, person);
//        return "redirect:/people";
//    }
//
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable("id") int id){
//        personDAO.delete(id);
//        return "redirect:/people";
//    }
//}
