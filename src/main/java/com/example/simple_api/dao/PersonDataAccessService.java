package com.example.simple_api.dao;

import com.example.simple_api.model.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository("pDao")
public class PersonDataAccessService implements PersonDao{

  private static List<Person> DB = new ArrayList<>();

  @Override
  public int insertPerson(UUID id, Person person) {
    DB.add(new Person(id, person.getName()));
    return 1;
  }

  @Override
  public List<Person> selectAllPeople() {
    return DB;
  }
}
