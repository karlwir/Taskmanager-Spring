package se.jdr.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import se.jdr.model.User;

public interface UserrRepository extends CrudRepository<User, Long> {

	Collection<User> findByFirstname(String firstname);
}
