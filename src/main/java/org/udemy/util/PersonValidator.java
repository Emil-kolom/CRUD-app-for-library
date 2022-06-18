package org.udemy.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.udemy.models.Person;
import org.udemy.services.PeopleService;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService ) {
        this.peopleService = peopleService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        Optional<Person> checkedPerson = peopleService.findByFullName(person);
        if(checkedPerson.isPresent()) {
            // if it's not the same person
            if (!checkedPerson.get().getId().equals(person.getId())) {
                errors.rejectValue("fullName", "1", "Это фио используется сейчас");
            }
        }
    }
}
