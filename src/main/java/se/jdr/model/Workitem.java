package se.jdr.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "work_items")
public class Workitem {

	@Id
	@GeneratedValue
	private Long id;

}
