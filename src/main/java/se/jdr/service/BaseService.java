package se.jdr.service;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import se.jdr.model.AbstractEntity;
import se.jdr.service.ServiceTransaction.Action;

abstract class BaseService<E extends AbstractEntity, R extends PagingAndSortingRepository<E, Long>> {

	protected R repository;
	private ServiceTransaction serviceTransaction;
	protected UserService userService;
	protected WorkItemService workItemService;
	protected TeamService teamService;
	protected IssueService issueService;
	protected AuditingService auditingService;
	
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
	
	protected Pageable createPageRequest(int page, int size) {
	    return new PageRequest(page, size);
	}
	
	public E refreshEntity(E entity) throws ServiceException {
		return execute(() -> repository.findOne(entity.getId()));
	}
	
	public E getById(Long id) throws ServiceException {
		return execute(() -> repository.findOne(id));
	}
	
	public Page<E> getAll(int page, int size) throws ServiceException {
		return execute(() -> repository.findAll(createPageRequest(page, size)));
	}
	
	public Page<E> getAll(int page) throws ServiceException {
		return getAll(page, 10);
	}

	public Page<E> getAll(Pageable pageable) throws ServiceException {
		return getAll(pageable.getPageNumber(), pageable.getPageSize());
	}
}
