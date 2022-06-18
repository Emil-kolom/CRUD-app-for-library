package org.udemy.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.udemy.dao.PersonDAO;
import org.udemy.models.Person;

@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        Person checkedPerson = personDAO.getByFullName(person.fullName());
        if(checkedPerson != null) {
            if (checkedPerson.id() != person.id()) {
                errors.rejectValue("fullName", "1", "Это фио используется сейчас");
            }
        }
    }
}
