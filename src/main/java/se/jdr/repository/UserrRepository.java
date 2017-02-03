package se.jdr.repository;

import org.springframework.data.repository.CrudRepository;

import se.jdr.model.User;

public interface UserrRepository extends CrudRepository<User, Long> {

}
