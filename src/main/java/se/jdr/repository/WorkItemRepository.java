package se.jdr.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import se.jdr.model.WorkItem;

public interface WorkItemRepository extends CrudRepository<WorkItem, Long> {

	public Collection<WorkItem> findByStatus(WorkItem.Status status);

	@Query("select wi FROM #{#entityName} wi JOIN wi.user u JOIN u.team t WHERE t.id = :teamId")
	Collection<WorkItem> getWorkItemsByTeamId(@Param("teamId") Long teamId);

	public Collection<WorkItem> findByUserId(Long userId);

	public Collection<WorkItem> findByDescriptionLike(String description);

	@Query("select workItem from Issue i WHERE i.openIssue=1")
	public Collection<WorkItem> getAllWorkItemsWithIssues();

	Long countByUserId(Long userId);

}
