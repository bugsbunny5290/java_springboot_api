package com.example.simple_api.service;

import com.example.simple_api.dao.PersonDao;
import com.example.simple_api.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

  private final PersonDao personDao;

  @Autowired
  public PersonService(@Qualifier("pDao") PersonDao personDao) {
    this.personDao = personDao;
  }

  public int addPerson(Person person){
    return personDao.insertPerson(person);
  }



}
