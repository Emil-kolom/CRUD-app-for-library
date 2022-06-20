package org.udemy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.udemy.models.Book;
import org.udemy.services.BooksService;
import org.udemy.services.PeopleService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService){
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String printIndexPage(Model model,
                                 @RequestParam(name = "page", required = false) Integer page,
                                 @RequestParam(name = "bookPerPage", required = false) Integer bookPerPage,
                                 @RequestParam(name = "sort_by_year",required = false)boolean isSortedByYear,
                                 @RequestParam(name = "search", required = false) String searchText){
        List<Book> bookList;
        if(page != null && bookPerPage != null){
            bookList = booksService.getPaginPage(page, bookPerPage,searchText);
        } else {
            bookList = booksService.list(searchText);
        }
        if(isSortedByYear){
            booksService.sortByYear(bookList);
        }
        model.addAttribute("books",bookList);
        return "books/index";
    }

    @GetMapping("/{id}")
    public String printPersonalPage(@PathVariable("id") int id,
                       Model model){
        Book book = booksService.getById(id);
        model.addAttribute("book", book);
        if(book.getOwner() == null){
            model.addAttribute("people", peopleService.list());
        }
        return "books/show";
    }

    @GetMapping("/new")
    public String printNewPage(@ModelAttribute("book") Book book){
        return "books/new";
    }

    @GetMapping("/{id}/edit")
    public String printEditPage(Model model, @PathVariable("id") int id){
        model.addAttribute("book", booksService.getById(id));
        return "books/edit";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "books/new";
        }
        booksService.save(book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "books/edit";
        }
        booksService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        booksService.deleteById(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id){
        booksService.releaseBookById(id);
        return "redirect:/books/";
    }

    @PatchMapping("/{id}/assign")
    public String releaseBook(@PathVariable("id") Integer id,
                              @RequestParam("person_id") int person_id){
        booksService.assignBookById(id, person_id);
        return "redirect:/books/";
    }

    @GetMapping("/search")
    public String search(){
        return "books/search";
    }
}
