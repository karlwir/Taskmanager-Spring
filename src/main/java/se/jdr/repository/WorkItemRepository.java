package se.jdr.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import se.jdr.model.WorkItem;

public interface WorkItemRepository extends CrudRepository<WorkItem, Long> {
	
	public Collection<WorkItem> findByStatus(WorkItem.Status status);
	
//	@Query("select wi FROM #{#entityName} wi JOIN User u ON wi.userid = u.id WHERE u.team = :teamId")
//	Collection<WorkItem> getWorkItemsByTeamId(Long teamId);
	
	public Collection<WorkItem> findByUserId(Long userId);
	
	public Collection<WorkItem> findByDescriptionLike(String description);
	
	@Query("select wi from #{#entityName} wi join Issue i on wi.id = i.id where i.openIssue = 1")
	public Collection<WorkItem> getAllWorkItemsByIssue();
	
}
