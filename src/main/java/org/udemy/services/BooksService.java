package org.udemy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.udemy.models.Book;
import org.udemy.models.Person;
import org.udemy.repositories.BooksRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class BooksService {
    private final BooksRepository booksRepository;
    private final PeopleService peopleService;

    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleService peopleService) {
        this.booksRepository = booksRepository;
        this.peopleService = peopleService;
    }

    @Transactional(readOnly = true)
    public List<Book> list(){
        return booksRepository.findAll();
    }


    @Transactional(readOnly = true)
    public List<Book> list(String searchText){
        if(Objects.nonNull(searchText)){
            return booksRepository.findAllByTitleLike(searchText);
        }
        return list();
    }

    @Transactional(readOnly = true)
    public Book getById(Integer id){
        return booksRepository.findById(id).orElse(null);
    }

    public void save(Book book){
        booksRepository.save(book);
    }

    public void update(Integer id, Book updateBook){
        Optional<Book> optionalBook = booksRepository.findById(id);
        if(!optionalBook.isPresent())
            return;

        Book book = optionalBook.get();
        book.setTitle(updateBook.getTitle());
        book.setYear(updateBook.getYear());
        book.setAuthor(updateBook.getAuthor());

    }

    public void deleteById(Integer id){
        booksRepository.deleteById(id);
    }

    public void releaseBookById(Integer id) {
        booksRepository.findById(id)
                .ifPresent(book->{
                    book.setOwner(null);
                    book.setTakenAt(null);
        });
    }

    public void assignBookById(Integer id, int person_id) {
        booksRepository.findById(id)
                .ifPresent(book->{
                    Person person = peopleService.findById(id);
                    if(person != null) {
                        book.setOwner(person);
                        book.setTakenAt(LocalDate.now());
                    }
                });
    }


    @Transactional(readOnly = true)
    public List<Book> getPaginPage(Integer page, Integer bookPerPage, String searchText) {
        if(page < 1 || bookPerPage < 1) {
            return list(searchText);
        }
        //to count from 1 and not from 0
        page -=1;
        if(Objects.nonNull(searchText)){
            return booksRepository.findAllByTitleLike(searchText, PageRequest.of(page,bookPerPage)).getContent();
        }
        return booksRepository.findAll(PageRequest.of(page,bookPerPage)).getContent();
    }

    @Transactional(readOnly = true)
    public void sortByYear(List<Book> bookList) {
        bookList.sort(Comparator.comparing(
                Book::getYear,
                Comparator.reverseOrder()));
    }
}
