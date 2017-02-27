package se.jdr.service;

import java.util.Collection;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.PagingAndSortingRepository;

import se.jdr.model.AbstractEntity;
import se.jdr.service.ServiceTransaction.Action;

abstract class BaseService<E extends AbstractEntity, R extends PagingAndSortingRepository<E, Long>> {

	protected R repository;
	protected UserService userService;
	protected WorkItemService workItemService;
	protected TeamService teamService;
	protected IssueService issueService;
	protected AuditingService auditingService;
	private ServiceTransaction serviceTransaction;
	
	public BaseService(R repository, ServiceTransaction serviceTransaction) {
		this.repository = repository;
		this.serviceTransaction = serviceTransaction;
	}

	protected void setUserService(UserService userService){
		this.userService = userService;
	}
	
	protected void setWorkItemService(WorkItemService workItemService) {
		this.workItemService = workItemService;
	}
	
	protected void setIssueService(IssueService issueService) {
		this.issueService = issueService;
	}
	
	protected void setTeamService(TeamService teamService) {
		this.teamService = teamService;
	}
	
	protected void setAuditingService(AuditingService auditingService) {
		this.auditingService = auditingService;
	}
	
	protected <T> T execute(Action<T> action) throws ServiceException {
		try {
			return action.execute();			
		} catch (DataAccessException e) {
			throw new ServiceException("Execute failed: " + e.getMessage());
		}
	}
	
	protected <T> T transaction(Action<T> action) throws ServiceException {
		try {
			return serviceTransaction.execute(() -> action.execute());			
		} catch (DataAccessException e) {
			throw new ServiceException("Transaction failed: " + e.getMessage());
		}
	}

	public Collection<E> getAll(int page, int size) throws ServiceException {
		return execute(() -> repository.findAll(createPageRequest(page, size)).getContent());
	}
	
	public Collection<E> getAll(int page) throws ServiceException {
		return getAll(page, 10);
	}
	
	protected Pageable createPageRequest(int page, int size) {
		return new PageRequest(page, size);
	}

	public Collection<E> getRevisions(E entity) throws ServiceException {
		return execute(() -> auditingService.getRevisions(entity));
	}
	
	public E refreshEntity(E entity) throws ServiceException {
		return execute(() -> repository.findOne(entity.getId()));
	}
	
}
