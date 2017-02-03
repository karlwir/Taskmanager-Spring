package se.jdr.repository;

import org.springframework.data.repository.CrudRepository;

import se.jdr.model.Workitem;

public interface WorkitemRepository extends CrudRepository<Workitem, Long> {

}
