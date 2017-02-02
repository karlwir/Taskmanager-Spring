package se.jdr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "issues")
public class Issue {

	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = false)
	private String description;

	protected Issue() {
	}

	public Issue(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "Issue " + id + ", description: " + description;
	}

}
