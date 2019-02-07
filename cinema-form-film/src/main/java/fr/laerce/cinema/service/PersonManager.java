package fr.laerce.cinema.service;

import fr.laerce.cinema.dao.PersonDao;
import fr.laerce.cinema.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Component
public class PersonManager {
    @Autowired
    PersonDao personDao;

    public List<Person> getAll(){
        return personDao.findAllByOrderBySurname();
    }
    public boolean existsByIdtmdb(BigInteger id){
        return personDao.existsByIdtmdb(id);
    }
    public Person savePerson(Person p)
    {
        return personDao.save(p);
    }
    public Person findByIdTmdb(BigInteger id){
        return personDao.findByIdtmdb(id);
    }
}
