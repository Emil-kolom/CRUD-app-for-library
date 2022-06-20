package org.udemy.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "book")
public class Book {

        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(name = "title")
        @NotEmpty(message = "Название книги не может быть пустым")
        private String title;

        @Column(name = "author")
        @NotEmpty(message = "Имя автора не может быть пустым")
        private String author;

        @Column(name = "year")
        @NotNull(message = "Год должен быть указан")
        private Integer year;

        @ManyToOne
        @JoinColumn(name = "person_id", referencedColumnName = "id")
        private Person owner;

        @Column(name = "taken_at")
        private LocalDate takenAt;

        @Transient
        private boolean isOverdue;

        public Book() {
        }

        public Book(String title, String author, Integer year) {
                this.title = title;
                this.author = author;
                this.year = year;
        }

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public String getAuthor() {
                return author;
        }

        public void setAuthor(String author) {
                this.author = author;
        }

        public Integer getYear() {
                return year;
        }

        public void setYear(Integer year) {
                this.year = year;
        }

        public Person getOwner() {
                return owner;
        }

        public void setOwner(Person owner) {
                this.owner = owner;
        }

        public LocalDate getTakenAt() {
                return takenAt;
        }

        public void setTakenAt(LocalDate takenAt) {
                this.takenAt = takenAt;
        }

        public boolean isOverdue() {
                if(takenAt != null) {
                        Period period = Period.between(LocalDate.now(), takenAt);

                        return period.getDays() > 10;
                } else return false;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Book book = (Book) o;
                return id.equals(book.id) && title.equals(book.title) && Objects.equals(author, book.author) && Objects.equals(year, book.year);
        }

        @Override
        public int hashCode() {
                return Objects.hash(id, title, author, year);
        }
}
