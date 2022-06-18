package org.udemy.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "book")
public class Book {
        Integer id,
        @NotEmpty(message = "Название книги не может быть пустым")
        String name,
        @NotEmpty(message = "Имя автора не может быть пустым")
        String author,
        @NotNull(message = "Год должен быть указан")
        Integer year,
        Integer personId
}
