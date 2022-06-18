package org.udemy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.udemy.models.Book;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
}
