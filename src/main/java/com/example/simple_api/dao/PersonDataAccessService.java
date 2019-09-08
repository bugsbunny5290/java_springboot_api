package com.example.simple_api.dao;

import com.example.simple_api.model.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("postgres")
public class PersonDataAccessService implements PersonDao {

  private final JdbcTemplate jdbcTemplate;


  private static List<Person> DB = new ArrayList<>();


  @Autowired
  public PersonDataAccessService(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public int insertPerson(UUID id, Person person) {

    final String sql = "INSERT INTO person (id, name) VALUES (?,?)";

//    return jdbcTemplate.update(sql, );

    DB.add(new Person(id, person.getName()));
    return 1;
  }

  @Override
  public List<Person> selectAllPeople() {
    final String sql = "SELECT id, name FROM person";
    return jdbcTemplate.query(sql, (resultSet, i) -> {
      UUID id = UUID.fromString(resultSet.getString("id"));
      String name = resultSet.getString("name");
      return new Person(id, name);
    });
  }

  @Override
  public Optional<Person> selectPersonById(UUID id) {
    final String sql = "SELECT id, name FROM person WHERE id = ?";
    Person person = jdbcTemplate.queryForObject(sql, new Object[]{id}, (resultSet, i) -> {
      UUID personId = UUID.fromString(resultSet.getString("id"));
      String name = resultSet.getString("name");
      return new Person(personId, name);
    });
    return Optional.ofNullable(person);
  }

  @Override
  public int deletePersonById(UUID id) {

    final String sql = "DELETE FROM person WHERE id = ?";

    jdbcTemplate.update(sql, id);
    return 1;

  }

  @Override
  public int updatePersonById(UUID id, Person person) {
    return selectPersonById(id).map(p -> {
      int indexOfPersonToUpdate = DB.indexOf(p);
      if (indexOfPersonToUpdate >= 0) {
        DB.set(indexOfPersonToUpdate, new Person(id, person.getName()));
        return 1;
      }
      return 0;
    }).orElse(0);
  }
}
