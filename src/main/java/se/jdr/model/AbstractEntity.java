package se.jdr.model;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class AbstractEntity {

	@Id
	@GeneratedValue
	private Long id;

	public Long getId() {
		return id;
	}
	
	@CreatedDate
	protected LocalDateTime createdDate;
	
	@LastModifiedDate
	protected LocalDateTime revisionDate;
	
	@CreatedBy
	@OneToOne
	protected User createdBy;
	
	@LastModifiedBy
	@OneToOne
	protected User revisionBy;

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return revisionDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public User getModifiedBy() {
		return revisionBy;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.revisionDate = modifiedDate;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.revisionBy = modifiedBy;
	}
}
