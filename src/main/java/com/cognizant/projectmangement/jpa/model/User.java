package com.cognizant.projectmangement.jpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the users database table.
 * @author CTS
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_id", unique=true, nullable=false)
	private int userId;
	
	@Column(name="employee_id", nullable=false)
	private int employeeId;

	@Column(name="first_name", nullable=false, length=200)
	private String firstName;

	@Column(name="last_name", nullable=false, length=200)
	private String lastName;
	
	//uni-directional many-to-one association to Project
	@ManyToOne
	@JoinColumn(name="project_id")
	private Project project;

	//uni-directional many-to-one association to Task
	@ManyToOne
	@JoinColumn(name="task_id")
	private Task task;
}