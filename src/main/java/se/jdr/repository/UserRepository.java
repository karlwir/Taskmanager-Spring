package se.jdr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import se.jdr.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	User findUserByUserId(String userId);

	User findByUsername(String username);

	@Query("select u from #{#entityName} u where u.firstname like %:fName% and u.lastname like %:lName%")
	Page<User> getUser(@Param("fName") String firstname, @Param("lName") String lastname, Pageable pageable);

	Page<User> findByTeamId(Long teamId, Pageable pageable);

	Long countByTeamId(Long teamId);

	Page<User> findByFirstName(String firstname, Pageable pageable);


}
