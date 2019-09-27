package com.cognizant.projectmangement.jpa.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the project database table.
 * @author CTS
 * 
 */
@Entity
@Table(name="project")
@NamedQuery(name="Project.findAll", query="SELECT p FROM Project p")
@Getter
@Setter
@NoArgsConstructor
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="project_id", unique=true, nullable=false)
	private int projectId;

	@Column(name="end_date", nullable=false)
	private LocalDate endDate;

	@Column(nullable=false)
	private int priority;

	@Column(nullable=false, length=200)
	private String project;

	@Column(name="start_date", nullable=false)
	private LocalDate startDate;
}