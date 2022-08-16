package ru.msaggik.spring.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.msaggik.spring.dao.PeopleDAO;
import ru.msaggik.spring.models.Person;

@Component
public class PersonValidator implements Validator {

    // обращение к БД
    private final PeopleDAO peopleDAO;
    @Autowired
    public PersonValidator(PeopleDAO peopleDAO) {
        this.peopleDAO = peopleDAO;
    }


    @Override
    public boolean supports(Class<?> aClass) { // к какому классу относится валидатор
        return Person.class.equals(aClass);
    }

    //Вариант 1
//    @Override
//    public void validate(Object o, Errors errors) {
//        Person person = (Person) o; // даункастинг объекта о до объекта person
//        // мониторинг повторения email в БД
//        if (peopleDAO.show(person.getEmail()) != null) // условие для ошибки
//            errors.rejectValue("email", "", "This email is already taken"); // (поле, код ошибки, сообщение ошибки)
//    }
    // Вариант 2
    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o; // даункастинг объекта о до поля person
        // мониторинг повторения email в БД
        if (peopleDAO.show(person.getEmail()).isPresent()) // условие для ошибки
            errors.rejectValue("email", "", "This email is already taken"); // (поле, код ошибки, сообщение ошибки)
    }
}
