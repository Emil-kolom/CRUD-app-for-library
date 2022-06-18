package org.udemy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.udemy.dao.BookDAO;
import org.udemy.dao.PersonDAO;
import org.udemy.models.Book;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String printIndexPage(Model model){
        model.addAttribute("books", bookDAO.list());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String printPersonalPage(@PathVariable("id") int id,
                       Model model){
        Book book = bookDAO.getById(id);
        model.addAttribute("book", book);
        if(book.personId() == null){
            model.addAttribute("people", personDAO.list());
        }
        else{
            model.addAttribute("peopleFullName",
                    personDAO.getById(book.personId()).fullName());
        }
        return "books/show";
    }

    @GetMapping("/new")
    public String printNewPage(@ModelAttribute("book") Book book){
        return "books/new";
    }

    @GetMapping("/{id}/edit")
    public String printEditPage(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookDAO.getById(id));
        return "books/edit";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "books/new";
        }
        bookDAO.save(book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "books/edit";
        }
        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDAO.deleteById(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id){
        bookDAO.releaseBookById(id);
        return "redirect:/books/";
    }

    @PatchMapping("/{id}/assign")
    public String releaseBook(@PathVariable("id") Integer id,
                              @RequestParam("person_id") int person_id){
        bookDAO.assignBookById(id, person_id);
        return "redirect:/books/";
    }
}
