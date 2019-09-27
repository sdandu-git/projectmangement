package com.cognizant.projectmangement.jpa.model;

import java.io.Serializable;
import java.time.LocalDate;

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
 * The persistent class for the task database table.
 * @author CTS
 */
@Entity
@Table(name="task")
@NamedQuery(name="Task.findAll", query="SELECT t FROM Task t")
@Getter
@Setter
@NoArgsConstructor
public class Task implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="task_id", unique=true, nullable=false)
	private int taskId;

	@Column(name="end_date", nullable=false)
	private LocalDate endDate;

	@Column(nullable=false)
	private int priority;

	@Column(name="start_date", nullable=false)
	private LocalDate startDate;

	@Column(nullable=false, length=200)
	private String status;

	@Column(name="task_description", nullable=false, length=200)
	private String taskDescription;

	//uni-directional many-to-one association to ParentTask
	@ManyToOne
	@JoinColumn(name="parent_id")
	private ParentTask parentTask;

	//uni-directional many-to-one association to Project
	@ManyToOne
	@JoinColumn(name="project_id")
	private Project project;
}