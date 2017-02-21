package se.jdr.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.jdr.model.WorkItem;
import se.jdr.model.WorkItem.Status;

@Component
public class AuditingService {

	private EntityManager entityManager;
	
	@Autowired
	public AuditingService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	private AuditReader getReader() {
		return AuditReaderFactory.get(entityManager);
	}

	@SuppressWarnings("unchecked")
	public Set<Long> getDoneWorkItemsByDate(LocalDateTime from, LocalDateTime to) {
		 List<WorkItem> workItems = getReader().createQuery()
											   .forRevisionsOfEntity(WorkItem.class, true, false)
											   .add(AuditEntity.property("status").eq(Status.DONE))
											   .add((AuditEntity.property("revisionDate").between(from, to)))
											   .getResultList();
		 
		 Set<Long> ids = new HashSet<>();
		 workItems.forEach(w -> ids.add(w.getId()));
		 return ids;
	}
}
