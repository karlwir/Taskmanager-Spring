package se.jdr.service;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import se.jdr.model.AbstractEntity;
import se.jdr.service.ServiceTransaction.Action;

abstract class BaseService<E extends AbstractEntity, R extends PagingAndSortingRepository<E, Long>> {

	protected R repository;
	private ServiceTransaction serviceTransaction;
	private ServiceManager serviceManager;
	protected UserService userService;
	protected WorkItemService workItemService;
	protected TeamService teamService;
	protected IssueService issueService;
	
	public BaseService(R repository, ServiceManager serviceManager, ServiceTransaction serviceTransaction) {
		this.repository = repository;
		this.serviceManager = serviceManager;
		this.serviceTransaction = serviceTransaction;
	}
	
	public void init() {
		this.userService = serviceManager.getUserService();
		this.workItemService = serviceManager.getWorkItemService();
		this.teamService = serviceManager.getTeamService();
		this.issueService = serviceManager.getIssueService();
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
	
	public E getById(Long id) throws ServiceException {
		return execute(() -> repository.findOne(id));
	}
	
	public Page<E> getAll(int page, int size) throws ServiceException {
		return execute(() -> repository.findAll(createPageRequest(page, size)));
	}
	
	public Page<E> getAll(int page) throws ServiceException {
		return getAll(page, 10);
	}
}
