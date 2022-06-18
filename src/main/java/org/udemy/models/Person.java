package org.udemy.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {
        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(name = "full_name")
        @NotEmpty(message = "Имя не должно быть пустым")
        @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длиной")
        private String fullName;

        @Column(name = "year_birth")
        @Min(value = 1900, message = "Год рождения должен быть больше, чем 1900")
        private Integer yearBirth;

        @OneToMany(
                cascade = CascadeType.ALL,
                mappedBy = "owner"
        )
        private List<Book> bookList;

        public Person() {
        }

        public Person(String fullName, Integer yearBirth) {
                this.fullName = fullName;
                this.yearBirth = yearBirth;
        }

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public String getFullName() {
                return fullName;
        }

        public void setFullName(String fullName) {
                this.fullName = fullName;
        }

        public Integer getYearBirth() {
                return yearBirth;
        }

        public void setYearBirth(Integer yearBirth) {
                this.yearBirth = yearBirth;
        }

        public List<Book> getBookList() {
                return bookList;
        }

        public void setBookList(List<Book> bookList) {
                this.bookList = bookList;
        }
}