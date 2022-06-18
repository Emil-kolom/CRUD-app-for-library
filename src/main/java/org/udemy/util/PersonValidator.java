package org.udemy.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.udemy.models.Person;
import org.udemy.repositories.PeopleRepository;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonValidator(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        Optional<Person> checkedPerson = peopleRepository.findByFullName(person.getFullName());
        if(checkedPerson.isPresent()) {
            if (!checkedPerson.get().getId().equals(person.getId())) {
                errors.rejectValue("fullName", "1", "Это фио используется сейчас");
            }
        }
    }
}
